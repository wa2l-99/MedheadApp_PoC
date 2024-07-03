package com.poc.medhead.service;

import com.poc.medhead.dao.HospitalRepository;
import com.poc.medhead.dao.SpecialityRepository;
import com.poc.medhead.exceptions.AlreadyExistsException;
import com.poc.medhead.mapper.SpecialityMapper;
import com.poc.medhead.model.MedicalSpeciality;
import com.poc.medhead.util.request.SpecialityRequest;
import com.poc.medhead.util.response.PageResponse;
import com.poc.medhead.util.response.SpecialityResponse;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SpecialityService {

    private final SpecialityRepository specialityRepository;
    private final SpecialityMapper specialityMapper;
    private final HospitalRepository hospitalRepository;


    public Integer saveSpeciality(SpecialityRequest specialityRequest) {
        var medicalSpecialty = specialityMapper.toMedicalSpeciality(specialityRequest);

        if (specialityRepository.existsByNom(medicalSpecialty.getNom())){
            throw new AlreadyExistsException("La spécialité médicale avec le nom " + medicalSpecialty.getNom()+ " existe déja");
        }
        return  specialityRepository.save(medicalSpecialty).getId();
    }

    public PageResponse<SpecialityResponse> getAllSpecialities(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id  ").ascending());

        Page<MedicalSpeciality> specialities = specialityRepository.findAll(pageable);

        List<SpecialityResponse> specialityResponses = specialities.stream()
                .map(specialityMapper::toMedicalSpecialityResponse)
                .collect(Collectors.toList());
        return new PageResponse<>(
                specialityResponses,
                specialities.getNumber(),
                specialities.getSize(),
                specialities.getTotalElements(),
                specialities.getTotalPages(),
                specialities.isFirst(),
                specialities.isLast()
        );
    }


}
