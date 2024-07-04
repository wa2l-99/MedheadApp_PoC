package com.poc.reservation.util.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ReservationResponse(

        Integer id,
        String reference,
        Integer patientId,
        Integer hospitalId

) { }
