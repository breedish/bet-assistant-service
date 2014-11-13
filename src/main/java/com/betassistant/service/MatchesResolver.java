package com.betassistant.service;

import com.betassistant.domain.Competition;
import com.betassistant.resource.response.MatchesSummaryResult;

/**
 * @author zenind
 */
public interface MatchesResolver {

    MatchesSummaryResult resolve(Competition competition);

    Competition getType();

}
