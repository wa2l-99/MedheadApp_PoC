package com.poc.medhead.controller;


import com.poc.medhead.service.HospitalService;
import com.poc.medhead.util.request.HospitalRequest;
import com.poc.medhead.util.response.HospitalResponse;
import com.poc.medhead.util.response.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/hospital")
@RequiredArgsConstructor
public class hospitalController {

    private final HospitalService hospitalService;


    @PostMapping
    public ResponseEntity<Integer> createHospital(
            @RequestBody @Valid HospitalRequest request
    ){
        // Retourner une réponse HTTP avec le statut 201 Created et l'id de l'hôpital créé
        return  ResponseEntity.ok(hospitalService.createHospital(request));
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
            @RequestBody HospitalResponse updatedHospital
    ){
        return ResponseEntity.ok(hospitalService.updateHospital(HospitalId, updatedHospital));
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

    @GetMapping("/search")
    public ResponseEntity<PageResponse<HospitalResponse>> findNearestHospitalWithSpecialty(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam String specialty) {
        return ResponseEntity.ok(hospitalService.findNearestHospitalWithSpecialty(page, size, latitude, longitude, specialty));
    }
}