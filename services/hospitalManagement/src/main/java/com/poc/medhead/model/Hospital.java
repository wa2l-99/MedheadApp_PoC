package com.poc.medhead.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hospital")
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    private String nom_organisation;

    @NotNull
    private String adresse;

    @NotEmpty
    @Column(unique = true, columnDefinition = "VARCHAR(5)")
    @Pattern(regexp = "^[A-Z0-9]{5}$")
    private int code_postal;

    @ManyToMany
    @JoinTable(
            name = "hospital_speciality",
            joinColumns = @JoinColumn(name = "hospital_id"),
            inverseJoinColumns = @JoinColumn(name = "speciality_id")
    )
    private Set<MedicalSpeciality> specialites_medicales;

    private  int lits_disponible;

    private float longitude;

    private float latitude;



}
