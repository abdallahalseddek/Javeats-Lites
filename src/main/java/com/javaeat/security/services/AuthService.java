package com.javaeat.security.services;

import com.javaeat.security.dto.*;

import java.security.Principal;

public interface AuthService {
    AuthResponse register(SignUpDto signUpDto);

    AuthResponse authenticate(AuthRequest authRequest);
    AuthResponse generateRefreshToken(TokenRequest tokenRequest);
    void changePassword(ChangePasswordRequest request, Principal connectedUser);
}
