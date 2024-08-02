package com.wilsonromero.authservice.services;

import com.wilsonromero.authservice.common.dto.TokenResponse;
import com.wilsonromero.authservice.common.dto.UserRequest;

public interface AuthService {
    TokenResponse createUser(UserRequest userRequest);
}
