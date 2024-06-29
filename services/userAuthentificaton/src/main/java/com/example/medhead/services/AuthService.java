package com.example.medhead.services;

import com.example.medhead.dao.RoleRepository;
import com.example.medhead.dao.TokenRepository;
import com.example.medhead.dao.UserRepository;
import com.example.medhead.models.Token;
import com.example.medhead.models.User;
import com.example.medhead.util.request.RegistrationRequest;
import lombok.RequiredArgsConstructor;
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

    public void register (RegistrationRequest registrationRequest) {

        var userRole = roleRepository.findByName("Patient")
                //
                .orElseThrow( () -> new IllegalStateException("Patient Role was not initilaized"));
        var user = User.builder()
                .nom(registrationRequest.getNom())
                .prenom(registrationRequest.getPrenom())
                .dateNaissance(registrationRequest.getDateNaissance())
                .email(registrationRequest.getEmail())
                .sexe(registrationRequest.getSexe())
                .adresse(registrationRequest.getAdresse())
                .adresse(registrationRequest.getNumero())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .roles(Set.of(userRole))
                .build();
        userRepository.save(user);
        sendValidationEmail(user);
    }

    private void sendValidationEmail(User user) {
        var newToken = generateAndSaveActivationToken(user);
        //send email
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
