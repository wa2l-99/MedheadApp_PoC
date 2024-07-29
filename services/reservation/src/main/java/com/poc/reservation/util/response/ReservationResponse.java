package com.poc.reservation.util.response;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationResponse {
    private Integer id;
    private String reference;
    private PatientResponse patient;
    private HospitalResponse hospital;

}
