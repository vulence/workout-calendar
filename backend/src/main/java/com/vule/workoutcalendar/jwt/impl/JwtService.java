package com.vule.workoutcalendar.jwt.impl;

import com.vule.workoutcalendar.jwt.api.JwtServiceApi;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

@Service
public class JwtService implements JwtServiceApi {
    private final JwtDecoder jwtDecoder;

    public JwtService(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public Integer parseUserIdFromJwt(String jwtToken) {
        return Integer.parseInt(jwtDecoder.decode(jwtToken).getClaims().get("userId").toString());
    }
}
