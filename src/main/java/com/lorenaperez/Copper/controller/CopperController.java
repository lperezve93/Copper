package com.lorenaperez.Copper.controller;

import com.lorenaperez.Copper.constants.Deribit;
import com.lorenaperez.Copper.dto.UserBalanceResponseDTO;
import com.lorenaperez.Copper.service.CopperService;
import com.lorenaperez.Copper.util.CopperUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/copper")
public class CopperController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CopperController.class);

    @GetMapping(path = "/user-balance", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserBalanceResponseDTO>> getUserBalance() {
        LOGGER.info("+++ Controller - Get User Balances +++");
        List<JSONObject> userDepositResponse = new ArrayList<>();
        EnumSet.allOf(Deribit.CURRENCY.class).forEach(
                currency -> {
                    String getBalanceUrl = Deribit.GET_DEPOSIT_URL.concat("?currency=").concat(currency.toString());
                    try {
                        JSONObject result = CopperUtil.callEndpoint("GET", getBalanceUrl);
                        if (result != null) userDepositResponse.add(result);
                    } catch (Exception e) {
                        LOGGER.error("Error calling endpoint " + getBalanceUrl);
                    }
                }
        );
        List<UserBalanceResponseDTO> userBalanceResponseDTOList = CopperService.getAndSaveUserBalances(userDepositResponse);
        return new ResponseEntity<>(userBalanceResponseDTOList, HttpStatus.OK);
    }
}

