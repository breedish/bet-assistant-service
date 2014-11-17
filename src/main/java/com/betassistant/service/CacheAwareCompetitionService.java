package com.betassistant.service;

import com.betassistant.domain.Competition;
import com.betassistant.domain.Team;
import com.betassistant.resource.response.MatchesSummaryResponse;
import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author zenind
 */
public class CacheAwareCompetitionService implements CompetitionService {

    private final CompetitionService delegate;

    private final Cache<Competition, MatchesSummaryResponse> matchesSummaryCache = CacheBuilder.newBuilder()
        .maximumSize(200)
        .expireAfterAccess(12, TimeUnit.HOURS)
        .build();

    public CacheAwareCompetitionService(CompetitionService delegate) {
        Preconditions.checkNotNull(delegate);
        this.delegate = delegate;
    }

    @Override
    public Set<Competition> getCompetitions() {
        return delegate.getCompetitions();
    }

    @Override
    public Set<Team> getTeams(Competition competition) {
        return delegate.getTeams(competition);
    }

    @Override
    public MatchesSummaryResponse getMatchesSummary(Competition competition) {
        MatchesSummaryResponse response = matchesSummaryCache.getIfPresent(competition);
        if (response == null) {
            response = delegate.getMatchesSummary(competition);
            matchesSummaryCache.put(competition, response);
        }

        return response;
    }
}
