package com.vule.security.dto.userfront;

public record UserfrontResponse(
        String mode,
        String message,
        String redirectTo,
        String sessionId,
        String nonce,
        Tokens tokens
) {
}
