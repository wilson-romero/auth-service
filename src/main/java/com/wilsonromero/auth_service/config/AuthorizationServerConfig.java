package com.wilsonromero.auth_service.config;

import com.wilsonromero.auth_service.common.mappers.RegisteredClientMapper;
import com.wilsonromero.auth_service.repositories.JpaRegisteredClientRepository;
import com.wilsonromero.auth_service.common.entities.RegisteredClientEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Configuration
public class AuthorizationServerConfig {

    private final JpaRegisteredClientRepository jpaRegisteredClientRepository;
    private final RegisteredClientMapper registeredClientMapper;

    public AuthorizationServerConfig(JpaRegisteredClientRepository jpaRegisteredClientRepository, RegisteredClientMapper registeredClientMapper) {
        this.jpaRegisteredClientRepository = jpaRegisteredClientRepository;
        this.registeredClientMapper = registeredClientMapper;
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        return new RegisteredClientRepository() {
            @Override
            @Transactional
            public void save(RegisteredClient registeredClient) {
                RegisteredClientEntity entity = registeredClientMapper.toRegisteredClientEntity(registeredClient);
                jpaRegisteredClientRepository.save(entity);
            }

            @Override
            public RegisteredClient findById(String id) {
                return jpaRegisteredClientRepository.findById(UUID.fromString(id))
                        .map(registeredClientMapper::toRegisteredClient)
                        .orElse(null);
            }

            @Override
            public RegisteredClient findByClientId(String clientId) {
                return jpaRegisteredClientRepository.findByClientId(clientId)
                        .map(registeredClientMapper::toRegisteredClient)
                        .orElse(null);
            }
        };
    }
}
