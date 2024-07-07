package com.poc.medhead.service;

import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.poc.medhead.dao.HospitalRepository;
import com.poc.medhead.dao.SpecialityRepository;
import com.poc.medhead.exceptions.AlreadyExistsException;
import com.poc.medhead.mapper.HospitalMapper;
import com.poc.medhead.model.Hospital;
import com.poc.medhead.model.MedicalSpeciality;
import com.poc.medhead.util.request.AddSpecialityToHospitalRequest;
import com.poc.medhead.util.request.HospitalRequest;
import com.poc.medhead.util.response.HospitalResponse;
import com.poc.medhead.util.response.PageResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HospitalService {


    private final HospitalRepository hospitalRepository;
    private final SpecialityRepository specialityRepository;
    private final HospitalMapper mapper;
    private final GeocodingService geocodingService;


    public Integer saveHospital(HospitalRequest hospitalRequest) {
        var hospital = mapper.toHospital(hospitalRequest);

        if (hospitalRepository.existsByNomOrganisation(hospital.getNomOrganisation())){
            throw new AlreadyExistsException("L'Hopital avec le nom " + hospital.getNomOrganisation()+ " existe déja");
        }
        return hospitalRepository.save(hospital).getId();
    }
    public HospitalResponse getHospitalById(Integer hospitalId) {
        return hospitalRepository.findById(hospitalId)
                .map(mapper::toHospitalResponse)
                .orElseThrow(() -> new EntityNotFoundException("Hopital n'a pas trouvé avec l'ID " + hospitalId));
    }

    public PageResponse<HospitalResponse> getAllHospitals(int page, int size) {
        // Création de l'objet Pageable avec tri par date de création décroissante
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

        // Récupération des hôpitaux par spécialité avec pagination
        Page<Hospital> hospitals = hospitalRepository.findAll(pageable);

        // Transformation des entités Hospital en DTO HospitalResponse
        List<HospitalResponse> hospitalResponses = hospitals.stream()
                .map(mapper::toHospitalResponse)
                .collect(Collectors.toList());
         return new PageResponse<>(
                 hospitalResponses,
                 hospitals.getNumber(),
                 hospitals.getSize(),
                 hospitals.getTotalElements(),
                 hospitals.getTotalPages(),
                 hospitals.isFirst(),
                 hospitals.isLast()
         );
    }

    public PageResponse<HospitalResponse> getHospitalsBySpeciality(int page, int size, Integer specialityId) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Hospital> hospitals = hospitalRepository.findHospitalsBySpeciality(pageable, specialityId);
        List<HospitalResponse> hospitalResponses = hospitals.stream()
                .map(mapper::toHospitalResponse)
                .collect(Collectors.toList());
        return new PageResponse<>(
                hospitalResponses,
                hospitals.getNumber(),
                hospitals.getSize(),
                hospitals.getTotalElements(),
                hospitals.getTotalPages(),
                hospitals.isFirst(),
                hospitals.isLast()
        );
    }


    public HospitalResponse findNearestHospital(String address, String specialty) throws InterruptedException, ApiException, IOException {
        GeocodingResult[] results = geocodingService.getGeocoding(address);

        if (results.length == 0) {
            throw new EntityNotFoundException("Addresse non trouvé: " + address);
        }

        double latitude = results[0].geometry.location.lat;
        double longitude = results[0].geometry.location.lng;

        List<Hospital> hospitals = hospitalRepository.findNearestAvailableHospitalsBySpecialty(specialty);

        Hospital nearestHospital = hospitals.stream()
                .min(Comparator.comparingDouble(h -> calculateDistance(latitude, longitude, h.getLatitude(), h.getLongitude())))
                .orElseThrow(() -> new EntityNotFoundException("Aucun hôpital avec cette spécialité : " + specialty));

        return mapper.toHospitalResponse(nearestHospital);
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the earth in km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // Distance in km
    }

    public HospitalResponse updateHospital(Integer hospitalId, HospitalRequest updatedHospital) {
        Hospital existingHospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new EntityNotFoundException("Hôpital non trouvé avec l'ID " + hospitalId));

        // Convertir les IDs de spécialités en entités MedicalSpeciality
        Set<MedicalSpeciality> specialities = updatedHospital.getSpecialiteIds().stream()
                .map(specialityId -> specialityRepository.findById(specialityId)// Trouver chaque spécialité par son ID
                        .orElseThrow(() -> new EntityNotFoundException("Spécialité non trouvée avec l'ID " + specialityId)))
                .collect(Collectors.toSet()); // Collecter les résultats dans un Set

        existingHospital.setNomOrganisation(updatedHospital.getNomOrganisation());
        existingHospital.setAdresse(updatedHospital.getAdresse());
        existingHospital.setCodePostal(updatedHospital.getCodePostal());
        existingHospital.setSpecialitesMedicales(specialities);
        existingHospital.setLitsDisponible(updatedHospital.getLitsDisponible());
        existingHospital.setLongitude(updatedHospital.getLongitude());
        existingHospital.setLatitude(updatedHospital.getLatitude());

        hospitalRepository.save(existingHospital);

        return mapper.toHospitalResponse(existingHospital);
    }


    public void  deleteHospital(Integer hospitalId) {
        Hospital existingHospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new EntityNotFoundException("Hôpital non trouvé avec l'ID " + hospitalId));

        hospitalRepository.delete(existingHospital);
    }


    public HospitalResponse  addSpecialityToHospital(AddSpecialityToHospitalRequest request) {
        Hospital hospital = hospitalRepository.findById(request.hospitalId())
                .orElseThrow(() -> new EntityNotFoundException("Hôpital non trouvé avec ID " + request.hospitalId()));

        MedicalSpeciality speciality = specialityRepository.findById(request.specialityId())
                .orElseThrow(() -> new EntityNotFoundException("Spécialité médicale non trouvée avec ID " + request.specialityId()));

        hospital.getSpecialitesMedicales().add(speciality);
        hospitalRepository.save(hospital);

        return mapper.toHospitalResponse(hospital);
    }

    public HospitalResponse updateBeds(Integer hospitalId, Integer beds) {
        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new EntityNotFoundException("Hôpital non trouvé avec ID " + hospitalId));

        hospital.setLitsDisponible(beds);
        hospitalRepository.save(hospital);

        return mapper.toHospitalResponse(hospital);
    }
}
