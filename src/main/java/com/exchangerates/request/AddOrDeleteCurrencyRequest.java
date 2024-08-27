package com.exchangerates.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddOrDeleteCurrencyRequest {
    @NonNull
    private String currency;
}
