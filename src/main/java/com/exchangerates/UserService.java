package com.exchangerates;

import com.exchangerates.database.User;
import com.exchangerates.database.UserRepository;
import com.exchangerates.exception.UserAlreadyExistsException;
import com.exchangerates.exception.UserDoNotExist;
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

//    public void registerUser(String email, String password) {
//        User existingUser = userRepository.findByEmail(email);
//        if (existingUser != null) {
//            throw new UserAlreadyExistsException("User with email " + email + " already exists");
//        } else {
//            User newUser = new User(email, password);
//
//            userRepository.save(newUser);
//        }
//    }
//
//    public void loginUser(String email, String password) {
//        User existingUser = userRepository.findByEmail(email);
//        if (existingUser == null) {
//            throw new UserDoNotExist("User with email " + email + " don't exist");
//        } else {
//            if (existingUser.getPassword().equals(password)) {
//                System.out.println("Welcome " + email);
//            } else {
//                System.out.println("Wrong password");
//            }
//        }
//    }
//
    public String getUserCurrencies(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        User user = userOptional.orElseThrow(() -> new RuntimeException("User not found"));
        return user.getCurrencies();
    }

    @Transactional
    public void addCurrency(String email, String currency) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        User user = userOptional.orElseThrow(() -> new RuntimeException("User not found"));
        String userCurrencies = user.getCurrencies();
        if (userCurrencies == null || userCurrencies.isEmpty()) {
            user.setCurrencies(currency);
        } else {
            user.setCurrencies(userCurrencies + ", " + currency);
        }
    }

    @Transactional
    public void deleteCurrency(String email, String currency) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        User user = userOptional.orElseThrow(() -> new RuntimeException("User not found"));
        String userCurrencies = user.getCurrencies();
        String pattern ="\\b" + currency + "\\b,?\\s*|,?\\s*\\b" + currency + "\\b";
        String newUserCurrencies = userCurrencies.replaceAll(pattern, "");
        user.setCurrencies(newUserCurrencies);
    }
}
