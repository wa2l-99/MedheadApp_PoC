package com.poc.reservation.client;

import com.poc.reservation.config.FeignConfig;
import com.poc.reservation.util.response.HospitalResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(
        name = "hospitalManagement-service",
        url = "${application.config.hospital-url}",
        configuration = FeignConfig.class

)
public interface HospitalClient {

    @GetMapping("{hospital-id}")
    Optional<HospitalResponse>  findHospitalById(@PathVariable("hospital-id") Integer hospitalId);

    @PutMapping("/updateBeds/{hospital-id}")
    void updateBeds(@PathVariable("hospital-id") Integer hospitalId, @RequestParam("beds") Integer beds);
}
