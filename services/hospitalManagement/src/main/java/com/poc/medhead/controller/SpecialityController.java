package com.poc.medhead.controller;

import com.poc.medhead.service.SpecialityService;
import com.poc.medhead.util.request.AddSpecialityToHospitalRequest;
import com.poc.medhead.util.request.SpecialityRequest;
import com.poc.medhead.util.response.HospitalResponse;
import com.poc.medhead.util.response.PageResponse;
import com.poc.medhead.util.response.SpecialityResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/speciality")
@RequiredArgsConstructor
public class SpecialityController {

    private final SpecialityService specialityService;

    @PostMapping()
    public ResponseEntity<Integer> createSpecialty(
            @RequestBody @Valid SpecialityRequest specialityRequest
    ){
        return ResponseEntity.ok(specialityService.saveSpeciality(specialityRequest));
    }

    @GetMapping()
    public  ResponseEntity<PageResponse<SpecialityResponse>> findAllSpecialities(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ){
        return ResponseEntity.ok(specialityService.getAllSpecialities(page,size));
    }

}
