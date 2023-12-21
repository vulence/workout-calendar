package com.vule.workoutcalendar.jwt;

import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    private final JwtDecoder jwtDecoder;

    public JwtService(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    public Integer parseUserIdFromJwt(String jwt) {
        return Integer.parseInt(jwtDecoder.decode(jwt).getClaims().get("userId").toString());
    }
}
