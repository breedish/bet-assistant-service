package com.betassistant.service;

import com.betassistant.domain.Competition;
import com.betassistant.domain.Team;
import com.betassistant.resource.response.MatchesSummaryResponse;

import java.util.Set;

/**
 * @author zenind
 */
public interface CompetitionService {

    Set<Competition> getCompetitions();

    Set<Team> getTeams(Competition competition);

    MatchesSummaryResponse getMatchesSummary(Competition competition);
}
