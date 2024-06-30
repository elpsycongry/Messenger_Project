package com.example.messenger.security;

import com.example.messenger.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @RequestBody User user) {
        try {
            User userSaved = authenticationService.register(user);
            String responseMessage = userSaved.getEmail() ;
            return ResponseEntity.ok(responseMessage);
        } catch (IllegalStateException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            response.put("email", user.getEmail());
            return ResponseEntity.badRequest().body(response);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> registerUser(
            @RequestBody AuthenticationRequest request) {
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(request);

        if (authenticationResponse == null) {
            return ResponseEntity.badRequest().body("Tài khoản chưa được xác thực");
        }

        return ResponseEntity.ok(authenticationResponse);
    }
}
