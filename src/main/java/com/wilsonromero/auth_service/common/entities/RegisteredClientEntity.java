package com.wilsonromero.auth_service.common.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "registered_client")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisteredClientEntity  {

    @Id
    @GeneratedValue
    private UUID id;

    private String clientId;
    private String clientSecret;
    private String roles;
    private String scopes;
}
