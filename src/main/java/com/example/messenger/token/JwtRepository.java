package com.example.messenger.token;

import com.example.messenger.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JwtRepository extends JpaRepository<JwtToken, Long> {
    Optional<JwtToken> findByJwtContains(String jwt);
    Optional<JwtToken> findByUser(User user);

}
