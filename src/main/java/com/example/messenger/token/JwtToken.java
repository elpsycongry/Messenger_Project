package com.example.messenger.token;

import com.example.messenger.user.User;
import com.example.messenger.user.UserDetail;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "token")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jwt;

    private Boolean available = true;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}