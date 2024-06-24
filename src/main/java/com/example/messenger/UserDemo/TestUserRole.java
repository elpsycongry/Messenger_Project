package com.example.messenger.UserDemo;

import com.example.messenger.user.IUserService;
import com.example.messenger.user.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userRole")
public class TestUserRole {
    @Autowired
    private IUserService userService;
    @Autowired
    private RoleRepository repository;
    @GetMapping
    public ResponseEntity<?> demo() {
        return ResponseEntity.ok(
                userService.getUserByRole(repository.findById(1L).get()));
    }
}