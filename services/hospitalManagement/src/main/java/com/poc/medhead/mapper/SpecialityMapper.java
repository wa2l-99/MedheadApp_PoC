package com.poc.medhead.mapper;

import com.poc.medhead.model.MedicalSpeciality;
import com.poc.medhead.util.request.SpecialityRequest;
import com.poc.medhead.util.response.SpecialityResponse;
import org.springframework.stereotype.Service;

@Service
public class SpecialityMapper {

    public MedicalSpeciality toMedicalSpeciality (SpecialityRequest specialityRequest){

        return MedicalSpeciality.builder()
                .id(specialityRequest.id())
                .nom(specialityRequest.nom())
                .build();
    }

    public SpecialityResponse toMedicalSpecialityResponse (MedicalSpeciality medicalSpeciality){

        return SpecialityResponse.builder()
                .id(medicalSpeciality.getId())
                .nom(medicalSpeciality.getNom())
                .build();
    }
}
