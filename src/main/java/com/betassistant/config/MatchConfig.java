package com.betassistant.config;

import com.betassistant.domain.Competition;
import com.betassistant.service.CacheAwareCompetitionService;
import com.betassistant.service.CompetitionService;
import com.betassistant.service.DefaultCompetitionService;
import com.betassistant.service.resolver.MatchesResolver;
import com.betassistant.service.resolver.NBAMatchResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author zenind
 */
@Configuration
public class MatchConfig {

    @Bean
    CompetitionService competitionService(List<MatchesResolver> resolverList) {
        return new CacheAwareCompetitionService(new DefaultCompetitionService(resolverList));
    }

    @Bean
    NBAMatchResolver nbaMatchResolver() {
        return new NBAMatchResolver(Competition.NBA);
    }
}
