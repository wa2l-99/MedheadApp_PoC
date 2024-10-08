package com.poc.medhead.controller;


import com.google.maps.errors.ApiException;
import com.poc.medhead.service.HospitalService;
import com.poc.medhead.service.SpecialityService;
import com.poc.medhead.util.request.AddSpecialityToHospitalRequest;
import com.poc.medhead.util.request.HospitalRequest;
import com.poc.medhead.util.request.SpecialityRequest;
import com.poc.medhead.util.response.HospitalResponse;
import com.poc.medhead.util.response.PageResponse;
import com.poc.medhead.util.response.SpecialityResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/hospital")
@RequiredArgsConstructor
public class HospitalController {

    private final HospitalService hospitalService;
    private final SpecialityService specialityService;


    @PostMapping("/addHospital")
    public ResponseEntity<Integer> createHospital(
            @RequestBody @Valid HospitalRequest hospitalRequest
    ){
        return ResponseEntity.ok(hospitalService.saveHospital(hospitalRequest));
    }

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
    public ResponseEntity<String> deleteHospital(
            @PathVariable("hospital-id") Integer HospitalId
    ){
        hospitalService.deleteHospital(HospitalId);
        return ResponseEntity.ok("L'hôpital a été supprimé avec succès.");
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
    public ResponseEntity<List<HospitalResponse>> findNearestHospitals(
            @RequestParam String address,
            @RequestParam String specialty) throws InterruptedException, ApiException, IOException {
        return ResponseEntity.ok(hospitalService.findNearestHospitals(address,specialty));
    }

    @PostMapping("/addSpecialityToHospital")
    public ResponseEntity<HospitalResponse> addSpecialityToHospital (
            @RequestBody @Valid AddSpecialityToHospitalRequest request
    ) {

        return ResponseEntity.ok(hospitalService.addSpecialityToHospital(request));
    }

    @PutMapping("/updateBeds/{hospital-id}")
    public ResponseEntity<HospitalResponse> updateNbBeds(
            @PathVariable("hospital-id") Integer hospitalId,
            @RequestParam("beds") Integer beds)
    {
        return ResponseEntity.ok(hospitalService.updateBeds(hospitalId, beds));
    }


    /*********** Specialty APIs *************/

    @PostMapping("/speciality")
    public ResponseEntity<Integer> createSpecialty(
            @RequestBody @Valid SpecialityRequest specialityRequest
    ){
        return ResponseEntity.ok(specialityService.saveSpeciality(specialityRequest));
    }

    @GetMapping("/specialities")
    public  ResponseEntity<PageResponse<SpecialityResponse>> findAllSpecialities(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "1000", required = false) int size
    ){
        return ResponseEntity.ok(specialityService.getAllSpecialities(page,size));
    }
}