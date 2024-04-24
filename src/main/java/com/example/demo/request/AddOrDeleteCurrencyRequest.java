package com.example.demo.request;

import org.springframework.lang.NonNull;

public class AddOrDeleteCurrencyRequest {
    @NonNull
    private String email;
    @NonNull
    private String currency;

    public AddOrDeleteCurrencyRequest(@NonNull String email, @NonNull String currency) {
        this.email = email;
        this.currency = currency;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(@NonNull String currency) {
        this.currency = currency;
    }
}
