package com.vule.workoutcalendar.user;

import com.vule.workoutcalendar.jwt.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository users;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserService(UserRepository users, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public String login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password())
        );

        return jwtService.generateJwt(authentication);
    }

    public ResponseEntity<?> create(User user) {
        Optional<User> existingUser = users.findExistingUser(user.getUsername(), user.getEmail());

        if (existingUser.isEmpty()) {
            users.save(new User(user.getUsername(), user.getEmail(), passwordEncoder.encode(user.getPassword())));
            return ResponseEntity.status(HttpStatus.CREATED).body("User created");
        }
        else {
            User foundUser = existingUser.get();
            if (foundUser.getEmail().equals(user.getEmail())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email is already taken");
            } else if (foundUser.getUsername().equals(user.getUsername())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Username is already taken");
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Phone number is already taken");
            }
        }
    }

    public User findUser(String username) {
        return users.findByUsername(username);
    }
}
