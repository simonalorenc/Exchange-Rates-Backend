package com.exchangerates;

import com.exchangerates.database.User;
import com.exchangerates.database.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public String getUserCurrencies(Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return user.getCurrencies();
    }

    @Transactional
    public void addCurrency(String currency, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        String userCurrencies = user.getCurrencies();
        if (userCurrencies == null || userCurrencies.isEmpty()) {
            user.setCurrencies(currency);
        } else {
            user.setCurrencies(userCurrencies + ", " + currency);
        }
        userRepository.save(user);
    }

    @Transactional
    public void deleteCurrency(String currency, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        String userCurrencies = user.getCurrencies();
        String pattern ="\\b" + currency + "\\b,?\\s*|,?\\s*\\b" + currency + "\\b";
        String newUserCurrencies = userCurrencies.replaceAll(pattern, "");
        user.setCurrencies(newUserCurrencies);
    }

    private User checkUser(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        return userOptional.orElseThrow(() -> new RuntimeException("User not found"));
    }
}
