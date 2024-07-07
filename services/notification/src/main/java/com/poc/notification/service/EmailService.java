package com.poc.notification.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.poc.notification.model.EmailTemplates.RESERVATION_CONFIRMATION;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private  final JavaMailSender mailSender;

    private final SpringTemplateEngine templateEngine;

    //Quand on recoit une notification et on veut envoyer un email, on ne veut pas bloquer
    //tous le process jusqu'a l'email sera envoyé
    @Async
    public  void sendReservationEmail(
            String destinationEmail,
            String patientName,
            String hospitalName,
            String reservationReference
    ) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper =
                new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_RELATED, StandardCharsets.UTF_8.name());
        messageHelper.setFrom("contact@medhead.org");

        //Create the template email
        final String templateName = RESERVATION_CONFIRMATION.getTemplate();

        //pass parameters to the template email
        Map<String, Object> variables = new HashMap<>();

        variables.put("destinationEmail",destinationEmail);
        variables.put("patientName", patientName);
        variables.put("hospitalName",hospitalName);
        variables.put("reservationReference",reservationReference);

        Context context = new Context();
        context.setVariables(variables);
        messageHelper.setText(RESERVATION_CONFIRMATION.getSubject(), true);

        try{
            String htmlTemplate = templateEngine.process(templateName, context);
            messageHelper.setText(htmlTemplate, true);
            messageHelper.setTo(destinationEmail);
            mailSender.send(mimeMessage);
            log.info(String.format("INFO - L'email a été envoyé avec succès à %s avec le modèle %s", destinationEmail, templateName));
        }catch (MessagingException e){
            log.warn("WARNING - Cannot send email to {}", destinationEmail );
        }
        mailSender.send(mimeMessage);
    }
}
