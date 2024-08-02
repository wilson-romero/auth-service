package com.wilsonromero.authservice.common.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    @NotNull
    private String email;
    @NotNull
    private String password;
}
