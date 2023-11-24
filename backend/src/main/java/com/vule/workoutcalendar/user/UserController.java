package com.vule.workoutcalendar.user;

import com.vule.workoutcalendar.jwt.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;

    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        String jwtToken = userService.login(loginRequest);

        Cookie cookie = new Cookie("jwt", jwtToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(3600);

        response.addCookie(cookie);

        User user = userService.findUser(loginRequest.username());

        return ResponseEntity.ok(user);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@CookieValue(name = "jwt", required = false) String jwtToken, HttpServletResponse response) {
        if (jwtToken == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Not authenticated"));

        Cookie cookie = new Cookie("jwt", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);

        return ResponseEntity.ok().body(Map.of("message", "Logout successful."));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        return userService.create(user);
    }

    @GetMapping("/check-authentication")
    public ResponseEntity<?> checkAuthentication(@CookieValue(name = "jwt", required = false) String jwtToken) {
        if (jwtToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Not authenticated"));
        }

        User user = userService.findUser(jwtService.parseUsernameFromJwt(jwtToken));
        return ResponseEntity.ok(user);
    }
}
