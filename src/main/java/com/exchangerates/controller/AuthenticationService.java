package com.exchangerates.controller;

import com.exchangerates.config.JwtService;
import com.exchangerates.database.User;
import com.exchangerates.database.UserRepository;
import com.exchangerates.exception.UserAlreadyExistsException;
import com.exchangerates.exception.UserDoNotExist;
import com.exchangerates.request.RegisterRequest;
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

    public AuthenticationResponse register(RegisterRequest request) throws UserAlreadyExistsException {
        Optional<User> existingUser = repository.findByEmail((request.getEmail()));
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException(request.getEmail());
        } else {
            var user = User.builder()
                    .firstname(request.getFirstname())
                    .lastname(request.getLastname())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .build();
            repository.save(user);
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .user(user)
                    .build();
        }
    }

    public AuthenticationResponse login(AuthenticationRequest request) throws UserDoNotExist, BadCredentialsException {
        Optional<User> optionalUser = repository.findByEmail((request.getEmail()));
        if (optionalUser.isEmpty()) {
            throw new UserDoNotExist(request.getEmail());
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
                        .user(user)
                        .build();
            } catch (BadCredentialsException e) {
                throw new BadCredentialsException("Inncorrect Password");
            }
        }
    }
}
