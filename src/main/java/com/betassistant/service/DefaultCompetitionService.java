package com.betassistant.service;

import com.betassistant.domain.Competition;
import com.betassistant.domain.Team;
import com.betassistant.resource.response.MatchesSummaryResponse;
import com.betassistant.service.results.CompetitionResultsSummaryProvider;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author zenind
 */
@Service
public class DefaultCompetitionService implements CompetitionService {

    private final CompetitionResultsSummaryProvider summaryProvider;

    @Autowired
    public DefaultCompetitionService(CompetitionResultsSummaryProvider summaryProvider) {
        this.summaryProvider = summaryProvider;
    }

    public Set<Competition> getCompetitions() {
        return Sets.newHashSet(Competition.values());
    }

    public Set<Team> getTeams(Competition competition) {
        return competition.getTeams();
    }

    @Override
    public MatchesSummaryResponse getMatchesSummary(Competition competition) {
        return summaryProvider.getMatchesSummary(competition);
    }
}
