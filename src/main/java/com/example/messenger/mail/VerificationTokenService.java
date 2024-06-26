package com.example.messenger.mail;

import com.example.messenger.user.User;

public interface VerificationTokenService {
    VerificationToken createVerificationToken(User user);
    boolean checkVerificationToken(VerificationToken token);

}
