package com.poc.reservation.util.response;

import com.poc.reservation.model.MedicalSpeciality;
import lombok.*;

import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HospitalResponse {
    private Integer id;
    private String nomOrganisation;
    private String adresse;
    private String codePostal;
    private Integer litsDisponible;
    private float longitude;
    private float latitude;
    private Set<MedicalSpeciality> specialitesMedicales;
}