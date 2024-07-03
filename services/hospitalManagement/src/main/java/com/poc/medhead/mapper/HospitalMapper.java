package com.poc.medhead.mapper;

import com.poc.medhead.dao.SpecialityRepository;
import com.poc.medhead.model.Hospital;
import com.poc.medhead.model.MedicalSpeciality;
import com.poc.medhead.util.request.HospitalRequest;
import com.poc.medhead.util.response.HospitalResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class HospitalMapper {

    @Autowired
    private SpecialityRepository specialityRepository;

    public Hospital toHospital(HospitalRequest request) {
        Set<MedicalSpeciality> specialities = request.getSpecialiteIds().stream()
                .map(specialityRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());

        return Hospital.builder()
                .nomOrganisation(request.getNomOrganisation())
                .adresse(request.getAdresse())
                .codePostal(request.getCodePostal())
                .litsDisponible(request.getLitsDisponible())
                .longitude(request.getLongitude())
                .latitude(request.getLatitude())
                .specialitesMedicales(specialities)
                .build();
    }

    public HospitalResponse toHospitalResponse(Hospital hospital) {
        return HospitalResponse.builder()
                .id(hospital.getId())
                .nomOrganisation(hospital.getNomOrganisation())
                .adresse(hospital.getAdresse())
                .codePostal(hospital.getCodePostal())
                .litsDisponible(hospital.getLitsDisponible())
                .longitude(hospital.getLongitude())
                .latitude(hospital.getLatitude())
                .specialitesMedicales(hospital.getSpecialitesMedicales())
                .build();
    }
}
