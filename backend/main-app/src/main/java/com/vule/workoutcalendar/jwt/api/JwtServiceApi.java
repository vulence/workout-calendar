package com.vule.workoutcalendar.jwt.api;

public interface JwtServiceApi {

    Integer parseUserIdFromJwt(String jwtToken);
}
