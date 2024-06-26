package com.example.messenger.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserUnverifiedRepository unverifiedRepository;
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

    @Override
    public User save(User requestUser) {

        return userRepository.save(requestUser);
    }

    @Override
    public UserUnverified saveUnverified(User user) {
        return unverifiedRepository.save(UserUnverified.builder()
                .password(user.getPassword())
                .email(user.getEmail())
                .build());
    }


}
