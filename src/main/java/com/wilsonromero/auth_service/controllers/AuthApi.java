package com.wilsonromero.auth_service.controllers;

import com.wilsonromero.auth_service.common.constants.ApiPathVariables;
import com.wilsonromero.auth_service.common.dtos.TokenResponse;
import com.wilsonromero.auth_service.common.dtos.UserRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(ApiPathVariables.V1_ROUTE + ApiPathVariables.AUTH_ROUTE)
public interface AuthApi {
    @PostMapping("/register")
    ResponseEntity<TokenResponse> createUser(@RequestBody @Valid UserRequest userRequest);

    @GetMapping
    ResponseEntity<Long> getUser(@RequestAttribute("X-User-Id") Long userId);
}
