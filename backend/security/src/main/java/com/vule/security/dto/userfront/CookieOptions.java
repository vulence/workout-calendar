package com.vule.security.dto.userfront;

public record CookieOptions(
        boolean secure,
        String sameSite,
        long expires
) {
}
