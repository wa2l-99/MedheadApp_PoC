package com.poc.medhead.util.request;

import com.poc.medhead.model.MedicalSpeciality;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record HospitalRequest(
        @NotNull(message = "L'identifiant ne doit pas être nul")
        Integer id,

        @NotEmpty(message = "Le nom de l'organisation ne doit pas être vide")
        @Size(max = 255, message = "Le nom de l'organisation ne doit pas dépasser 255 caractères")
        String nom_organisation,

        @NotNull(message = "L'adresse ne doit pas être nulle")
        @Size(max = 255, message = "L'adresse ne doit pas dépasser 255 caractères")
        String adresse,

        @NotEmpty(message = "Le code postal ne doit pas être vide")
        @Pattern(regexp = "^[A-Z0-9]{5}$", message = "Le code postal doit être composé de 5 caractères alphanumériques en majuscules")
        String code_postal,

        @NotNull(message = "Le nombre de lits disponibles ne doit pas être nul")
        Integer lits_disponible,

        @NotNull(message = "La longitude ne doit pas être nulle")
        Float longitude,

        @NotNull(message = "La latitude ne doit pas être nulle")
        Float latitude,

        @NotNull(message = "La spécialité de l'hôpital ne doit pas être nulle")
        Set<MedicalSpeciality> specialites_medicales

) {}
