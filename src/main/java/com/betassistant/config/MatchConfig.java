package com.betassistant.config;

import com.betassistant.domain.Competition;
import com.betassistant.service.results.resolver.NBAResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zenind
 */
@Configuration
public class MatchConfig {

    @Bean
    NBAResolver nbaMatchResolver() {
        return new NBAResolver(Competition.NBA);
    }
}
