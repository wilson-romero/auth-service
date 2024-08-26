package com.wilsonromero.auth_service.common.mappers;

import com.wilsonromero.auth_service.common.entities.RegisteredClientEntity;
import com.wilsonromero.auth_service.services.EncryptionService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;

@Component
public class RegisteredClientMapper {

    private final EncryptionService encryptionService;

    public RegisteredClientMapper(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    public RegisteredClient toRegisteredClient(RegisteredClientEntity entity) {
        return RegisteredClient.withId(entity.getId().toString())
                .clientId(entity.getClientId())
                .clientSecret(entity.getClientSecret())
                .scopes(scopes -> {
                    String entityScopes = entity.getScopes();
                    if (entityScopes != null && !entityScopes.isEmpty()) {
                        scopes.addAll(Set.of(entityScopes.split(",")));
                    }
                })
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_JWT)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .clientSettings(ClientSettings.builder().build())
                .tokenSettings(TokenSettings.builder().build())
                .build();
    }

    public RegisteredClientEntity toRegisteredClientEntity(RegisteredClient registeredClient) {
        return RegisteredClientEntity.builder()
                .id(UUID.fromString(registeredClient.getId()))
                .clientId(registeredClient.getClientId())
                .clientSecret(encryptionService.encrypt(registeredClient.getClientSecret()))
                .scopes(String.join(",", registeredClient.getScopes()))
                .build();
    }
}
