package com.lorenaperez.Copper.model;

import lombok.Data;

@Data
public class UserWithdrawal {

    private String transactionId;

    private String address;

    private Double amount;

    private String currency;

    private String state;

    private Integer timestamp;

    private Double fee;

    private Double priority;

    public UserWithdrawal(String transactionId, String address, Double amount, String currency, String state, Integer timestamp, Double fee, Double priority) {
        this.transactionId = transactionId;
        this.address = address;
        this.amount = amount;
        this.currency = currency;
        this.state = state;
        this.timestamp = timestamp;
        this.fee = fee;
        this.priority = priority;
    }
}
