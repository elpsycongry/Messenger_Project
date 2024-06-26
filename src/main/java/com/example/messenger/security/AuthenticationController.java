package com.example.messenger.security;

import com.example.messenger.user.User;
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
    public ResponseEntity<?> registerUser(
            @RequestBody User user) {
        User userSaved = authenticationService.register(user);
        String responseMessage = "Một email đã được gửi tới địa chỉ " + userSaved.getEmail() + " vui lòng truy cập vào gmail và xác nhận";
        return ResponseEntity.ok(responseMessage);
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
