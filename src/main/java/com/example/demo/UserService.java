package com.example.demo;

import com.example.demo.database.User;
import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.exception.UserDoNotExist;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void registerUser(String email, String password) {
        User existingUser = userRepository.findByEmail(email);
        if (existingUser != null) {
            throw new UserAlreadyExistsException("User with email " + email + " already exists");
        } else {
            User newUser = new User(email, password);

            userRepository.save(newUser);
        }
    }

    public void loginUser(String email, String password) {
        User existingUser = userRepository.findByEmail(email);
        if (existingUser == null) {
            throw new UserDoNotExist("User with email " + email + " don't exist");
        } else {
            if (existingUser.getPassword().equals(password)) {
                System.out.println("Welcome " + email);
            } else {
                System.out.println("Wrong password");
            }
        }
    }

    public String getUserCurrencies(String email) {
        User user = userRepository.findByEmail(email);
        return user.getCurrencies();
    }

    @Transactional
    public void addCurrency(String email, String currency) {
        User user = userRepository.findByEmail(email);
        String userCurrencies = user.getCurrencies();
        if (userCurrencies == null || userCurrencies.isEmpty()) {
            user.setCurrencies(currency);
        } else {
            user.setCurrencies(userCurrencies + ", " + currency);

        }
    }

    @Transactional
    public void deleteCurrency(String email, String currency) {
        User user = userRepository.findByEmail(email);
        String userCurrencies = user.getCurrencies();
        String pattern ="\\b" + currency + "\\b,?\\s*|,?\\s*\\b" + currency + "\\b";
        String newUserCurrencies = userCurrencies.replaceAll(pattern, "");
        user.setCurrencies(newUserCurrencies);
    }
}
