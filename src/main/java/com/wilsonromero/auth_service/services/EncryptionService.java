package com.wilsonromero.auth_service.services;

public interface EncryptionService {
    String encrypt(String rawSecret) ;
    boolean matches(String rawSecret, String encryptedSecret);
}
