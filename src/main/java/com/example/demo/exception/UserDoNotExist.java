package com.example.demo.exception;

public class UserDoNotExist extends RuntimeException{

    public UserDoNotExist(String message) {
        super(message);
    }

    public UserDoNotExist(String message, Throwable cause) {
        super(message, cause);
    }
}
