package com.example.messenger.verificationToken;

import com.example.messenger.user.User;

public interface VerificationTokenService {
    VerificationToken createVerificationToken(User user, String action);
    boolean checkVerificationToken(VerificationToken token);
    VerificationToken getVerificationTokenByToken(String token);

}
