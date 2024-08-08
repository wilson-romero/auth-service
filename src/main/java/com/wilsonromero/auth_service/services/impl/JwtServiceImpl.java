package com.wilsonromero.auth_service.services.impl;

import com.wilsonromero.auth_service.common.dtos.TokenResponse;
import com.wilsonromero.auth_service.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Service
public class JwtServiceImpl implements JwtService {
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    @Override
    public TokenResponse generateToken(Long userId) {
        long currentTimeMillis = System.currentTimeMillis();
        Date expirationDate = new Date(currentTimeMillis + 3600000);
        String token = Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(new Date(currentTimeMillis))
                .setExpiration(expirationDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        return TokenResponse.builder()
                .accessToken(token)
                .build();
    }

    @Override
    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public boolean isExpired(String token) {
        try {
            Claims claims = getClaims(token);
            return claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    @Override
    public Long extractUserId(String token) {
        Claims claims = getClaims(token);
        return Optional.of(Long.parseLong(claims.getSubject()))
                .orElseThrow(() -> new IllegalArgumentException("Invalid token"));
    }
}
