package com.lorenaperez.Copper.dto;

import com.lorenaperez.Copper.constants.Currency;
import com.lorenaperez.Copper.util.CopperUtil;
import lombok.Builder;
import lombok.Data;
import org.json.JSONObject;

@Data
@Builder
public class WithdrawResponseDTO {

    String address;

    String state;

    Currency currency;

    Double amount;

    Double fee;

    int priority;

    String transactionDate;

    String confirmationDate;

    public static WithdrawResponseDTO from(JSONObject withdraw) {
        return WithdrawResponseDTO.builder()
                .address(withdraw.getString("address"))
                .state(withdraw.getString("state"))
                .currency(Currency.valueOf(withdraw.getString("currency")))
                .amount(withdraw.getDouble("amount"))
                .fee(withdraw.getDouble("fee"))
                .priority(withdraw.getInt("priority"))
                .transactionDate(CopperUtil.timestampToDate(withdraw.getInt("created_timestamp")))
                .confirmationDate(CopperUtil.timestampToDate(withdraw.getInt("confirmed_timestamp")))
                .build();
    }

}
