package com.poc.medhead.service;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.LatLng;
import com.poc.medhead.dao.HospitalRepository;
import com.poc.medhead.mapper.HospitalMapper;
import com.poc.medhead.model.Hospital;
import com.poc.medhead.model.MedicalSpeciality;
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

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HospitalService {


    private final HospitalRepository hospitalRepository;
    private final HospitalMapper mapper;
    private final GeoApiContext  geoApiContext;


    public Integer createHospital(HospitalRequest request) {
        // Utilisation du mapper pour convertir HospitalRequest en Hospital
        var hospital = mapper.toHospital(request);
        // Sauvegarder l'entité hospital dans la base de données et retourner l'id de l'hôpital créé
        return hospitalRepository.save(hospital).getId();
    }


    public HospitalResponse getHospitalById(Integer hospitalId) {
        return hospitalRepository.findById(hospitalId)
                .map(mapper::toHospitalResponse)
                .orElseThrow(() -> new EntityNotFoundException("Hopital n'a pas trouvé avec l'ID" + hospitalId));
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

    public PageResponse<HospitalResponse> findNearestHospitalWithSpecialty(int page, int size, Double latitude, Double longitude, String specialty) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Hospital> hospitals = hospitalRepository.findHospitalsWithSpecialtyAndAvailableBeds(pageable,specialty);

        List<HospitalResponse> hospitalResponses = hospitals.stream()
                .map(mapper::toHospitalResponse)
                .collect(Collectors.toList());

        HospitalResponse nearestHospital = hospitalResponses.stream()
                .min(Comparator.comparingDouble(h -> calculateDistance(latitude, longitude, h.getLatitude(), h.getLongitude())))
                .orElseThrow(() -> new RuntimeException("Aucun hôpital disponible avec la spécialité donnée"));

        return new PageResponse<>(
                List.of(nearestHospital),
                hospitals.getNumber(),
                hospitals.getSize(),
                hospitals.getTotalElements(),
                hospitals.getTotalPages(),
                hospitals.isFirst(),
                hospitals.isLast()
        );
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        LatLng origin = new LatLng(lat1, lon1);
        LatLng destination = new LatLng(lat2, lon2);

        try {
            DistanceMatrix result = DistanceMatrixApi.newRequest(geoApiContext)
                    .origins(origin)
                    .destinations(destination)
                    .await();

            return result.rows[0].elements[0].distance.inMeters / 1000.0; // convert meters to kilometers
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur de calcul de la distance", e);
        }
    }

    public HospitalResponse updateHospital(Integer hospitalId, HospitalResponse updatedHospital) {
        Hospital existingHospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new EntityNotFoundException("Hopital non trouvé avec l'ID " + hospitalId));

            existingHospital.setNom_organisation(updatedHospital.getNom_organisation());
            existingHospital.setAdresse(updatedHospital.getAdresse());
            existingHospital.setCode_postal(updatedHospital.getCode_postal());
            existingHospital.setSpecialites_medicales(updatedHospital.getSpecialites_medicales());
            existingHospital.setLits_disponible(updatedHospital.getLits_disponible());
            existingHospital.setLongitude(updatedHospital.getLongitude());
            existingHospital.setLatitude(updatedHospital.getLatitude());

        hospitalRepository.save(existingHospital);

        return mapper.toHospitalResponse(existingHospital);        }

    public void  deleteHospital(Integer hospitalId) {
        Hospital existingHospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new EntityNotFoundException("Hopital non trouvé avec l'ID " + hospitalId));

        hospitalRepository.delete(existingHospital);
    }
}
