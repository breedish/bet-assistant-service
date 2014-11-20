package com.betassistant.service.results;

import com.betassistant.domain.Competition;
import com.betassistant.resource.response.MatchesSummaryResponse;

/**
 * @author zenind
 */
public interface CompetitionResultsSummaryProvider {

    MatchesSummaryResponse getMatchesSummary(Competition competition);

}
