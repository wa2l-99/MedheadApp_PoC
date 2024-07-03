package com.poc.medhead.controller;


import com.google.maps.errors.ApiException;
import com.poc.medhead.service.HospitalService;
import com.poc.medhead.util.request.AddSpecialityToHospitalRequest;
import com.poc.medhead.util.request.HospitalRequest;
import com.poc.medhead.util.response.HospitalResponse;
import com.poc.medhead.util.response.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/api/hospital")
@RequiredArgsConstructor
public class HospitalController {

    private final HospitalService hospitalService;


    @PostMapping("/addHospital")
    public ResponseEntity<Integer> createHospital(
            @RequestBody @Valid HospitalRequest hospitalRequest
    ){
        return ResponseEntity.ok(hospitalService.saveHospital(hospitalRequest));
    }

    /**
    @PostMapping("/reserve")
    public ResponseEntity<List<HospitalReserveResponse>> reserveHospitals(
            @RequestBody @Valid HospitalReserveRequest request
    ){
        return ResponseEntity.ok(hospitalService.reserveHospitals(request));
    }**/

    @GetMapping("/{hospital-id}")
    public ResponseEntity<HospitalResponse> findHospitalById(
            @PathVariable("hospital-id") Integer HospitalId
    ){
        return ResponseEntity.ok(hospitalService.getHospitalById(HospitalId));
    }

    @GetMapping()
    public  ResponseEntity<PageResponse<HospitalResponse>> findAllHospitals(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ){
        return ResponseEntity.ok(hospitalService.getAllHospitals(page,size));
    }
    @PutMapping("/{hospital-id}")
    public ResponseEntity<HospitalResponse> updateHospital(
            @PathVariable("hospital-id") Integer HospitalId,
            @RequestBody @Valid HospitalRequest hospitalRequest) {
        return ResponseEntity.ok(hospitalService.updateHospital(HospitalId, hospitalRequest));
    }

    @DeleteMapping("/{hospital-id}")
    public ResponseEntity<Void> deleteHospital(
            @PathVariable("hospital-id") Integer HospitalId
    ){
        hospitalService.deleteHospital(HospitalId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/speciality/id={speciality-id}")
    public  ResponseEntity<PageResponse<HospitalResponse>> findAllHospitalsBySpecialty(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            @PathVariable("speciality-id") Integer SpecialityId
    ){
        return  ResponseEntity.ok(hospitalService.getHospitalsBySpeciality(page,size,SpecialityId));
    }

    @GetMapping("/nearest")
    public ResponseEntity<HospitalResponse> findNearestHospital(
            @RequestParam String address,
            @RequestParam String specialty) throws InterruptedException, ApiException, IOException {
        return ResponseEntity.ok(hospitalService.findNearestHospital(address, specialty));
    }

    @PostMapping("/addSpecialityToHospital")
    public ResponseEntity<Void> addSpecialityToHospital (
            @RequestBody @Valid AddSpecialityToHospitalRequest request
    ) {
        hospitalService.addSpecialityToHospital(request);
        return ResponseEntity.ok().build();}
}