package com.example.messenger.user;

import java.util.Optional;

public interface IUserService {
    User findByEmail(String email);
}
