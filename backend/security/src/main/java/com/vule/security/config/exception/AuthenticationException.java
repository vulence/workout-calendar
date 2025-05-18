package com.vule.security.config.exception;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class AuthenticationException extends RuntimeException {

    private final HttpStatusCode statusCode;

    public AuthenticationException(String message, HttpStatusCode statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
