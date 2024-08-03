package com.example.medhead.services;

import com.example.medhead.dao.RoleRepository;
import com.example.medhead.dao.TokenRepository;
import com.example.medhead.dao.UserRepository;
import com.example.medhead.email.EmailService;
import com.example.medhead.email.EmailTemplateName;
import com.example.medhead.exception.UserNotFoundException;
import com.example.medhead.mapper.UserMapper;
import com.example.medhead.model.Role;
import com.example.medhead.model.Token;
import com.example.medhead.model.User;
import com.example.medhead.util.request.AuthenticationRequest;
import com.example.medhead.util.request.RegistrationRequest;
import com.example.medhead.util.response.AuthenticationResponse;
import com.example.medhead.util.response.UserResponse;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldRegisterUser() throws MessagingException {
        RegistrationRequest request = new RegistrationRequest(1, "John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@gmail.com", "Homme", "33000 Bordeaux", "1234567890", "password");

        Role role = new Role();
        role.setNom("Patient");

        when(roleRepository.findByNom("Patient")).thenReturn(Optional.of(role));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        String activationUrl = "http://localhost:4200/activate-account";
        ReflectionTestUtils.setField(authService, "activationUrl", activationUrl);

        authService.register(request);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertThat(savedUser.getEmail()).isEqualTo("john.doe@gmail.com");
        assertThat(savedUser.getRoles()).contains(role);

        verify(emailService).sendEmail(
                eq(savedUser.getEmail()),
                eq(savedUser.fullName()),
                eq(EmailTemplateName.ACTIVATE_ACCOUNT),
                eq("http://localhost:4200/activate-account"),
                anyString(),
                eq("Account Activation")
        );
    }

    @Test
    void shouldAuthenticateUser() {
        AuthenticationRequest request = new AuthenticationRequest("john.doe@gmail.com", "password");

        User user = new User();
        user.setEmail("john.doe@gmail.com");

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtService.generateToken(anyMap(), any(User.class))).thenReturn("jwtToken");

        AuthenticationResponse response = authService.authenticate(request);

        assertThat(response.getToken()).isEqualTo("jwtToken");
    }
}
