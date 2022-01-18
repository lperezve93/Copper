package com.lorenaperez.Copper.controller;

import com.lorenaperez.Copper.constants.Currency;
import com.lorenaperez.Copper.constants.Priority;
import com.lorenaperez.Copper.dto.WithdrawResponseDTO;
import com.lorenaperez.Copper.exception.CopperCurrencyException;
import com.lorenaperez.Copper.exception.CopperPriorityException;
import com.lorenaperez.Copper.service.CopperService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
public class CopperControllerTest {
    @InjectMocks
    private CopperController copperController;

    @Mock
    private CopperService copperService;

    @Test
    public void makeWithdrawal_throwPriorityException() throws Exception {
        assertThrows(CopperPriorityException.class, () -> copperController.withdraw("ADDRESS", Currency.BTC.toString(), "0.006", "mide"));
    }

    @Test
    public void makeWithdrawal_throwCurrencyException() throws Exception {
        assertThrows(CopperCurrencyException.class, () -> copperController.withdraw("ADDRESS", "BC", "0.006", Priority.mid.toString()));
    }

    @Test
    public void makeWithdrawal_successfulResponse() throws Exception {
        // Given
        WithdrawResponseDTO withdrawResponseDTO = createWithdrawResponseDTO();
        given(copperService.withdraw(any(), any(), any(), any())).willReturn(withdrawResponseDTO);

        // When
        ResponseEntity<WithdrawResponseDTO> response = copperController.withdraw("ADDRESS", Currency.BTC.toString(), "0.006", Priority.mid.toString());

        // Then
        assertThat(response.getBody().getAmount(), is(withdrawResponseDTO.getAmount()));
        assertThat(response.getBody().getCurrency(), is(withdrawResponseDTO.getCurrency()));
        assertThat(response.getBody().getState(), is(withdrawResponseDTO.getState()));

    }

    private WithdrawResponseDTO createWithdrawResponseDTO() {
        return WithdrawResponseDTO.builder()
                .address("ADDRESSXXX")
                .state("confirmed")
                .currency(Currency.ETH)
                .amount(0.00060)
                .fee(0.001)
                .priority(1)
                .transactionDate("")
                .confirmationDate("")
                .build();
    }
}
