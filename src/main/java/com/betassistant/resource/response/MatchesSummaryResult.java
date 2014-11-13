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
 * "isHome": false
 * },
 * {
 * "homeScore": 95,
 * "awayScore": 104,
 * "isHome": true
 * }
 * ],
 * "590": [
 * {
 * "homeScore": 88,
 * "awayScore": 82,
 * "isHome": true
 * },
 * {
 * "homeScore": 95,
 * "awayScore": 104,
 * "isHome": true
 * }
 * ],
 * "totalMatches": 234,
 * "totalGoals": 3566
 * }
 * {code}
 *
 * @author zenind
 */
public class MatchesSummaryResult {

    private final Integer totalMatches;

    private final Integer totalGoals;

    private final Map<Integer, List<MatchResult>> results;

    public MatchesSummaryResult(Integer totalMatches, Integer totalGoals, Map<Integer, List<MatchResult>> results) {
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

    public Map<Integer, List<MatchResult>> getResults() {
        return results;
    }
}
