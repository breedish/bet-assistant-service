package com.betassistant.service.results;

import com.betassistant.domain.Competition;
import com.betassistant.resource.response.MatchesSummaryResponse;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zenind
 */
@Component
public class InMemorySummaryResultsStorage implements CompetitionResultsSummaryProvider {

    private final Map<Competition, MatchesSummaryResponse> storage = new ConcurrentHashMap<>();

    @Override
    public MatchesSummaryResponse getMatchesSummary(Competition competition) {
        return storage.get(competition);
    }

    public void updateSummaryResults(Competition competition, MatchesSummaryResponse summaryResponse) {
        storage.put(competition, summaryResponse);
    }

}
