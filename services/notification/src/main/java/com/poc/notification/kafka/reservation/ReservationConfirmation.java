package com.poc.notification.kafka.reservation;

public record ReservationConfirmation(

        String reservationReference,

        Patient patient,

        Hospital hospital
) { }
