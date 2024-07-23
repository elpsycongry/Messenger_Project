package com.example.messenger.security;

import com.example.messenger.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            if (userSaved != null){
                String responseMessage = userSaved.getEmail() ;
                return ResponseEntity.ok(responseMessage);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exit !");
            }
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
