package com.poc.notification.model;

import lombok.Getter;

public enum EmailTemplates {

    RESERVATION_CONFIRMATION("reservation-confirmation.html", "Réservation traitée avec succès"),
    ;

    @Getter
    private final String template;
    @Getter
    private final String subject;


    EmailTemplates(String template, String subject) {
        this.template = template;
        this.subject = subject;
    }
}
