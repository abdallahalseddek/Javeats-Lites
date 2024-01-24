package com.javaeat.security.controller;

import com.javaeat.security.dto.AuthRequest;
import com.javaeat.security.dto.AuthResponse;
import com.javaeat.security.dto.SignUpDto;
import com.javaeat.security.dto.TokenRequest;
import com.javaeat.security.services.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication EndPoints")
public class AuthController {
    private final AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid SignUpDto signUpDto){
        return ResponseEntity.ok(authService.register(signUpDto));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody @Valid AuthRequest authRequest){
        return ResponseEntity.ok(authService.authenticate(authRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> generateRefreshToken(@RequestBody @Valid TokenRequest tokenRequest){
        return ResponseEntity.ok(authService.generateRefreshToken(tokenRequest));
    }
}
