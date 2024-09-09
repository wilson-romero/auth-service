package com.wilsonromero.auth_service.config;

import com.wilsonromero.auth_service.common.entities.RegisteredClientEntity;
import com.wilsonromero.auth_service.common.mappers.RegisteredClientMapper;
import com.wilsonromero.auth_service.repositories.RegisteredClientJpaRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Optional;
import java.util.UUID;

@Configuration
public class AuthorizationServerConfig {

    private final RegisteredClientJpaRepository jpaRegisteredClientJpaRepository;  // Repositorio JPA
    private final RegisteredClientMapper registeredClientMapper;

    public AuthorizationServerConfig(
            RegisteredClientJpaRepository jpaRegisteredClientJpaRepository,
            RegisteredClientMapper registeredClientMapper) {
        this.jpaRegisteredClientJpaRepository = jpaRegisteredClientJpaRepository;
        this.registeredClientMapper = registeredClientMapper;
    }

    @Bean
    public SecurityFilterChain authorizationServerFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        return http.build();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        return new JpaRegisteredClientRepositoryImpl();
    }

    // Implementación que carga y guarda los clientes usando JPA
    public class JpaRegisteredClientRepositoryImpl implements RegisteredClientRepository {

        @Override
        public RegisteredClient findById(String id) {
            Optional<RegisteredClientEntity> clientEntity = jpaRegisteredClientJpaRepository.findById(UUID.fromString(id));
            return clientEntity.map(registeredClientMapper::toRegisteredClient).orElse(null);
        }

        @Override
        public RegisteredClient findByClientId(String clientId) {
            Optional<RegisteredClientEntity> clientEntity = jpaRegisteredClientJpaRepository.findByClientId(clientId);
            return clientEntity.map(registeredClientMapper::toRegisteredClient).orElse(null);
        }

        // Implementación del método save
        @Override
        public void save(RegisteredClient registeredClient) {
            // Mapea el RegisteredClient a RegisteredClientEntity
            RegisteredClientEntity clientEntity = registeredClientMapper.toRegisteredClientEntity(registeredClient);
            // Guarda el cliente en la base de datos usando JPA
            jpaRegisteredClientJpaRepository.save(clientEntity);
        }
    }
}
