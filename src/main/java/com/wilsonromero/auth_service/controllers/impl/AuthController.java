package com.wilsonromero.auth_service.controllers.impl;

import com.wilsonromero.auth_service.common.dtos.TokenResponse;
import com.wilsonromero.auth_service.common.dtos.UserRequest;
import com.wilsonromero.auth_service.controllers.AuthApi;
import com.wilsonromero.auth_service.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController implements AuthApi {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public ResponseEntity<TokenResponse> createUser(UserRequest userRequest) {
        return ResponseEntity.ok(authService.createUser(userRequest));
    }

    @Override
    public ResponseEntity<Long> getUser(Long userId) {
        return ResponseEntity.ok(userId);
    }
}
