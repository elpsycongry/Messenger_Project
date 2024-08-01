package com.example.messenger.security;

import com.example.messenger.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
    public ResponseEntity<User> registerUser(
            @RequestBody User user) {
        return ResponseEntity.ok(authenticationService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(
            @RequestBody AuthenticationRequest request) {
        AuthenticationResponse res = null;
        try {
             res = authenticationService.getNewToken(request.getRefreshToken());
        } catch (Exception e){
            return  new ResponseEntity<>("Refresh Token Expired!", HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(res);
    }
}
