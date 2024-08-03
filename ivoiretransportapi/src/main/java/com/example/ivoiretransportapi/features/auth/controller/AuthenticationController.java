package com.example.ivoiretransportapi.features.auth.controller;

import com.example.ivoiretransportapi.features.auth.models.SigninRequest;
import com.example.ivoiretransportapi.features.auth.models.SigninResponse;
import com.example.ivoiretransportapi.features.auth.models.SignupRequest;
import com.example.ivoiretransportapi.features.auth.service.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
@Tag(name = "Authentication")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> register(
            @RequestBody @Valid SignupRequest request
    ) throws MessagingException {
        authenticationService.signup(request);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/signin")
    public ResponseEntity<SigninResponse> authenticate(
            @RequestBody SigninRequest request
    ) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }

    @GetMapping("/activate-account")
    public void confirm(
            @RequestParam String token
    ) throws MessagingException {
        authenticationService.activateAccount(token);
    }
}
