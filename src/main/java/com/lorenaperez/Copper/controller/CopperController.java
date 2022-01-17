package com.lorenaperez.Copper.controller;

import com.lorenaperez.Copper.dto.UserBalanceResponseDTO;
import com.lorenaperez.Copper.dto.UserHistoryResponseDTO;
import com.lorenaperez.Copper.service.CopperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
}

