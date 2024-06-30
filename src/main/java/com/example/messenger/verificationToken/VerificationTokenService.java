package com.example.messenger.verificationToken;

import com.example.messenger.user.User;

import java.util.Optional;

public interface VerificationTokenService {
    VerificationToken createVerificationToken(User user, String action);
    boolean checkVerificationToken(VerificationToken token);
    VerificationToken getVerificationTokenByToken(String token);


    Optional<VerificationToken> getByUserEmailAndAction(String email, String action);

    VerificationToken updateToken(String email, String action);
}
