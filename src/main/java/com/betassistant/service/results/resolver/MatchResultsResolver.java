package com.betassistant.service.results.resolver;

import com.betassistant.domain.Competition;
import com.betassistant.domain.MatchResult;
import com.betassistant.domain.Team;

import java.util.List;

/**
 * @author zenind
 */
public interface MatchResultsResolver {

    List<MatchResult> resolve(Team team);

    Competition getType();

}
