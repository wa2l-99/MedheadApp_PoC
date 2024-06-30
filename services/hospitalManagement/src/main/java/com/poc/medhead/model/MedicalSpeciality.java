package com.poc.medhead.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "medical_speciality" )
public class MedicalSpeciality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(unique = true)
    private String nom;

    @ManyToMany(mappedBy = "specialites_medicales")
    private Set<Hospital> hospital;
}
