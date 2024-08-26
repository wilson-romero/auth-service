package com.wilsonromero.auth_service.services.impl;

import com.wilsonromero.auth_service.services.EncryptionService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BCryptEncryptionServiceImpl implements EncryptionService {
    private final PasswordEncoder passwordEncoder;

    public BCryptEncryptionServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encrypt(String rawSecret) {
        return passwordEncoder.encode(rawSecret);
    }

    @Override
    public boolean matches(String rawSecret, String encryptedSecret) {
        return passwordEncoder.matches(rawSecret, encryptedSecret);
    }
}
