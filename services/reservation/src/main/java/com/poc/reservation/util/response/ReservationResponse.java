package com.poc.reservation.util.response;


public record ReservationResponse(

        Integer id,
        String reference,
        Integer patientId,
        Integer hospitalId

) { }
