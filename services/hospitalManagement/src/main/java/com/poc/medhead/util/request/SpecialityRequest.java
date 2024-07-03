package com.poc.medhead.util.request;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record SpecialityRequest (
        Integer id,

        @NotEmpty(message = "Le nom de la spécialité médicale ne doit pas être vide" )
        String nom

) {}
