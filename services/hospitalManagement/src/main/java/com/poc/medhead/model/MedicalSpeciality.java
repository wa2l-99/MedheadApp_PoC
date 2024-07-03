package com.poc.medhead.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "medical_speciality")
public class MedicalSpeciality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nom;

    @ManyToMany(mappedBy = "specialitesMedicales")
    @JsonBackReference
    private Set<Hospital> hospitals;
}
