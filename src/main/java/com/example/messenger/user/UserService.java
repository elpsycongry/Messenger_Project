package com.example.messenger.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void addRolesToUser(Long userId, List<Role> roles) {

    }

    @Override
    public Iterable<User> getUserByRole(Role role) {
        return userRepository.findAllByRoles(role);
    }

    @Override
    public User save(User requestUser) {
        return userRepository.save(requestUser);
    }

    @Override
    public boolean checkEmailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public User verifyEmail(User user) {
        user.setStatus("activated");
        return userRepository.save(user);
    }


}
