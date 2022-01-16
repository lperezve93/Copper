package com.lorenaperez.Copper.repository;

import com.lorenaperez.Copper.model.Token;
import com.lorenaperez.Copper.model.UserBalance;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CopperRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(CopperRepository.class);

    private static Token token;

    private static List<UserBalance> userBalanceList = new ArrayList<>();

    public static Token saveToken(Token newToken) {
        LOGGER.info("+++ Repository - saving token +++");
        token = newToken;
        return token;
    }

    public static Token getToken() {
        return token != null ? token : null;
    }

    public static List<UserBalance> saveUserBalance(List<UserBalance> userBalances) {
        LOGGER.info("+++ Repository - saving user balance +++");
        userBalanceList = userBalances;
        return userBalanceList;
    }

}
