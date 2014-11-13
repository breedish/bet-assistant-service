package com.betassistant.service;

import com.betassistant.domain.Competition;
import com.betassistant.domain.Team;
import com.betassistant.resource.response.MatchesSummaryResult;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zenind
 */
@Service
public class CompetitionService {

    private final static Map<Competition, Set<Team>> teamByCompetition = Arrays.stream(Competition.values())
        .collect(Collectors.toMap(p -> p, Competition::getTeams));

    private final static Map<Competition, MatchesResolver> matchesResolverByCompetition = ImmutableMap.of(
        Competition.NBA, new NBAMatchResolver()
    );

    public Set<Competition> getCompetitions() {
        return Sets.newHashSet(Competition.values());
    }

    public Set<Team> getTeams(Competition competition) {
        return teamByCompetition.getOrDefault(competition, Collections.emptySet());
    }

    public MatchesSummaryResult getMatchesResults(Competition competition) {
        if (!matchesResolverByCompetition.containsKey(competition)) {
            throw new IllegalStateException(
                String.format("Matches results service for %s competition is not available", competition.getName())
            );
        }

        return matchesResolverByCompetition.get(competition).resolve(competition);
    }

}
