package com.poc.medhead.util.request;

import jakarta.validation.constraints.NotNull;

public record AddSpecialityToHospitalRequest (
        @NotNull(message = "L'identifiant de l'hopital ne doit pas être nul")
        Integer hospitalId,

        @NotNull(message = "L'identifiant de spécialité medicale ne doit pas être nul")
        Integer specialityId
) {

}
