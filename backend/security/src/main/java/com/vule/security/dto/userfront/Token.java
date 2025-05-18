package com.vule.security.dto.userfront;

public record Token(
        String value,
        CookieOptions cookieOptions
) {
}
