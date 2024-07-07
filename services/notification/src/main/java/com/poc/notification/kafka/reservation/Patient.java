package com.poc.notification.kafka.reservation;

import java.time.LocalDate;

public record Patient(

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
