package com.example.medhead.services;

import com.example.medhead.Email.EmailService;
import com.example.medhead.Email.EmailTemplateName;
import com.example.medhead.dao.RoleRepository;
import com.example.medhead.dao.TokenRepository;
import com.example.medhead.dao.UserRepository;
import com.example.medhead.models.Token;
import com.example.medhead.models.User;
import com.example.medhead.util.request.AuthenticationRequest;
import com.example.medhead.util.request.RegistrationRequest;
import com.example.medhead.util.response.AuthenticationResponse;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final TokenRepository tokenRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

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
                .accountLocked(false)
                .enabled(false)
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


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var claims = new HashMap<String, Object>();
        var user = ((User) auth.getPrincipal());
        claims.put("fullName", user.getFullName());

        var jwtToken = jwtService.generateToken(claims, (User) auth.getPrincipal());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    //@Transactional
    public void activateAccount(String token) throws MessagingException {
        // récuperer le token de la bdd
        Token savedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("invalid token"));
        //si le token est déja expiré
        if(LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            sendValidationEmail(savedToken.getUser());
            throw new RuntimeException("Activation token has expired. A new token has been sent to the same email adress");
        }
        var user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setEnabled(true);
        userRepository.save(user);
        // valider le token
        savedToken.setValidatedAt(LocalDateTime.now());
        // mettre à jour le token validé
        tokenRepository.save(savedToken);

    }
}
