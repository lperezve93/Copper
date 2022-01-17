package com.lorenaperez.Copper.service;

import com.lorenaperez.Copper.constants.Deribit;
import com.lorenaperez.Copper.dto.UserBalanceResponseDTO;
import com.lorenaperez.Copper.model.Token;
import com.lorenaperez.Copper.model.UserBalance;
import com.lorenaperez.Copper.repository.CopperRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@ExtendWith(MockitoExtension.class)
public class CopperServiceTest {

    @InjectMocks
    @Spy
    private CopperService copperService;

    @Mock
    private CopperRepository copperRepository;

    @Test
    public void authentication_saveToken() throws Exception {
        // Given
        copperService.authentication();
        Token token = copperRepository.getToken();

        // Then
        assertThat(token, notNullValue());
    }

    @Test
    public void getAndSaveUserBalances_saveBalances() throws Exception {
        // Given
        copperService.authentication();
        List<UserBalance> userBalances = createUserBalances();
        List<JSONObject> userDeposits = createUserDepositList();

        // When
        Mockito.doReturn(userDeposits).when(copperService).getUserDeposits();
        List<UserBalanceResponseDTO> userBalancesDTO = copperService.getAndSaveUserBalances();

        // Then
        assertThat(userBalances.size(), is(userBalancesDTO.size()));
        assertThat(userBalances.get(0).getCurrency(), is(userBalancesDTO.get(0).getCurrency()));
        assertThat(userBalances.get(0).getAmount(), is(userBalancesDTO.get(0).getAmount()));
    }

    private List<UserBalance> createUserBalances() {
        List<UserBalance> userBalances = new ArrayList<>();
        userBalances.add(new UserBalance(Deribit.CURRENCY.BTC, 500D));
        return userBalances;
    }

    private List<JSONObject> createUserDepositList() throws JSONException {
        JSONArray dataArray = new JSONArray();
        JSONObject depositDetails = new JSONObject();
        depositDetails.put("amount", 500);
        depositDetails.put("currency", "BTC");
        dataArray.put(depositDetails);

        JSONObject deposit = new JSONObject();
        deposit.put("data", dataArray);
        deposit.put("count", 1);

        List<JSONObject> depositList = new ArrayList<>();
        depositList.add(deposit);

        return depositList;
    }
}
