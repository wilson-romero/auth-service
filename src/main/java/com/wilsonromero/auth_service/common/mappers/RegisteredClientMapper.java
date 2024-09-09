package com.wilsonromero.auth_service.common.mappers;

import com.wilsonromero.auth_service.common.entities.RegisteredClientEntity;
import com.wilsonromero.auth_service.services.EncryptionService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class RegisteredClientMapper {

    private final EncryptionService encryptionService;

    public RegisteredClientMapper(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    // Convierte de RegisteredClientEntity a RegisteredClient (Entidad a Modelo)
    public RegisteredClient toRegisteredClient(RegisteredClientEntity entity) {
        return RegisteredClient.withId(entity.getId().toString())
                .clientId(entity.getClientId())
                .clientSecret(entity.getClientSecret())
                .scopes(scopes -> {
                    List<String> entityScopes = entity.getScopes();
                    if (entityScopes != null && !entityScopes.isEmpty()) {
                        // Convierte la lista de scopes en un Set<String>
                        scopes.addAll(entityScopes.stream()
                                .map(String::trim)
                                .collect(Collectors.toSet()));
                    }
                })
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_JWT)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .clientSettings(ClientSettings.builder().build())
                .tokenSettings(TokenSettings.builder().build())
                .build();
    }

    // Convierte de RegisteredClient a RegisteredClientEntity (Modelo a Entidad)
    public RegisteredClientEntity toRegisteredClientEntity(RegisteredClient registeredClient) {
        return RegisteredClientEntity.builder()
                .id(UUID.fromString(registeredClient.getId()))
                .clientId(registeredClient.getClientId())
                // Usa encryptionService para encriptar el clientSecret
                .clientSecret(encryptionService.encrypt(registeredClient.getClientSecret()))
                .scopes(registeredClient.getScopes().stream().toList())
                .build();
    }
}
