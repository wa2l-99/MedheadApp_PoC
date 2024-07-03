package com.poc.medhead.service;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GeocodingService {

    @Autowired
    private GeoApiContext geoApiContext;

    public GeocodingResult[] getGeocoding(String address) throws InterruptedException, ApiException, IOException {
        return GeocodingApi.geocode(geoApiContext, address).await();
    }
}