package com.lorenaperez.Copper.util;

import com.lorenaperez.Copper.constants.Deribit;
import com.lorenaperez.Copper.repository.CopperRepository;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CopperUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(CopperUtil.class);

    public static JSONObject callEndpoint(String type, String urlEndpoint) throws Exception {
        urlEndpoint = Deribit.MAIN.concat(urlEndpoint);
        URL url = new URL(urlEndpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(type);
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
}
