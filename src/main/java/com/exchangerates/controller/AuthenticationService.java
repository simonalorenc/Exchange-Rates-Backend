package com.exchangerates.controller;

import com.exchangerates.config.JwtService;
import com.exchangerates.database.Role;
import com.exchangerates.database.User;
import com.exchangerates.database.UserRepository;
import com.exchangerates.exception.UserAlreadyExistsException;
import com.exchangerates.exception.UserDoNotExist;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        Optional<User> existingUser = repository.findByEmail((request.getEmail()));
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException("User with email " + request.getEmail() + " already exist.");
        } else {
            var user = User.builder()
                    .firstname(request.getFirstname())
                    .lastname(request.getLastname())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.USER)
                    .build();
            repository.save(user);
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        }
    }

    //correct exception, catch and handle dodac
    public AuthenticationResponse login(AuthenticationRequest request) {
        Optional<User> optionalUser = repository.findByEmail((request.getEmail()));
        if (optionalUser.isEmpty()) {
            throw new UserDoNotExist("User with email " + request.getEmail() + " don't exist.");
        } else {
            try {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );
                var user = optionalUser.get();
                var jwtToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder()
                        .token(jwtToken)
                        .build();
            } catch (BadCredentialsException e) {
                throw new BadCredentialsException("Inncorrect Password");
            }
        }
    }
}
