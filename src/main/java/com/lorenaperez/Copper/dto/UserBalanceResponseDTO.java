package com.lorenaperez.Copper.dto;

import com.lorenaperez.Copper.constants.Currency;
import com.lorenaperez.Copper.model.UserBalance;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserBalanceResponseDTO {

    private Currency currency;

    private Double amount;

    public static UserBalanceResponseDTO from(UserBalance userBalance) {
        return UserBalanceResponseDTO.builder()
                .currency(userBalance.getCurrency())
                .amount(userBalance.getAmount())
                .build();
    }
}
