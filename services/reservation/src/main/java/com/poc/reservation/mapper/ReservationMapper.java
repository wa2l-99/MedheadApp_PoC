package com.poc.reservation.mapper;

import com.poc.reservation.model.Reservation;
import com.poc.reservation.util.request.ReservationRequest;
import com.poc.reservation.util.response.HospitalResponse;
import com.poc.reservation.util.response.PatientResponse;
import com.poc.reservation.util.response.ReservationResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReservationMapper {

    public Reservation toReservation (ReservationRequest request){

        if (request == null){
            return null;
        }
        return Reservation.builder()
                .id(request.id())
                .reference(UUID.randomUUID().toString())
                .patientId(request.patientId())
                .hospitalId(request.hospitalId())
                .build();
    }

    public ReservationResponse toReservationResponse(Reservation reservation, PatientResponse patientResponse, HospitalResponse hospitalResponse) {
        ReservationResponse response = new ReservationResponse();
        response.setId(reservation.getId());
        response.setReference(reservation.getReference());
        response.setPatient(patientResponse);
        response.setHospital(hospitalResponse);
        return response;
    }

    public ReservationResponse toSimpleReservationResponse(Reservation reservation) {
        ReservationResponse response = new ReservationResponse();
        response.setId(reservation.getId());
        response.setReference(reservation.getReference());
        return response;
    }
}