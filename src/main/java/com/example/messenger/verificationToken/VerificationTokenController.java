package com.example.messenger.verificationToken;

import com.example.messenger.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/verificationToken")
public class VerificationTokenController {
    @Autowired
    private VerificationTokenService verificationTokenService;
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> verificationToken(@RequestParam String token) {
        VerificationToken verificationToken = verificationTokenService.getVerificationTokenByToken(token);
        if (verificationTokenService.checkVerificationToken(verificationToken)){
            doAction(verificationToken);
            return ResponseEntity.ok("verify token success!");
        }
        return ResponseEntity.badRequest().body("Token không hợp lệ vui lòng thử đăng ký lại!");
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateToken(@RequestBody VerificationTokenDto verificationToken) {
        VerificationToken verificationToken1 = verificationTokenService.updateToken(verificationToken.getEmail(), verificationToken.getAction());
        if (verificationToken1 == null) {
            return ResponseEntity.badRequest().body("Email không tồn tại");
        }
        return ResponseEntity.ok("update token success!");
    }

    private void doAction(VerificationToken verificationToken) {
        switch (verificationToken.getAction()){
            case "verify email" :
                userService.verifyEmail(verificationToken.getUser());
                break;
        }
    }
}
