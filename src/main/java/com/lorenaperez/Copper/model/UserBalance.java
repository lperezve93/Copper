package com.lorenaperez.Copper.model;

import com.lorenaperez.Copper.constants.Currency;
import lombok.Data;

@Data
public class UserBalance {

    private Currency currency;

    private Double amount;

    public UserBalance(Currency currency, Double amount) {
        this.currency = currency;
        this.amount = amount;
    }
}
