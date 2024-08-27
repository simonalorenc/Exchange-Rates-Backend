package com.exchangerates;

import com.exchangerates.database.User;
import com.exchangerates.database.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<String> getUserCurrencies(Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return user.getCurrencies();
    }

    @Transactional
    public void addCurrency(String currency, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        List<String> userCurrencies = user.getCurrencies();
        if (userCurrencies == null) {
            userCurrencies = new ArrayList<>();
        }
        userCurrencies.add(currency);
        user.setCurrencies(userCurrencies);

        userRepository.save(user);
    }

    @Transactional
    public void deleteCurrency(String currency, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        List<String> userCurrencies = user.getCurrencies();
        List<String> newUserCurrencies = userCurrencies.stream().filter(el -> !el.equals(currency)).collect(Collectors.toList());
        user.setCurrencies(newUserCurrencies);
        userRepository.save(user);
    }
}
