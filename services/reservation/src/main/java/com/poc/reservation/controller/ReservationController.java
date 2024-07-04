package com.poc.reservation.controller;

import com.poc.medhead.util.response.PageResponse;
import com.poc.reservation.service.ReservationService;
import com.poc.reservation.util.request.ReservationRequest;
import com.poc.reservation.util.response.ReservationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/addReservation")
    public ResponseEntity<Integer> createReservation(
        @RequestBody @Valid ReservationRequest reservationRequest
        ) {
        return ResponseEntity.ok(reservationService.createReservation(reservationRequest));
    }

    @GetMapping()
    public  ResponseEntity<PageResponse<ReservationResponse>> findAllReservations(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ){
        return ResponseEntity.ok(reservationService.getAllReservations(page,size));
    }

    @GetMapping("/{reservation-id}")
    public ResponseEntity<ReservationResponse> findGetReservation(
            @PathVariable("reservation-id") Integer reservationId
    ){
        return  ResponseEntity.ok(reservationService.getReservationById(reservationId));
    }

    @DeleteMapping("/{reservation-id}")
    public ResponseEntity<Void> deleteGetReservation(
            @PathVariable("reservation-id") Integer reservationId
    ){
        reservationService.deleteReservation(reservationId);
        return  ResponseEntity.accepted().build();
    }
}
