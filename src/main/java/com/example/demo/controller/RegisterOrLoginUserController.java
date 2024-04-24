package com.example.demo.controller;

import com.example.demo.database.User;
import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.exception.UserDoNotExist;
import com.example.demo.UserService;
import com.example.demo.request.RegisterOrLoginUserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RegisterOrLoginUserController {
    private final UserService userService;

    public RegisterOrLoginUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterOrLoginUserRequest request) {
        try {
            userService.registerUser(request.getEmail(), request.getPassword());
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody RegisterOrLoginUserRequest request) {
        try {
            userService.loginUser(request.getEmail(), request.getPassword());
        } catch (UserDoNotExist e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
