package com.example.messenger.security;

import com.example.messenger.token.JwtDTO;
import com.example.messenger.token.JwtRepository;
import com.example.messenger.token.JwtService;
import com.example.messenger.token.JwtToken;
import com.example.messenger.user.IUserService;
import com.example.messenger.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final IUserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final JwtRepository jwtRepository;

    public User register(User requestUser) {
        requestUser.setPassword(passwordEncoder.encode(requestUser.getPassword()));
        return userService.save(requestUser);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userService.findByEmail(request.getEmail());
        JwtToken refreshToken = saveRefreshTokenToUser(user);
        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .user(user)
                .token(jwtToken)
                .refreshToken(refreshToken.getJwt())
                .build();
    }

    public JwtToken saveRefreshTokenToUser(User user) {
        JwtToken jwtToken = null;
        String refreshToken = jwtService.generateRefreshToken(user);
        try {
            jwtToken = jwtRepository.findByUser(user).get();
            jwtToken.setJwt(refreshToken);
            jwtRepository.save(jwtToken);
        } catch (Exception e) {
            jwtToken = JwtToken.builder().available(true).jwt(refreshToken).user(user).build();
            jwtRepository.save(jwtToken);
        }
        return jwtToken;
    }

    public AuthenticationResponse getNewToken(String refreshToken) throws Exception {
        Optional<JwtToken> token = jwtRepository.findByJwtContains(refreshToken);

        if (token.isEmpty() || !jwtService.checkToken(refreshToken) ){
            throw new Exception("Token expired");
        }

        String newRefreshToken = jwtService.reSaveRefreshToken(token.get());
        String newToken = jwtService.generateToken(token.get().getUser());

        System.out.println(newRefreshToken);
        System.out.println(newToken);
        return AuthenticationResponse.builder()
                .refreshToken(newRefreshToken)
                .token(newToken)
                .user(token.get().getUser()).build();
    }
}
