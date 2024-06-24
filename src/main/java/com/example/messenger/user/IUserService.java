package com.example.messenger.user;

import java.util.List;

public interface IUserService {
    User findByEmail(String email);

    void addRolesToUser(Long userId, List<Role> roles);

    Iterable<User> getUserByRole(Role role);

    User save(User requestUser);
}
