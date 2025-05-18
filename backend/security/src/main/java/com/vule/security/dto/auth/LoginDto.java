package com.vule.security.dto.auth;

import jakarta.validation.constraints.NotNull;

import java.util.Map;

public record LoginDto(
        @NotNull String emailOrUsername,
        @NotNull String password,
        Map<String, Object> options
) {
}
