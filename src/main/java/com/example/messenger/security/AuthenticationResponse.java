package com.example.messenger.security;

import com.example.messenger.user.User;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private User user;
    private String token;
    private String refreshToken;
}
