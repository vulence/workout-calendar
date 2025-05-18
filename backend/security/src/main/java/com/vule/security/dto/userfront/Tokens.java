package com.vule.security.dto.userfront;

public record Tokens(
        Token access,
        Token id,
        Token refresh
) {
}
