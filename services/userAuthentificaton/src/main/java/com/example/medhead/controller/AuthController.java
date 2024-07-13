package com.example.medhead.controller;

import com.example.medhead.services.AuthService;
import com.example.medhead.util.request.AuthenticationRequest;
import com.example.medhead.util.request.RegistrationRequest;
import com.example.medhead.util.response.AuthenticationResponse;
import com.example.medhead.util.response.UserResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> register(@RequestBody @Valid RegistrationRequest request) throws MessagingException {
        service.register(request);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/authenticate")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest authenticationRequest) throws MessagingException {
        return ResponseEntity.ok(service.authenticate(authenticationRequest));

    }

    @GetMapping("/activate_account")
    public void activateAccount(@RequestParam String token) throws MessagingException {
        service.activateAccount(token);
    }

    @GetMapping()
    public ResponseEntity<List<UserResponse>> findAll() {
        return ResponseEntity.ok(service.findAllUsers());
    }


    @GetMapping("/{user-id}")
    public ResponseEntity<UserResponse> findById(
            @PathVariable("user-id") Integer userId
    ) {
        return ResponseEntity.ok(service.findById(userId));
    }

    @DeleteMapping("/{user-id}")
    public ResponseEntity<Void> delete(
            @PathVariable("user-id") Integer userId
    ) {
        service.deleteUser(userId);
        return ResponseEntity.accepted().build();
    }
}
