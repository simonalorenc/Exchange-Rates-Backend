package com.exchangerates.exception;

public class UserAlreadyExistsException extends RuntimeException{

    public UserAlreadyExistsException(String email) {
        super("User with email " + email + " already exist.");
    }

    public UserAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
