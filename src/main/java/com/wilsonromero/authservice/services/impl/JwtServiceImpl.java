package com.wilsonromero.authservice.services.impl;

import com.wilsonromero.authservice.common.dto.TokenResponse;
import com.wilsonromero.authservice.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {
    private final String secretToken;

    public JwtServiceImpl(@Value("${jwt.secret}") String secretToken) {
        this.secretToken = secretToken;
    }

    @Override
    public TokenResponse generateToken(Long userId) {
        Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24);
        byte[] apiKeySecretBytes = Base64.getDecoder().decode(secretToken);
        Key key = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS512.getJcaName());

        String token  = Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(new Date())
                .setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
        return TokenResponse.builder()
                .accessToken(token)
                .build();
    }

    @Override
    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(this.secretToken)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public boolean isTokenExpired(String token) {
        try {
            Date expirationDate = getClaimsFromToken(token).getExpiration();
            return expirationDate != null && expirationDate.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }


    @Override
    public Long extractUserId(String token) {
        try {
            return Long.parseLong(getClaimsFromToken(token).getSubject());
        } catch (Exception e) {
            return null;
        }
    }
}
