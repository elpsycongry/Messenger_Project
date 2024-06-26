package com.example.messenger.verificationToken;

import com.example.messenger.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "verification_token")
@AllArgsConstructor
@NoArgsConstructor
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;
    private LocalDateTime createAt;
    private LocalDateTime expiredAt;
    private String action;
    @ManyToOne
    private User user;
}


