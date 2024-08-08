package com.wilsonromero.auth_service.services;

import com.wilsonromero.auth_service.common.dtos.TokenResponse;
import io.jsonwebtoken.Claims;

public interface JwtService {
    TokenResponse generateToken(Long userId);
    Claims getClaims(String token);
    boolean isExpired(String token);
    Long extractUserId(String token);
}
