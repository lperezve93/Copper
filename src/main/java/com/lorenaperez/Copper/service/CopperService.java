package com.lorenaperez.Copper.service;

import com.lorenaperez.Copper.constants.Deribit;
import com.lorenaperez.Copper.util.CopperUtil;
import com.lorenaperez.Copper.dto.UserBalanceResponseDTO;
import com.lorenaperez.Copper.model.Token;
import com.lorenaperez.Copper.model.UserBalance;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import com.lorenaperez.Copper.repository.CopperRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

@Service
public class CopperService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CopperService.class);

    @EventListener(ApplicationReadyEvent.class)
    public void authentication() throws Exception {
        String url = Deribit.AUTH_URL;
        url = url.concat("?client_id=ewIeJJC7");
        url = url.concat("&client_secret=WLzZBYjTMnBqNMkuuxvPiaPFYlj3SW86NtebFRW1mLw");
        url = url.concat("&grant_type=client_credentials");
        JSONObject authObject = CopperUtil.callEndpoint("GET", url);
        CopperRepository.saveToken(setTokenValue(authObject));
        refreshAuth();
    }

    private void refreshAuth() {
        new Timer().schedule(
                new TimerTask() {
                    @SneakyThrows
                    @Override
                    public void run() {
                        authentication();
                    }
                }, CopperRepository.getToken().getExpiresIn() * 1000
        );
    }

    public static List<UserBalanceResponseDTO> getAndSaveUserBalances(List<JSONObject> userDepositList) {
        List<UserBalance> userBalanceList = new ArrayList<>();
        for (JSONObject deposit : userDepositList) {
            UserBalance userBalance = calculateUserBalance(deposit);
            if (userBalance != null) userBalanceList.add(userBalance);
        }
        if (userBalanceList.size() > 0) CopperRepository.saveUserBalance(userBalanceList);
        return userBalanceList.stream().map(UserBalanceResponseDTO::from).collect(Collectors.toList());
    }

    private static UserBalance calculateUserBalance(JSONObject deposit) {
        int depositCount = deposit.getInt("count");
        if (depositCount > 0) {
            Double totalAmount = (double) 0;
            String currency = "";
            JSONArray data = (JSONArray) deposit.get("data");
            for (int i = 0; i < data.length(); i++) {
                JSONObject element = (JSONObject) data.get(i);
                totalAmount += element.getDouble("amount");
                currency = (String) element.get("currency");
            }
            return new UserBalance(Deribit.CURRENCY.valueOf(currency), totalAmount);
        }
        LOGGER.debug("No deposit found on " + deposit);
        return null;
    }

    private Token setTokenValue(JSONObject authToken) {
        Token token = new Token();
        token.setAccessToken((String) authToken.get("access_token"));
        token.setRefreshToken((String) authToken.get("refresh_token"));
        token.setTokenType((String) authToken.get("token_type"));
        token.setExpiresIn((Integer) authToken.get("expires_in"));
        token.setScope((String) authToken.get("scope"));
        return token;
    }

}
