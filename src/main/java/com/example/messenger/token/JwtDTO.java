package com.example.messenger.token;

import com.example.messenger.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class JwtDTO {
    private Long id;
    private User user;
    private String jwtToken;
    private String refreshToken;
}
