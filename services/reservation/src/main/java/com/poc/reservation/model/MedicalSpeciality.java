package com.poc.reservation.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicalSpeciality {
    private Integer id;
    private String nom;
}
