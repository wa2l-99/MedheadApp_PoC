package com.poc.reservation.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationProducer {

    private final KafkaTemplate<String, ReservationConfirmation> kafkaTemplate;

    public void sendReservationConfirmation(ReservationConfirmation reservationConfirmation){

        log.info("Sending reservation confirmation");
        Message<ReservationConfirmation> message = MessageBuilder
                .withPayload(reservationConfirmation)
                .setHeader(KafkaHeaders.TOPIC,"reservation-topic")
                .build();
        kafkaTemplate.send(message);

    }
}
