package com.betassistant.service;

import com.betassistant.domain.Competition;
import com.betassistant.domain.MatchResult;
import com.betassistant.domain.Team;
import com.betassistant.resource.response.MatchesSummaryResponse;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author zenind
 */
@Service
public class CompetitionService {

    private static final Logger LOG = LoggerFactory.getLogger(CompetitionService.class);

    private final Cache<Team, List<MatchResult>> matchesCache = CacheBuilder.newBuilder()
        .maximumSize(200)
        .expireAfterAccess(10, TimeUnit.MINUTES)
        .build();

    private final ExecutorService executionPool = Executors.newCachedThreadPool();

    private final Map<Competition, MatchesResolver> matchesResolverByCompetition;

    @Autowired
    public CompetitionService(List<MatchesResolver> resolverList) {
        this.matchesResolverByCompetition = ImmutableMap.copyOf(
            resolverList.stream().collect(Collectors.toMap(MatchesResolver::getType, p -> p))
        );
    }

    public Set<Competition> getCompetitions() {
        return Sets.newHashSet(Competition.values());
    }

    public Set<Team> getTeams(Competition competition) {
        return competition.getTeams();
    }

    public MatchesSummaryResponse getMatchesSummary(Competition competition) {
        Map<Team, List<MatchResult>> byTeamResults = new HashMap<>();

        byTeamResults.putAll(matchesCache.getAllPresent(competition.getTeams()));

        List<Team> toResolve = competition.getTeams().stream()
            .filter(t -> !byTeamResults.containsKey(t))
            .collect(Collectors.toList());

        if (!toResolve.isEmpty()) {
            Map<Team, List<MatchResult>> resolved = resolveMatches(toResolve, competition);
            byTeamResults.putAll(resolved);
            matchesCache.putAll(resolved);
        }

        Pair<Long, Long> aggregateInfo = calculatesStats(byTeamResults);
        return new MatchesSummaryResponse(
            aggregateInfo.getValue0(),
            aggregateInfo.getValue1(),
            byTeamResults.entrySet().stream().collect(Collectors.toMap(e -> e.getKey().getId(), Map.Entry::getValue))
        );
    }

    protected Pair<Long, Long> calculatesStats(Map<Team, List<MatchResult>> results) {
        return results.values().stream()
            .collect(TotalStatsConsumer::new, TotalStatsConsumer::accept, TotalStatsConsumer::combine)
            .getStats();
    }

    static class TotalStatsConsumer implements Consumer<List<MatchResult>> {

        private int total;

        private int totalScore;

        @Override
        public void accept(List<MatchResult> value) {
            total+= value.size();
            totalScore += value.stream().mapToInt(m -> m.getScoreAway() + m.getScoreHome()).sum();
        }

        public void combine(TotalStatsConsumer other) {
            total += other.total;
            totalScore += other.totalScore;
        }

        public Pair<Long, Long> getStats() {
            return new Pair<>(medium(total), medium(totalScore));
        }

        private Long medium(int value) {
            Double result = value > 0 ? ((double) value) / 2 : 0;
            return result.longValue();
        }
    }

    protected Map<Team, List<MatchResult>> resolveMatches(List<Team> teams, Competition competition) {
        if (teams.isEmpty()) {
            return Collections.emptyMap();
        }

        final MatchesResolver resolver = findResolver(competition);
        final CountDownLatch latch = new CountDownLatch(teams.size());
        final Map<Team, List<MatchResult>> results = new ConcurrentHashMap<>();
        teams.parallelStream().forEach(t -> executionPool.submit(() -> {
            try {
                LOG.info("Resolving results for {}", t.getId());
                results.put(t, resolver.resolve(t));
            } catch (Exception e) {
                LOG.error("Issue happened while resolving result for '{}' team: {}", t, e);
            } finally {
                latch.countDown();
            }
        }));

        try {
            latch.await(180, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            LOG.error("Issue happened while resolving results for '{}' competition: {}", competition, e);
        }

        return results;
    }

    protected MatchesResolver findResolver(Competition competition) {
        if (!matchesResolverByCompetition.containsKey(competition)) {
            throw new IllegalStateException(
                String.format("Matches results service for %s competition is not available", competition.getName())
            );
        }
        return matchesResolverByCompetition.get(competition);
    }

    @PreDestroy
    public void onShutdown() {
        try {
            executionPool.shutdownNow();
            executionPool.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            LOG.error("Error awaiting matches execution pool to shutdown.");
            Thread.interrupted();
        }
    }
}
