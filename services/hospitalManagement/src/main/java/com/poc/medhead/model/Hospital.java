package com.poc.medhead.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
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

    @Column(name = "nom_organisation")
    private String nomOrganisation;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "code_postal")
    private String codePostal;

    @ManyToMany
    @JoinTable(
            name = "hospital_speciality",
            joinColumns = @JoinColumn(name = "hospital_id"),
            inverseJoinColumns = @JoinColumn(name = "speciality_id")
    )
    @JsonManagedReference
    private Set<MedicalSpeciality> specialitesMedicales;

    @Column(name = "lits_disponible")
    private Integer litsDisponible;

    @Column(name = "longitude")
    private float longitude;

    @Column(name = "latitude")
    private float latitude;

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
    }
}
