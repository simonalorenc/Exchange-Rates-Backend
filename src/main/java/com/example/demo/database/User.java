package com.example.demo.database;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    private String email;
    @NonNull
    private String password;
    @Nullable
    private String currencies;

    public User() {
    }

    public User(@NonNull String email, @NonNull String password) {
        this.email = email;
        this.password = password;
    }

    @Nullable
    public String getCurrencies() {
        return currencies;
    }

    public void setCurrencies(@Nullable String currencies) {
        this.currencies = currencies;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
