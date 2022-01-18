package com.lorenaperez.Copper.controller;

import com.lorenaperez.Copper.constants.Currency;
import com.lorenaperez.Copper.constants.Priority;
import com.lorenaperez.Copper.dto.UserBalanceResponseDTO;
import com.lorenaperez.Copper.dto.UserHistoryResponseDTO;
import com.lorenaperez.Copper.dto.WithdrawResponseDTO;
import com.lorenaperez.Copper.exception.CopperCurrencyException;
import com.lorenaperez.Copper.exception.CopperPriorityException;
import com.lorenaperez.Copper.service.CopperService;
import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/copper")
public class CopperController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CopperController.class);

    @Autowired
    CopperService copperService;

    @GetMapping(path = "/user-balance", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserBalanceResponseDTO>> getUserBalance() throws Exception {
        LOGGER.info("+++ Controller - Get User Balances +++");
        List<UserBalanceResponseDTO> userBalanceResponseDTOList = copperService.getAndSaveUserBalances();
        return new ResponseEntity<>(userBalanceResponseDTOList, HttpStatus.OK);
    }

    @GetMapping(path = "/history", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserHistoryResponseDTO>> getUserHistory(
    ) throws Exception {
        LOGGER.info("+++ Controller - Get User Deposit and Withdrawal history +++");
        List<UserHistoryResponseDTO> userHistoryResponseDTOList = copperService.getUserHistory();
        return new ResponseEntity<>(userHistoryResponseDTOList, HttpStatus.OK);
    }

    @GetMapping(path = "/withdraw", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<WithdrawResponseDTO> withdraw(
            @RequestParam(value = "address", required = true) String address,
            @RequestParam(value = "currency", required = true) String currency,
            @RequestParam(value = "amount", required = true) String amount,
            @RequestParam(value = "priority", required = true) String priority
    ) throws Exception {
        LOGGER.info("+++ Controller - Get User Deposit and Withdrawal history +++");
        validateRequest(currency, priority);
        WithdrawResponseDTO withdrawResponseDTO = copperService.withdraw(address, currency, amount, priority);
        return new ResponseEntity<>(withdrawResponseDTO, HttpStatus.OK);
    }

    private void validateRequest(String currency, String priority) throws CopperCurrencyException, CopperPriorityException {
        if (!EnumUtils.isValidEnum(Currency.class, currency))
            throw new CopperCurrencyException();
        if (!EnumUtils.isValidEnum(Priority.class, priority))
            throw new CopperPriorityException();
    }
}

