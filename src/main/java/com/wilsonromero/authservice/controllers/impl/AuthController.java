package com.wilsonromero.authservice.controllers.impl;

import com.wilsonromero.authservice.common.dto.TokenResponse;
import com.wilsonromero.authservice.common.dto.UserRequest;
import com.wilsonromero.authservice.controllers.AuthApi;
import com.wilsonromero.authservice.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class  AuthController implements AuthApi {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public ResponseEntity<TokenResponse> createUser(UserRequest userRequest) {
        return ResponseEntity.ok(authService.createUser(userRequest) );
    }
}
