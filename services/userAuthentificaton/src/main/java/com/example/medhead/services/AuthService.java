package com.example.medhead.services;

import com.example.medhead.email.EmailService;
import com.example.medhead.email.EmailTemplateName;
import com.example.medhead.dao.RoleRepository;
import com.example.medhead.dao.TokenRepository;
import com.example.medhead.dao.UserRepository;
import com.example.medhead.exception.UserNotFoundException;
import com.example.medhead.mapper.UserMapper;
import com.example.medhead.model.Token;
import com.example.medhead.model.User;
import com.example.medhead.util.request.AuthenticationRequest;
import com.example.medhead.util.request.RegistrationRequest;
import com.example.medhead.util.response.AuthenticationResponse;
import com.example.medhead.util.response.UserResponse;
import jakarta.mail.MessagingException;
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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


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
    private final UserMapper mapper;

    @Value("${spring.application.mailing.frontend.activation-url}")
    private String activationUrl;

    public void register (RegistrationRequest registrationRequest) throws MessagingException {

        var userRole = roleRepository.findByNom("Patient")
                .orElseThrow( () -> new IllegalStateException("Le rôle de l'utilisateur n'a pas été initialisé"));
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
                .orElseThrow(() -> new RuntimeException("jeton non valide"));
        //si le token est déja expiré
        if(LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            sendValidationEmail(savedToken.getUser());
            throw new RuntimeException("Le jeton d'activation a expiré. Un nouveau jeton a été envoyé à la même adresse e-mail.");
        }
        var user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));
        user.setEnabled(true);
        userRepository.save(user);
        // valider le token
        savedToken.setValidatedAt(LocalDateTime.now());
        // mettre à jour le token validé
        tokenRepository.save(savedToken);

    }

    public List<UserResponse> findAllUsers() {
        return  this.userRepository.findAll()
                .stream()
                .map(mapper::fromUser)
                .collect(Collectors.toList());
    }

    public UserResponse findById(Integer id) {
        return this.userRepository.findById(id)
                .map(mapper::fromUser)
                .orElseThrow(() -> new UserNotFoundException(String.format("No user found with the provided ID: %s", id)));
    }

    public void deleteUser(Integer id) {
       userRepository.deleteById(id);
    }
}
