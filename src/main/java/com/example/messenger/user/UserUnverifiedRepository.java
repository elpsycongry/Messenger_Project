package com.example.messenger.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserUnverifiedRepository extends JpaRepository<UserUnverified, Long> {
}
