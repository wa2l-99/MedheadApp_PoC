package com.poc.medhead.util.response;

import com.poc.medhead.model.MedicalSpeciality;
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
    private Set<MedicalSpeciality> specialitesMedicales;
    private Integer litsDisponible;
    private float longitude;
    private float latitude;
    private double distance;
}
