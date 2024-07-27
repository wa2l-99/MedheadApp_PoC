package com.poc.reservation.config;

import com.poc.reservation.service.JwtService;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FeignConfig {

    private final JwtService jwtService;

    public FeignConfig(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                // Récupérer le token JWT à partir de JwtService
                String token = jwtService.getToken();
                template.header("Authorization", "Bearer " + token);
            }
        };
    }

}