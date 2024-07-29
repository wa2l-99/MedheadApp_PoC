package com.example.medhead.util.response;

import com.example.medhead.model.Role;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

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
    private List<String> roles;
    private boolean accountLocked;
    private boolean enabled;

}
