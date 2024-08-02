package com.wilsonromero.authservice.services;

import com.wilsonromero.authservice.common.dto.TokenResponse;
import io.jsonwebtoken.Claims;

public interface JwtService {
    TokenResponse generateToken(Long userId);
    Claims getClaimsFromToken(String token);
    boolean isTokenExpired(String token);
    Long  extractUserId(String token);
}
