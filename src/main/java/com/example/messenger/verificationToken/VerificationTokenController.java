package com.example.messenger.verificationToken;

import com.example.messenger.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    private void doAction(VerificationToken verificationToken) {
        switch (verificationToken.getAction()){
            case "verify email" :
                userService.verifyEmail(verificationToken.getUser());
                break;
        }
    }
}
