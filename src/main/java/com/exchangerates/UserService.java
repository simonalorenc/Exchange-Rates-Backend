package com.exchangerates;

import com.exchangerates.database.User;
import com.exchangerates.database.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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

    public String getUserCurrencies(String email) {
        User user = checkUser(email);
        return user.getCurrencies();
    }

    @Transactional
    public void addCurrency(String email, String currency) {
        User user = checkUser(email);
        String userCurrencies = user.getCurrencies();
        if (userCurrencies == null || userCurrencies.isEmpty()) {
            user.setCurrencies(currency);
        } else {
            user.setCurrencies(userCurrencies + ", " + currency);
        }
    }

    @Transactional
    public void deleteCurrency(String email, String currency) {
        User user = checkUser(email);
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
