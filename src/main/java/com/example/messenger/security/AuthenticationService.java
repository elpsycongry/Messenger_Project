package com.example.messenger.security;

import com.example.messenger.mail.MailService;
import com.example.messenger.mail.MailStructure;
import com.example.messenger.user.UserRepository;
import com.example.messenger.verificationToken.VerificationToken;
import com.example.messenger.verificationToken.VerificationTokenService;
import com.example.messenger.token.JwtRepository;
import com.example.messenger.token.JwtService;
import com.example.messenger.token.JwtToken;
import com.example.messenger.user.IUserService;
import com.example.messenger.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final IUserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final JwtRepository jwtRepository;
    private final VerificationTokenService verificationTokenService;
    private final MailService mailService;

    public User register(User requestUser) {
        Optional<User> user = userService.findByEmail(requestUser.getEmail());
        if (user.isEmpty()) {
            requestUser.setPassword(passwordEncoder.encode(requestUser.getPassword()));
            requestUser.setStatus("pending");
            User userAfterSave = userService.save(requestUser);
            VerificationToken token = verificationTokenService.createVerificationToken(userAfterSave, "verify email");
            MailStructure mailStructure = MailStructure.builder()
                    .subject("Xác minh tài khoản Messenger của bạn")
                    .receiverName(userAfterSave.getEmail())
                    .verifyToken(token.getToken())
                    .build();
            mailService.sendMailHtml(userAfterSave.getEmail(), mailStructure );
            return userAfterSave;
        }
        else if (Objects.equals(user.get().getStatus(), "pending")){
            throw new IllegalStateException("User registration is pending. Please verify your email.");
        }
        return null;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userService.findByEmail(request.getEmail());
        if (Objects.equals(user.get().getStatus(), "pending")){
            return null;
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        JwtToken jwtToken = saveNewTokenToUser(user.get());
        return AuthenticationResponse.builder()
                .user(user.get())
                .token(jwtToken.getJwt())
                .build();
    }

    public JwtToken saveNewTokenToUser(User user){
        JwtToken jwtToken = null;
        String newToken = jwtService.generateToken(user);

        try {
            jwtToken = jwtRepository.findByUser(user).get();
            jwtToken.setJwt(newToken);
            jwtRepository.save(jwtToken);
        } catch (Exception e) {
            jwtToken = JwtToken.builder().available(true).jwt(newToken).user(user).build();
            jwtRepository.save(jwtToken);
        }
        return jwtToken;
    }
}
