package com.vule.security.dto.auth;

import jakarta.validation.constraints.NotNull;

import java.util.Map;

public record RegisterDto(
        @NotNull String email,
        @NotNull String password,
        String username,
        String name,
        Map<String, Object> data,
        boolean isMfaRequired,
        Map<String, Object> options
) {
}
