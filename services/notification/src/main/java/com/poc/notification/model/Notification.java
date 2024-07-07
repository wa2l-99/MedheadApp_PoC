package com.poc.notification.model;


import com.poc.notification.kafka.reservation.ReservationConfirmation;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class Notification {
    @Id
    private String id;
    private String title;
    private LocalDateTime notificationDate;
    private ReservationConfirmation reservationConfirmation;
}
