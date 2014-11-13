package com.betassistant.service;

import com.betassistant.domain.Competition;
import com.betassistant.resource.response.MatchesSummaryResult;
import org.springframework.stereotype.Component;

/**
 * @author zenind
 */
@Component
public class NBAMatchResolver implements MatchesResolver {

    @Override
    public MatchesSummaryResult resolve(Competition competition) {
        return null;
    }

    @Override
    public Competition getType() {
        return Competition.NBA;
    }
}
