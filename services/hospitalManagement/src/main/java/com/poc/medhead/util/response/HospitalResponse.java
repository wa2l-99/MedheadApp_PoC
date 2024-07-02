package com.poc.medhead.util.response;

import com.poc.medhead.model.MedicalSpeciality;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HospitalResponse {

    private Integer id;

    private String nom_organisation;

    private String adresse;

    private String code_postal;

    private Set<MedicalSpeciality> specialites_medicales;

    private  Integer lits_disponible;

    private float longitude;

    private float latitude;

}
