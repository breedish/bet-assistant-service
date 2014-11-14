package com.betassistant.resource.response;

import com.betassistant.domain.MatchResult;

import java.util.List;
import java.util.Map;

/**
 * Response format:
 * {code}
 * {
 * "201": [
 * {
 * "homeScore": 82,
 * "awayScore": 82,
 * "getHome": false
 * },
 * {
 * "homeScore": 95,
 * "awayScore": 104,
 * "getHome": true
 * }
 * ],
 * "590": [
 * {
 * "homeScore": 88,
 * "awayScore": 82,
 * "getHome": true
 * },
 * {
 * "homeScore": 95,
 * "awayScore": 104,
 * "getHome": true
 * }
 * ],
 * "totalMatches": 234,
 * "totalGoals": 3566
 * }
 * {code}
 *
 * @author zenind
 */
public class MatchesSummaryResponse {

    private final Integer totalMatches;

    private final Integer totalGoals;

    private final Map<Long, List<MatchResult>> results;

    public MatchesSummaryResponse(Integer totalMatches, Integer totalGoals, Map<Long, List<MatchResult>> results) {
        this.totalMatches = totalMatches;
        this.totalGoals = totalGoals;
        this.results = results;
    }

    public Integer getTotalMatches() {
        return totalMatches;
    }

    public Integer getTotalGoals() {
        return totalGoals;
    }

    public Map<Long, List<MatchResult>> getResults() {
        return results;
    }
}
