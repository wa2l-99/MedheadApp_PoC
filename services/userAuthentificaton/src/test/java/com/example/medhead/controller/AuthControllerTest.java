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
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
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


        mockMvc.perform(post("/auth/register")
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

        mockMvc.perform(post("/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."));

        verify(authService).authenticate(any(AuthenticationRequest.class));
    }

    @Test
    void testActivateAccount() throws Exception {
        String token = "activationToken123";

        mockMvc.perform(get("/auth/activate_account")
                        .param("token", token))
                .andExpect(status().isOk());

        verify(authService).activateAccount(token);
    }

    @Test
    void testFindAll() throws Exception {
        UserResponse user1 = new UserResponse();
        user1.setId(1);
        user1.setNom("Dupont");
        user1.setPrenom("Jean");
        user1.setDateNaissance(LocalDate.of(1990, 1, 1));
        user1.setEmail("jean.dupont@example.com");
        user1.setSexe("M");
        user1.setAdresse("123 Rue de la Paix, Paris");
        user1.setNumero("0123456789");
        user1.setRoles(List.of("Patient"));
        user1.setEnabled(true);
        user1.setAccountLocked(false);

        UserResponse user2 = new UserResponse();
        user2.setId(2);
        user2.setNom("Martin");
        user2.setPrenom("Marie");
        user2.setDateNaissance(LocalDate.of(1992, 5, 15));
        user2.setEmail("marie.martin@example.com");
        user2.setSexe("F");
        user2.setAdresse("456 Avenue des Champs-Élysées, Paris");
        user2.setNumero("0987654321");
        user2.setRoles(List.of("Patient"));
        user2.setEnabled(true);
        user2.setAccountLocked(false);

        List<UserResponse> users = Arrays.asList(user1, user2);
        when(authService.findAllUsers()).thenReturn(users);

        mockMvc.perform(get("/auth"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nom").value("Dupont"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nom").value("Martin"));

        verify(authService).findAllUsers();
    }

    @Test
    void testFindById() throws Exception {
        Integer userId = 1;
        UserResponse user = new UserResponse();
        user.setId(userId);
        user.setNom("Dupont");
        user.setPrenom("Jean");
        user.setDateNaissance(LocalDate.of(1990, 1, 1));
        user.setEmail("jean.dupont@example.com");
        user.setSexe("M");
        user.setAdresse("123 Rue de la Paix, Paris");
        user.setNumero("0123456789");
        user.setRoles(List.of("Patient"));
        user.setEnabled(true);
        user.setAccountLocked(false);

        when(authService.findById(userId)).thenReturn(user);

        mockMvc.perform(get("/auth/{user-id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.nom").value("Dupont"))
                .andExpect(jsonPath("$.prenom").value("Jean"))
                .andExpect(jsonPath("$.email").value("jean.dupont@example.com"));


        verify(authService).findById(userId);
    }

    @Test
    void testDelete() throws Exception {
        Integer userId = 1;

        mockMvc.perform(delete("/auth/{user-id}", userId))
                .andExpect(status().isAccepted());

        verify(authService).deleteUser(userId);
    }
}