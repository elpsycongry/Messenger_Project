package com.example.messenger.security;

import com.example.messenger.user.User;
import com.example.messenger.user.UserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUser(
            @RequestBody User user) {
        return ResponseEntity.ok(authenticationService.register(user));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> registerUser(
            @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
