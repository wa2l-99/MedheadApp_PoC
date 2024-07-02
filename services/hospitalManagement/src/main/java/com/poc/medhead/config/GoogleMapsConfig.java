package com.poc.medhead.config;

import com.google.maps.GeoApiContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoogleMapsConfig {

    @Bean
    public GeoApiContext geoApiContext() {
        return new GeoApiContext.Builder()
                .apiKey("AIzaSyAAz3CYCtK1vbOlVXwAqOFBrK-fW8wv3R4")
                .build();
    }
}
