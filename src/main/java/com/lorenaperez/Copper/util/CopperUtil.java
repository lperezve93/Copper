package com.lorenaperez.Copper.util;

import com.lorenaperez.Copper.constants.Deribit;
import com.lorenaperez.Copper.model.UserDeposit;
import com.lorenaperez.Copper.model.UserWithdrawal;
import com.lorenaperez.Copper.repository.CopperRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CopperUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(CopperUtil.class);

    public static JSONObject callEndpoint(String type, String urlEndpoint) throws Exception {
        urlEndpoint = Deribit.MAIN.concat(urlEndpoint);
        URL url = new URL(urlEndpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(type);
        conn.setRequestProperty("Content-Type", "application/json");
        if (CopperRepository.getToken() != null)
            conn.setRequestProperty("Authorization", "Bearer ".concat(CopperRepository.getToken().getAccessToken()));
        StringBuilder result = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);
            }
        }
        return (JSONObject) new JSONObject(result.toString()).get("result");
    }

    public static List<UserDeposit> fromJSONToDepositModel(List<JSONObject> depositJson) {
        List<UserDeposit> userDepositList = new ArrayList<>();
        for (JSONObject deposit : depositJson) {
            JSONArray data = (JSONArray) deposit.get("data");
            for (int i = 0; i < data.length(); i++) {
                JSONObject element = (JSONObject) data.get(i);
                String address = element.getString("address");
                Double amount = element.getDouble("amount");
                String currency = element.getString("currency");
                Integer timestamp = element.getInt("received_timestamp");
                String state = element.getString("state");
                String transactionId = element.getString("transaction_id");
                userDepositList.add(new UserDeposit(transactionId, address, amount, currency, state, timestamp));
            }
        }
        return userDepositList;
    }

    public static List<UserWithdrawal> fromJSONToWithdrawalModel(List<JSONObject> withdrawalJson) {
        List<UserWithdrawal> userWithdrawalList = new ArrayList<>();
        for (JSONObject deposit : withdrawalJson) {
            JSONArray data = (JSONArray) deposit.get("data");
            for (int i = 0; i < data.length(); i++) {
                JSONObject element = (JSONObject) data.get(i);
                String address = element.getString("address");
                Double amount = element.getDouble("amount");
                String currency = element.getString("currency");
                Integer timestamp = element.getInt("created_timestamp");
                String state = element.getString("state");
                String transactionId = element.getString("transaction_id");
                Double fee = element.getDouble("fee");
                Double priority = element.getDouble("priority");
                userWithdrawalList.add(new UserWithdrawal(transactionId, address, amount, currency, state, timestamp, fee, priority));
            }
        }
        return userWithdrawalList;
    }

    public static String timestampToDate(Integer timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return format.format(date);

    }
}
