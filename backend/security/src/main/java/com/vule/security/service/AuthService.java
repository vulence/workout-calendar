package com.vule.security.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vule.security.config.exception.AuthenticationException;
import com.vule.security.dto.auth.LoginDto;
import com.vule.security.dto.auth.RegisterDto;
import com.vule.security.dto.userfront.UserfrontResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Service
public class AuthService {

    private final String userfrontApiUrl;
    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public AuthService(ObjectMapper objectMapper, @Value("${userfront.api.url}") String userfrontApiUrl) {
        this.userfrontApiUrl = userfrontApiUrl;
        this.restClient = RestClient.builder()
                .baseUrl(this.userfrontApiUrl)
                .build();
        this.objectMapper = objectMapper;
    }

    public UserfrontResponse login(LoginDto loginDto) {
        String body;
        try {
            body = objectMapper.writeValueAsString(loginDto);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException("Failed to serialize login body!");
        }

        ResponseEntity<UserfrontResponse> response;

        try {
            response = restClient.post()
                    .uri("/auth/password")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(body)
                    .retrieve()
                    .toEntity(UserfrontResponse.class);
        } catch (HttpClientErrorException e) {
            throw new AuthenticationException(e.getResponseBodyAsString(), e.getStatusCode());
        } catch (RestClientResponseException e) {
            throw new AuthenticationException("An error occured while processing the request.", HttpStatus.INTERNAL_SERVER_ERROR);
        }


        return response.getBody();
    }

    public ResponseEntity<Void> register(RegisterDto registerDto) {
        String body;
        try {
            body = objectMapper.writeValueAsString(registerDto);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException("Failed to serialize registration body!");
        }

        ResponseEntity<Void> response = restClient.post()
                .uri("/auth/create")
                .contentType(MediaType.APPLICATION_JSON)
                .body(body)
                .retrieve()
                .toBodilessEntity();

        return response;
    }
}
