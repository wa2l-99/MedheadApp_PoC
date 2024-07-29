package com.example.medhead.util.response;

import com.example.medhead.model.User;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    
    private String token;
    private UserResponse user;
}


