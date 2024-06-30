package com.example.messenger.verificationToken;

import com.example.messenger.user.User;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerificationTokenDto {
    private Long id;
    private String token;
    private LocalDateTime createAt;
    private LocalDateTime expiredAt;
    private String action;
    private User user;
    private String email;
}
