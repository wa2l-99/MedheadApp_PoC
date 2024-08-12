package com.poc.gateway.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi userAuthApi() {
        return GroupedOpenApi.builder()
                .group("userAuth")
                .pathsToMatch("/userAuth-service/**")
                .build();
    }

    @Bean
    public GroupedOpenApi hospitalManagementApi() {
        return GroupedOpenApi.builder()
                .group("hospitalManagement")
                .pathsToMatch("/hospitalManagement-service/**")
                .build();
    }

    @Bean
    public GroupedOpenApi reservationApi() {
        return GroupedOpenApi.builder()
                .group("reservation")
                .pathsToMatch("/reservation-service/**")
                .build();
    }
}


