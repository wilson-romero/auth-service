package com.wilsonromero.auth_service.services;

import com.wilsonromero.auth_service.common.dtos.TokenResponse;
import com.wilsonromero.auth_service.common.dtos.UserRequest;

public interface AuthService {
    TokenResponse createUser(UserRequest userRequest);
}
