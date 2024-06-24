package com.example.messenger.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).get();
    }

    @Override
    public void addRolesToUser(Long userId, List<Role> roles) {

    }

    @Override
    public Iterable<User> getUserByRole(Role role) {
        return userRepository.findAllByRoles(role);
    }


}
