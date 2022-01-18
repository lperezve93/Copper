package com.lorenaperez.Copper.dto;

import com.lorenaperez.Copper.model.UserDeposit;
import com.lorenaperez.Copper.model.UserWithdrawal;
import com.lorenaperez.Copper.util.CopperUtil;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserHistoryResponseDTO {

    private String type;

    private String transactionId;

    private String address;

    private String date;

    private String state;

    private String currency;

    private Double amount;

    private Double fee;

    private Double priority;

    public static UserHistoryResponseDTO fromDeposit(UserDeposit userDeposit) {
        return UserHistoryResponseDTO.builder()
                .address(userDeposit.getAddress())
                .amount(userDeposit.getAmount())
                .currency(userDeposit.getCurrency())
                .date(CopperUtil.timestampToDate(userDeposit.getTimestamp()))
                .state(userDeposit.getState())
                .transactionId(userDeposit.getTransactionId())
                .type("DEPOSIT")
                .fee(0D)
                .priority(0D)
                .build();
    }

    public static UserHistoryResponseDTO fromWithdrawal(UserWithdrawal userWithdrawal) {
        return UserHistoryResponseDTO.builder()
                .address(userWithdrawal.getAddress())
                .amount(userWithdrawal.getAmount())
                .currency(userWithdrawal.getCurrency())
                .fee(userWithdrawal.getFee())
                .priority(userWithdrawal.getPriority())
                .date(CopperUtil.timestampToDate(userWithdrawal.getTimestamp()))
                .state(userWithdrawal.getState())
                .transactionId(userWithdrawal.getTransactionId())
                .type("WITHDRAWAL")
                .build();
    }
}
