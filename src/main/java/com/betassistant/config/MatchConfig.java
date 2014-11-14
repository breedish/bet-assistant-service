package com.betassistant.config;

import com.betassistant.domain.Competition;
import com.betassistant.service.CacheAwareCompetitionService;
import com.betassistant.service.CompetitionService;
import com.betassistant.service.DefaultCompetitionService;
import com.betassistant.service.resolver.MatchResultsResolver;
import com.betassistant.service.resolver.NBAResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author zenind
 */
@Configuration
public class MatchConfig {

    @Bean
    CompetitionService competitionService(List<MatchResultsResolver> resolverList) {
        return new CacheAwareCompetitionService(new DefaultCompetitionService(resolverList));
    }

    @Bean
    NBAResolver nbaMatchResolver() {
        return new NBAResolver(Competition.NBA);
    }
}
