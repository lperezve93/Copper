package com.lorenaperez.Copper.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDeposit {

    private String transactionId;

    private String address;

    private Double amount;

    private String currency;

    private String state;

    private Integer timestamp;

    public UserDeposit(String transactionId, String address, Double amount, String currency, String state, Integer timestamp) {
        this.transactionId = transactionId;
        this.address = address;
        this.amount = amount;
        this.currency = currency;
        this.state = state;
        this.timestamp = timestamp;
    }
}
