package com.poc.reservation.util.response;

import lombok.Data;

import java.util.Set;

@Data
public class HospitalResponse {
    private Integer id;
    private String nomOrganisation;
    private String adresse;
    private String codePostal;
    private Integer litsDisponible;
    private float longitude;
    private float latitude;
    private Set<String> specialitesMedicales;
}
