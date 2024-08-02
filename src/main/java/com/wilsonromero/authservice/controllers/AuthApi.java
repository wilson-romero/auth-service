package com.wilsonromero.authservice.controllers;

import com.wilsonromero.authservice.common.constants.ApiPathVariables;
import com.wilsonromero.authservice.common.dto.TokenResponse;
import com.wilsonromero.authservice.common.dto.UserRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(ApiPathVariables.V1_ROUTE + ApiPathVariables.AUTH_ROUTE)
public interface AuthApi {
    @PostMapping("/register")
    ResponseEntity<TokenResponse> createUser(@RequestBody @Valid UserRequest userRequest);
}
