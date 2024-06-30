package com.example.messenger.user;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    Optional<User> findByEmail(String email);

    void addRolesToUser(Long userId, List<Role> roles);

    Iterable<User> getUserByRole(Role role);

    User save(User requestUser);

    boolean checkEmailExists(String email);

    User verifyEmail(User user);


}
