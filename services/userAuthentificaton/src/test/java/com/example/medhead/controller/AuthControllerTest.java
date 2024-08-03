package com.example.medhead.controller;

import com.example.medhead.services.AuthService;
import com.example.medhead.util.request.AuthenticationRequest;
import com.example.medhead.util.request.RegistrationRequest;
import com.example.medhead.util.response.AuthenticationResponse;
import com.example.medhead.util.response.UserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules(); // Pour gérer LocalDate
    }

    @Test
    void testRegister() throws Exception {
        RegistrationRequest request = new RegistrationRequest();
        request.setNom("Dupont");
        request.setPrenom("Jean");
        request.setDateNaissance(LocalDate.of(1990, 1, 1));
        request.setEmail("jean.dupont@example.com");
        request.setSexe("Homme");
        request.setAdresse("123 Rue de la Paix, Paris");
        request.setNumero("0123456789");
        request.setPassword("password123");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isAccepted());

        verify(authService).register(any(RegistrationRequest.class));
    }

    @Test
    void testAuthenticate() throws Exception {
        LocalDate dateNaissance = LocalDate.of(1990, 1, 1);
        UserResponse userResponse = new UserResponse(
                2,
                "Wael",
                "Zantour",
                dateNaissance,
                "wael1@gmail.com",
                "Homme",
                "33000 Bordeaux",
                "33602559932",
                List.of("Patient"),
                true,
                true
        );
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("jean.dupont@example.com");
        request.setPassword("password123");

        AuthenticationResponse response = new AuthenticationResponse("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...", userResponse);

        when(authService.authenticate(any(AuthenticationRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."));

        verify(authService).authenticate(any(AuthenticationRequest.class));
    }
}
