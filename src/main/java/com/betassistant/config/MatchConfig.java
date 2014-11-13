package com.betassistant.config;

import com.betassistant.domain.Competition;
import com.betassistant.service.NBAMatchResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zenind
 */
@Configuration
public class MatchConfig {

    @Bean
    NBAMatchResolver nbaMatchResolver() {
        return new NBAMatchResolver(Competition.NBA);
    }
}
