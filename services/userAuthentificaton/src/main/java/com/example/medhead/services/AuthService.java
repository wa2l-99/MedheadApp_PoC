package com.example.medhead.services;

import com.example.medhead.Email.EmailService;
import com.example.medhead.Email.EmailTemplateName;
import com.example.medhead.dao.RoleRepository;
import com.example.medhead.dao.TokenRepository;
import com.example.medhead.dao.UserRepository;
import com.example.medhead.models.Token;
import com.example.medhead.models.User;
import com.example.medhead.util.request.RegistrationRequest;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final TokenRepository tokenRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final EmailService emailService;

    @Value("${spring.application.mailing.frontend.activation-url}")
    private String activationUrl;

    public void register (RegistrationRequest registrationRequest) throws MessagingException {

        var userRole = roleRepository.findByNom("Patient")
                .orElseThrow( () -> new IllegalStateException("Patient Role was not initialized"));
        var user = User.builder()
                .nom(registrationRequest.getNom())
                .prenom(registrationRequest.getPrenom())
                .dateNaissance(registrationRequest.getDateNaissance())
                .email(registrationRequest.getEmail())
                .sexe(registrationRequest.getSexe())
                .adresse(registrationRequest.getAdresse())
                .numero(registrationRequest.getNumero())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .roles(Set.of(userRole))
                .build();
        userRepository.save(user);
        sendValidationEmail(user);
    }

    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);
        emailService.sendEmail(
                user.getEmail(),
                user.fullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account Activation"

        );
    }

    private String generateAndSaveActivationToken(User user) {
        //generate a token
        String gneratedToken = generateActionCode(6);
        var token = Token.builder()
                .token(gneratedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(token);
        return gneratedToken;
    }

    private String generateActionCode(int length) {

        String characters="0123456789";

        StringBuilder codeBuilder = new StringBuilder(length);
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < length; i++) {

            int randomIndex = random.nextInt(characters.length());

            codeBuilder.append(characters.charAt(randomIndex));
        }
        return  codeBuilder.toString();
    }
}
