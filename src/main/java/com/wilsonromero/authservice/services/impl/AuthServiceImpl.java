package com.wilsonromero.authservice.services.impl;

import com.wilsonromero.authservice.common.dto.TokenResponse;
import com.wilsonromero.authservice.common.dto.UserRequest;
import com.wilsonromero.authservice.common.entities.UserModel;
import com.wilsonromero.authservice.repositories.UserRepository;
import com.wilsonromero.authservice.services.AuthService;
import com.wilsonromero.authservice.services.JwtService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthServiceImpl(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    public TokenResponse createUser(UserRequest userRequest) {
        return Optional.of(userRequest)
                .map(this::mapToEntity)
                .map(userRepository::save)
                .map(userCreated -> jwtService.generateToken(userCreated.getId()))
                .orElseThrow( () -> new RuntimeException("Error creating user"));
    }

    private UserModel mapToEntity(UserRequest userRequest) {
        return UserModel.builder()
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .role("USER")
                .build();
    }
}
