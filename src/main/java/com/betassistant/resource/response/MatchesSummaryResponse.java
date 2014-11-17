package com.betassistant.resource.response;

import com.betassistant.domain.MatchResult;

import java.util.List;
import java.util.Map;

/**
 * @author zenind
 */
public class MatchesSummaryResponse {

    private final TotalStats totalStats;

    private final Map<Long, List<MatchResult>> results;

    public MatchesSummaryResponse(TotalStats totalStats, Map<Long, List<MatchResult>> results) {
        this.totalStats = totalStats;
        this.results = results;
    }

    public TotalStats getTotalStats() {
        return totalStats;
    }

    public Map<Long, List<MatchResult>> getResults() {
        return results;
    }
}
