package com.javaeat.security.controller;

import com.javaeat.security.dto.*;
import com.javaeat.security.services.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

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

    @PostMapping("/generateToken")
    public ResponseEntity<AuthResponse> generateRefreshToken(@RequestBody @Valid TokenRequest tokenRequest){
        return ResponseEntity.ok(authService.generateRefreshToken(tokenRequest));
    }
    @PatchMapping("/changepassword")
    public ResponseEntity<?> changePassword(@RequestBody @Valid ChangePasswordRequest request,@RequestBody @Valid Principal user){
        authService.changePassword(request,user);
        return ResponseEntity.ok().build();
    }
}
