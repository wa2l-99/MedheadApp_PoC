package com.poc.medhead.mapper;

import com.poc.medhead.model.Hospital;
import com.poc.medhead.util.request.HospitalRequest;
import com.poc.medhead.util.response.HospitalResponse;
import org.springframework.stereotype.Component;

@Component
public class HospitalMapper {

    public Hospital toHospital(HospitalRequest request) {

        return Hospital.builder()
                .id(request.id())
                .nom_organisation(request.nom_organisation())
                .adresse(request.adresse())
                .code_postal(request.code_postal())
                .lits_disponible(request.lits_disponible())
                .longitude(request.longitude())
                .latitude(request.latitude())
                .specialites_medicales(request.specialites_medicales())
                .build();
    }

    public HospitalResponse toHospitalResponse(Hospital hospital) {

        return HospitalResponse.builder()
                .id(hospital.getId())
                .nom_organisation(hospital.getNom_organisation())
                .adresse(hospital.getAdresse())
                .code_postal(hospital.getCode_postal())
                .lits_disponible(hospital.getLits_disponible())
                .longitude(hospital.getLongitude())
                .latitude(hospital.getLatitude())
                .specialites_medicales(hospital.getSpecialites_medicales())
                .build();
    }
}
