package com.lorenaperez.Copper.repository;

import com.lorenaperez.Copper.model.Token;
import com.lorenaperez.Copper.model.UserBalance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CopperRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(CopperRepository.class);

    private static Token token;

    private List<UserBalance> userBalances;

    public static Token saveToken(Token newToken) {
        LOGGER.info("+++ Repository - saving token +++");
        token = newToken;
        return token;
    }

    public static Token getToken() {
        return token != null ? token : null;
    }

    public List<UserBalance> saveUserBalance(List<UserBalance> userBalances) {
        LOGGER.info("+++ Repository - saving user balance +++");
        this.userBalances = userBalances;
        return userBalances;
    }

}
