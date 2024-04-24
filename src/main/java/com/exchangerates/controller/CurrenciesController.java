package com.exchangerates.controller;

import com.exchangerates.UserService;
import com.exchangerates.request.AddOrDeleteCurrencyRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CurrenciesController {

    private UserService userService;

    public CurrenciesController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getUserCurrencies")
    public ResponseEntity<String> getUserCurrencies(@RequestParam String email) {
        String currencies = userService.getUserCurrencies(email);
        return ResponseEntity.ok(currencies);
    }

    @PostMapping("/addCurrency")
    public ResponseEntity<HttpStatus> addCurrency(@RequestBody AddOrDeleteCurrencyRequest request) {
        userService.addCurrency(request.getEmail(), request.getCurrency());
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("/deleteCurrency")
    public ResponseEntity<HttpStatus> deleteCurrency(@RequestBody AddOrDeleteCurrencyRequest request) {
        userService.deleteCurrency(request.getEmail(), request.getCurrency());
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
