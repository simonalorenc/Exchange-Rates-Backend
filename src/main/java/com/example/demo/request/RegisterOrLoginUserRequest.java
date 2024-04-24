package com.example.demo.request;

import org.springframework.lang.NonNull;

public class RegisterOrLoginUserRequest {

    @NonNull
    private String email;
    @NonNull
    private String password;

    public RegisterOrLoginUserRequest(@NonNull String email, @NonNull String password) {
        this.email = email;
        this.password = password;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }
}
