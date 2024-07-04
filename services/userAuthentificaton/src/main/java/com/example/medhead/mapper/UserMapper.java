package com.example.medhead.mapper;

import com.example.medhead.model.User;
import com.example.medhead.util.request.RegistrationRequest;
import com.example.medhead.util.response.UserResponse;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public User toUser (RegistrationRequest request) {
        if (request == null){
            return null;
        }
        return User.builder()
                .id(request.getId())
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .dateNaissance(request.getDateNaissance())
                .email(request.getEmail())
                .sexe(request.getSexe())
                .adresse(request.getAdresse())
                .numero(request.getNumero())
                .build();
    }

    public UserResponse fromUser(User user){
        if(user == null){
            return null;
        }
        return UserResponse.builder()
                .id(user.getId())
                .nom(user.getNom())
                .prenom(user.getPrenom())
                .dateNaissance(user.getDateNaissance())
                .email(user.getEmail())
                .sexe(user.getSexe())
                .adresse(user.getAdresse())
                .numero(user.getNumero())
                .enabled(user.isEnabled())
                .accountLocked(user.isAccountLocked())
                .build();
    }
}
