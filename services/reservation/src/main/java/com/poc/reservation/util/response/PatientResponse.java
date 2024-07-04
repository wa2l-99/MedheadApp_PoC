package com.poc.reservation.util.response;

import java.time.LocalDate;

public record PatientResponse(

        Integer id,
        String nom,
        String prenom,
        LocalDate dateNaissance,
        String email,
        String sexe,
        String adresse,
        String numero

) {

}
