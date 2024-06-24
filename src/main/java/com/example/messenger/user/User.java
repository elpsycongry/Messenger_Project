package com.example.messenger.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email is required")
    @Column(unique = true)
    private String email;
    private String password;
    private String phone;
    private String avatar;
    private String status;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;
}
