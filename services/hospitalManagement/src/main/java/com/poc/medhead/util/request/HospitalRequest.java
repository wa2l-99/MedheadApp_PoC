package com.poc.medhead.util.request;

import com.poc.medhead.model.MedicalSpeciality;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class HospitalRequest {

        private Integer id;

        @NotEmpty(message = "Le nom de l'organisation ne doit pas être vide")
        @Size(max = 255, message = "Le nom de l'organisation ne doit pas dépasser 255 caractères")
        private String nomOrganisation;

        @NotNull(message = "L'adresse ne doit pas être nulle")
        @Size(max = 255, message = "L'adresse ne doit pas dépasser 255 caractères")
        private String adresse;

        @NotEmpty(message = "Le code postal ne doit pas être vide")
        @Pattern(regexp = "^[A-Z0-9]{5}$", message = "Le code postal doit être composé de 5 caractères alphanumériques en majuscules")
        private String codePostal;

        @NotNull(message = "Le nombre de lits disponibles ne doit pas être nul")
        @Positive(message = "Le nombre de lits doit être positif")
        private Integer litsDisponible;

        @NotNull(message = "La longitude ne doit pas être nulle")
        private Float longitude;

        @NotNull(message = "La latitude ne doit pas être nulle")
        private Float latitude;

        @NotNull(message = "La spécialité de l'hôpital ne doit pas être nulle")
        private Set<Integer> specialiteIds;

        private LocalDateTime createdDate;

}
