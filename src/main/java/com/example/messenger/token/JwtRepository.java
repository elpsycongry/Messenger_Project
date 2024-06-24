package com.example.messenger.token;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JwtRepository extends JpaRepository<JwtToken, Long> {
}
