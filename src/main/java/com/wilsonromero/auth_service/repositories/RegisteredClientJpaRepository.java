package com.wilsonromero.auth_service.repositories;

import com.wilsonromero.auth_service.common.entities.RegisteredClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RegisteredClientJpaRepository extends JpaRepository<RegisteredClientEntity, UUID> {
    Optional<RegisteredClientEntity> findByClientId(String clientId);
}
