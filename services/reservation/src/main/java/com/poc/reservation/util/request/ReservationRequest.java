package com.poc.reservation.util.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


public record ReservationRequest(

        Integer id,

        String reference,

        @NotNull(message = "le patient est obligatoitre")
        Integer patientId,

        @NotNull(message = "l'hopital est obligatoitre")
        Integer hospitalId
) {
}
