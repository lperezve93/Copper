package com.lorenaperez.Copper.model;

import com.lorenaperez.Copper.constants.Deribit;
import lombok.Data;

@Data
public class UserBalance {

    private Deribit.CURRENCY currency;

    private Double amount;

    public UserBalance (Deribit.CURRENCY currency, Double amount) {
        this.currency = currency;
        this.amount = amount;
    }
}
