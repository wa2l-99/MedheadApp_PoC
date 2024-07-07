package com.poc.notification.service;

import com.poc.notification.dao.NotificationRepository;
import com.poc.notification.kafka.reservation.ReservationConfirmation;
import com.poc.notification.model.Notification;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationRepository notificationRepository;
    private final EmailService emailService;

    //1- persist the data ( save the notification)
    @KafkaListener(topics = "reservation-topic")
    public void consumerReservationConfirmationNotification(ReservationConfirmation reservationConfirmation) throws MessagingException {
        log.info(format("Consuming the message from reservation-topic:: %s", reservationConfirmation));
        notificationRepository.save(
                Notification.builder()
                        .title("Reservation Confirmation")
                        .notificationDate(LocalDateTime.now())
                        .build()
        );
        //2- send the email
        var patientName = reservationConfirmation.patient().nom() + " " + reservationConfirmation.patient().prenom();
        var hospitalName = reservationConfirmation.hospital().nomOrganisation();
        var patientEmail = reservationConfirmation.patient().email();

        emailService.sendReservationEmail(
                patientEmail,
                patientName,
                hospitalName,
                reservationConfirmation.reservationReference()
        );
    }


}
