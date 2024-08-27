package com.exchangerates.exception;

public class UserDoNotExist extends RuntimeException{

    public UserDoNotExist() {}

    public UserDoNotExist(String email) {
        super("User doesn't exist.");
    }

    public UserDoNotExist(String message, Throwable cause) {
        super(message, cause);
    }
}
