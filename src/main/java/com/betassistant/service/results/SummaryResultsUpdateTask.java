package com.betassistant.service.results;

import com.betassistant.domain.Competition;
import com.betassistant.domain.MatchResult;
import com.betassistant.domain.Team;
import com.betassistant.resource.response.MatchesSummaryResponse;
import com.betassistant.resource.response.TotalStats;
import com.betassistant.service.results.resolver.MatchResultsResolver;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author zenind
 */
@Component
public class SummaryResultsUpdateTask {

    private static final Logger LOG = LoggerFactory.getLogger(SummaryResultsUpdateTask.class);

    private final ExecutorService executionPool = Executors.newCachedThreadPool();

    private final Map<Competition, MatchResultsResolver> matchesResolverByCompetition;

    private final InMemorySummaryResultsStorage summaryResultsStorage;

    @Autowired
    public SummaryResultsUpdateTask(List<MatchResultsResolver> resolverList, InMemorySummaryResultsStorage summaryResultsStorage) {
        this.matchesResolverByCompetition = ImmutableMap.copyOf(
            resolverList.stream().collect(Collectors.toMap(MatchResultsResolver::getType, p -> p))
        );
        this.summaryResultsStorage = summaryResultsStorage;
    }

    @PostConstruct
    private void init() {
        update();
    }

    @Scheduled(cron = "0 0 0/12 1/1 * ? *")
    public void update() {
        for (Competition competition : Competition.values()) {
            summaryResultsStorage.updateSummaryResults(competition, getMatchesSummary(competition));
        }
    }

    public MatchesSummaryResponse getMatchesSummary(Competition competition) {
        Map<Team, List<MatchResult>> byTeamResults = resolveMatches(competition.getTeams(), competition);

        TotalStats aggregateInfo = calculateTotalStats(byTeamResults);
        return new MatchesSummaryResponse(
            aggregateInfo,
            byTeamResults.entrySet().stream().collect(Collectors.toMap(e -> e.getKey().getId(), Map.Entry::getValue))
        );
    }

    protected TotalStats calculateTotalStats(Map<Team, List<MatchResult>> results) {
        return results.values().stream()
            .collect(TotalStats::new, TotalStats::accept, TotalStats::combine);
    }

    protected Map<Team, List<MatchResult>> resolveMatches(Set<Team> teams, Competition competition) {
        if (teams.isEmpty()) {
            return Collections.emptyMap();
        }

        final MatchResultsResolver resolver = findResolver(competition);
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

    protected MatchResultsResolver findResolver(Competition competition) {
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
