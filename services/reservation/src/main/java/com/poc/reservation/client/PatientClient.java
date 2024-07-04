package com.poc.reservation.client;


import com.poc.reservation.util.response.PatientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(
        name = "userAuthentificaton-service",
        url = "${application.config.patient-url}"
)
public interface PatientClient {

    @GetMapping("/{patient-id}")
    Optional<PatientResponse> findPatientById(@PathVariable("patient-id") Integer patientId);
}
