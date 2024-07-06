package com.poc.reservation.kafka;

import com.poc.reservation.util.response.HospitalResponse;
import com.poc.reservation.util.response.PatientResponse;

public record ReservationConfirmation(

        String reservationReference,

        PatientResponse patient,

        HospitalResponse hospital
) {

}
