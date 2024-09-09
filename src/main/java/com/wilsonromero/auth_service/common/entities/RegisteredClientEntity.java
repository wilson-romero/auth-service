package com.wilsonromero.auth_service.common.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "registered_client")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisteredClientEntity  {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(updatable = false, nullable = false)
    private UUID id;  // UUID que se generará automáticamente

    @Column(unique = true)
    private String clientId;       // ID del cliente

    private String clientSecret;   // Secreto del cliente (encriptado)

    @ElementCollection(fetch = FetchType.EAGER) // Almacenamiento de roles como lista
    @CollectionTable(name = "client_roles", joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "role")
    private List<String> roles;    // Roles como lista de Strings

    @ElementCollection(fetch = FetchType.EAGER) // Almacenamiento de scopes como lista
    @CollectionTable(name = "client_scopes", joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "scope")
    private List<String> scopes;   // Scopes como lista de Strings

    @ElementCollection(fetch = FetchType.EAGER) // Almacenamiento de grant types como lista
    @CollectionTable(name = "client_grant_types", joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "grant_type")
    private List<String> grantTypes; // Tipos de autenticación como lista
}
