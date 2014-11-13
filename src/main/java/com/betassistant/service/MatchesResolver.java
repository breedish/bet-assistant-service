package com.betassistant.service;

import com.betassistant.domain.Competition;
import com.betassistant.domain.MatchResult;
import com.betassistant.domain.Team;

import java.util.List;

/**
 * @author zenind
 */
public interface MatchesResolver {

    List<MatchResult> resolve(Team team);

    Competition getType();

}
