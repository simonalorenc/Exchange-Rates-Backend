package com.exchangerates.controller;

import com.exchangerates.UserService;
import com.exchangerates.request.AddOrDeleteCurrencyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CurrenciesController {
    private final UserService userService;

    @GetMapping("/getUserCurrencies")
    public ResponseEntity<List<String>> getUserCurrencies(Principal connectedUser) {
        List<String> currencies = userService.getUserCurrencies(connectedUser);
        return ResponseEntity.ok(currencies);
    }

    @PostMapping("/addCurrency")
    public ResponseEntity<HttpStatus> addCurrency(@RequestBody AddOrDeleteCurrencyRequest request,
                                                  Principal connectedUser) {
        userService.addCurrency(request.getCurrency(), connectedUser);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("/deleteCurrency")
    public ResponseEntity<HttpStatus> deleteCurrency(@RequestBody AddOrDeleteCurrencyRequest request,
                                                     Principal connectedUser) {
        userService.deleteCurrency(request.getCurrency(), connectedUser);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
