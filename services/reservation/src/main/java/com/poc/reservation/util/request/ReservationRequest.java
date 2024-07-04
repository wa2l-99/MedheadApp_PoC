package com.poc.reservation.util.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public record ReservationRequest(

        Integer id,

        String reference,

        @NotNull(message = "le patient est obligatoitre")
        @NotBlank(message = "le patient est obligatoitre")
        @NotEmpty(message = "le patient est obligatoitre")
        Integer patientId,

        @NotNull(message = "l'hopital est obligatoitre")
        @NotBlank(message = "l'hopital est obligatoitre")
        @NotEmpty(message = "l'hopital est obligatoitre")
        Integer hospitalId
) {
}
