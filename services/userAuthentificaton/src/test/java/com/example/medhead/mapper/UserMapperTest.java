package com.example.medhead.mapper;


import com.example.medhead.model.Role;
import com.example.medhead.model.User;
import com.example.medhead.util.request.RegistrationRequest;
import com.example.medhead.util.response.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserMapperTest {


    //Declarer le service à tester
    private UserMapper mapper;

    //Intialiser l'objet mapper
    @BeforeEach
    void setUp() {
        mapper = new UserMapper();
    }


    @Test
    public void shouldMapRegestrationRequestToUser() {
        //creation d'un objet de type RegestrationRequest
        LocalDate dateNaissance = LocalDate.of(1999, 1, 1);

        RegistrationRequest request = new RegistrationRequest(1,
                "John",
                "Doe",
                dateNaissance,
                "JhonD@gmail.com",
                "Homme",
                "69 Rue de la liberté",
                "+3360904544",
                "123456789"

        );

        User user = mapper.toUser(request);

        assertEquals(request.getId(), user.getId());
        assertEquals(request.getNom(), user.getNom());
        assertEquals(request.getPrenom(), user.getPrenom());
        assertEquals(request.getDateNaissance(), user.getDateNaissance());
        assertEquals(request.getEmail(), user.getEmail());
        assertEquals(request.getSexe(), user.getSexe());
        assertEquals(request.getAdresse(), user.getAdresse());
        assertEquals(request.getNumero(), user.getNumero());
    }

    @Test
    public void shouldMapRegestrationRequestToUserWhenRegestrationRequestIsNull() {
        RegistrationRequest request = null;
        User user = mapper.toUser(request);

        //vérifier que la RegistrationRequest est null
        assertThat(user).isNull();
    }

    @Test
    public void shouldMapUserToUserResponse() {
        LocalDate dateNaissance = LocalDate.of(1999, 1, 1);

        //Given
        User user = User.builder()
                .id(2)
                .nom("Zantour")
                .prenom("Wael")
                .dateNaissance(dateNaissance)
                .email("wael1@gmail.com")
                .sexe("Homme")
                .adresse("33000 Bordeaux")
                .numero("33602559932")
                .password("123456789")
                .roles(Set.of(new Role(1, "Patient", null, null, null)))
                .accountLocked(true)
                .enabled(true)
                .build();

        //When
        UserResponse userResponse = mapper.fromUser(user);

        //Then
        assertEquals(user.getId(), userResponse.getId());
        assertEquals(user.getNom(), userResponse.getNom());
        assertEquals(user.getPrenom(), userResponse.getPrenom());
        assertEquals(user.getDateNaissance(), userResponse.getDateNaissance());
        assertEquals(user.getEmail(), userResponse.getEmail());
        assertEquals(user.getSexe(), userResponse.getSexe());
        assertEquals(user.getAdresse(), userResponse.getAdresse());
        assertEquals(user.getNumero(), userResponse.getNumero());
        assertEquals(user.getRoles().stream().map(Role::getNom).collect(Collectors.toList()), userResponse.getRoles());
        assertEquals(user.isEnabled(), userResponse.isEnabled());
        assertEquals(user.isAccountLocked(), userResponse.isAccountLocked());

    }

    //vérifier que la méthode retourne null quand le user est null
    @Test
    public void shouldMapUserToUserResponseWhenIsNull() {
        User user = null;
        UserResponse userResponse = mapper.fromUser(user);

        //vérifier que l'objet user est null
        assertThat(userResponse).isNull();
    }

}