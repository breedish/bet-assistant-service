package com.betassistant.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.config.PropertyOverrideConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

/**
 * @author zenind
 */
@Configuration
@ComponentScan(basePackages = "com.betassistant")
public class MainConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper;
    }

    @Bean
    public PropertyOverrideConfigurer propertyOverrideConfigurer() {
        PropertyOverrideConfigurer configurer = new PropertyOverrideConfigurer();
        configurer.setLocations(new ClassPathResource("bet-assistant.properties"));
        return configurer;
    }

    @Bean
    PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(ApplicationContext context) {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setLocation(new ClassPathResource("bet-assistant.properties"));
        configurer.setIgnoreResourceNotFound(true);
        return configurer;
    }

}

