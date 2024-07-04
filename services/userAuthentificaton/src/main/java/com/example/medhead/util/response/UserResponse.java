package com.example.medhead.util.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private Integer id;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String email;
    private String sexe;
    private String adresse;
    private String numero;
    private boolean accountLocked;
    private boolean enabled;

}
