package com.poc.reservation.service;

import com.poc.medhead.util.response.PageResponse;
import com.poc.reservation.client.HospitalClient;
import com.poc.reservation.client.PatientClient;
import com.poc.reservation.dao.ReservationRepository;
import com.poc.reservation.exception.AlreadyExistsException;
import com.poc.reservation.exception.BusinessException;
import com.poc.reservation.kafka.ReservationConfirmation;
import com.poc.reservation.kafka.ReservationProducer;
import com.poc.reservation.mapper.ReservationMapper;
import com.poc.reservation.model.Reservation;
import com.poc.reservation.util.request.ReservationRequest;
import com.poc.reservation.util.response.HospitalResponse;
import com.poc.reservation.util.response.PatientResponse;
import com.poc.reservation.util.response.ReservationResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final PatientClient patientClient;
    private final HospitalClient hospitalClient;
    private final ReservationRepository reservationRepository;
    private final ReservationMapper mapper;
    private final ReservationProducer reservationProducer;

    public ReservationResponse createReservation(ReservationRequest reservationRequest) {

        var reservation = mapper.toReservation(reservationRequest);

        //check the patient --> OpenFiegn
        var patient = this.patientClient.findPatientById(reservationRequest.patientId()).orElseThrow(() -> new BusinessException("Impossible de créer une réservation: : Aucune réservation n'existe avec l'identifiant fourni"));

        //check the hospital
        var hospital = this.hospitalClient.findHospitalById(reservationRequest.hospitalId()).orElseThrow(() -> new BusinessException("Impossible de créer une réservation: : Aucun hôpital n'existe avec l'identifiant fourni "));

        // Check bed availability
        if (hospital.getLitsDisponible() <= 0) {
            throw new BusinessException("Impossible de créer une réservation: Aucun lit disponible dans cet hôpital");
        }

        if (reservationRepository.existsReservationByPatientIdAndHospitalId(patient.id(), hospital.getId())) {
            throw new AlreadyExistsException("Une réservation a déjà été effectuée pour l'hôpital " + hospital.getNomOrganisation() + " par le patient : " + patient.nom() + " " + patient.prenom());

        }

        // Create the reservation
        reservationRepository.save(reservation);

        // Update the number of available beds
        hospitalClient.updateBeds(reservationRequest.hospitalId(), hospital.getLitsDisponible() - 1);

            reservationProducer.sendReservationConfirmation(new ReservationConfirmation(reservation.getReference(), patient, hospital));

        return mapper.toReservationResponse(reservation, patient, hospital);
    }

    public PageResponse<ReservationResponse> getAllReservations(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

        Page<Reservation> reservations = reservationRepository.findAll(pageable);

        List<ReservationResponse> reservationResponses = reservations.stream().map(reservation -> {
            // Récupérez les PatientResponse et HospitalResponse correspondants
            PatientResponse patientResponse = this.patientClient.findPatientById(reservation.getPatientId()).orElseThrow(() -> new BusinessException("Patient non trouvé avec l'id : " + reservation.getPatientId()));
            HospitalResponse hospitalResponse = this.hospitalClient.findHospitalById(reservation.getHospitalId()).orElseThrow(() -> new BusinessException("Hôpital non trouvé avec l'id : " + reservation.getHospitalId()));

            // Utilisez la méthode de mappage complète
            return mapper.toReservationResponse(reservation, patientResponse, hospitalResponse);
        }).collect(Collectors.toList());

        return new PageResponse<>(reservationResponses, reservations.getNumber(), reservations.getSize(), reservations.getTotalElements(), reservations.getTotalPages(), reservations.isFirst(), reservations.isLast());
    }


    public ReservationResponse getReservationById(Integer reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new EntityNotFoundException("Réservation non trouvée avec l'ID: " + reservationId));

        // Récupérer les détails du patient
        PatientResponse patientResponse = this.patientClient.findPatientById(reservation.getPatientId()).orElseThrow(() -> new BusinessException("Patient non trouvé avec l'id : " + reservation.getPatientId()));

        // Récupérer les détails de l'hôpital
        HospitalResponse hospitalResponse = this.hospitalClient.findHospitalById(reservation.getHospitalId()).orElseThrow(() -> new BusinessException("Hôpital non trouvé avec l'id : " + reservation.getHospitalId()));

        // Mapper la réservation avec les informations du patient et de l'hôpital
        return mapper.toReservationResponse(reservation, patientResponse, hospitalResponse);
    }


    public void deleteReservation(Integer reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new EntityNotFoundException("Réservation non trouvée avec l'ID: " + reservationId));

        reservationRepository.delete(reservation);
    }

}
