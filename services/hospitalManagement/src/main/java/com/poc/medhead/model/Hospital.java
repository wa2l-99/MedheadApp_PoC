package com.poc.medhead.model;

import jakarta.persistence.*;
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

    private String nom_organisation;

    private String adresse;

    private String code_postal;

    @ManyToMany
    @JoinTable(
            name = "hospital_speciality",
            joinColumns = @JoinColumn(name = "hospital_id"),
            inverseJoinColumns = @JoinColumn(name = "speciality_id")
    )
    private Set<MedicalSpeciality> specialites_medicales;

    private  Integer lits_disponible;

    private float longitude;

    private float latitude;

}
