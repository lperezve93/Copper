package com.lorenaperez.Copper.repository;

import com.lorenaperez.Copper.constants.Deribit;
import com.lorenaperez.Copper.model.Token;
import com.lorenaperez.Copper.model.UserBalance;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;


@ExtendWith(MockitoExtension.class)
public class CopperRepositoryTest {

    @InjectMocks
    private CopperRepository copperRepository;

    @Test
    public void saveToken_authentication_tokenSaved() {
        // Given
        Token token = checkTokenStatus();
        Token tokenToStore = createToken();

        // When
        Token tokenResult = CopperRepository.saveToken(tokenToStore);

        // Then
        assertThat(tokenResult, notNullValue());
        assertThat(tokenResult.getTokenType(), is(tokenToStore.getTokenType()));
        assertThat(tokenResult.getAccessToken(), is(tokenToStore.getAccessToken()));
    }

    @Test
    public void saveToken_authentication_getToken() {
        // Given
        Token tokenToStore = createToken();
        CopperRepository.saveToken(tokenToStore);

        // When
        Token token = CopperRepository.getToken();

        // Then
        assertThat(token, notNullValue());
        assertThat(token.getTokenType(), is(tokenToStore.getTokenType()));
        assertThat(token.getAccessToken(), is(tokenToStore.getAccessToken()));
    }

    @Test
    public void saveUserBalances_getUserBalances() {
        // Given
        List<UserBalance> userBalances = createUserBalances();

        // When
        List<UserBalance> userBalanceStored = CopperRepository.saveUserBalance(userBalances);

        // Then
        assertThat(userBalanceStored, notNullValue());
        assertThat(userBalanceStored.size(), is(userBalances.size()));
    }

    private Token checkTokenStatus() {
        try {
            Field field = CopperRepository.class.getDeclaredField("token");
            field.setAccessible(true);
            return (Token) field.get(copperRepository);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Token createToken() {
        Token token = new Token();
        token.setTokenType("bearer");
        token.setAccessToken("1642362127665.1ACSOPYD.D1rHgUSz3rHOlA3_95mvOZJqZzMesE8L1A7_U5fxZpWsG2HKhZxq8b1Q9P3rO783IcAfr7yTzabCYtaoxY5wsPLHdDggpXIIUexQS3CVDy5Xh6eeVQHdOLaobuxFYsUN6aLEhZkN5pus7BOchiMe5VhvUAjoLuFGUb0vO8u_UDBtEHDh6e2QB9SSpXTlUd8xycX8tAQNwTmPddP30yD1Pu0S2k7kZhGldSqFQiy86e57l4YM8Z6rdOozZAtD6dqjzL1cjdLbkQ_NeDHsraYhIYsSIVk8fMMsfW9W0pHh_8uVTFcyYTNTGgyMDctuQLPGOkOntELdz6y9kOvmFbk8S95I1W2mTNqwVIc");
        token.setRefreshToken("1642966027665.1FxP3oBV.PSLQj5SzlEgfgl7VP-TphAVAgCn-BA6nnQlXg_S5bhbb6r-vl4Kd3gVhiaOvR8NMuaqSbjW8JRR_PDchkug8pQauozUsPfky49DzaKUL1m9woU-JSzAeohEdKaRlcBbuUNfWcqIoLuJq7sgnYKZ_0h40HGicAfxGZBkHIW7b6s9f7DKKYQmgvqE2QT7HkrJ84txy5f6MDuPp_457Mof63vDpKi5dxS1eRrYlaOYJyujcdhQ0QGaJ7T8tEmlZW8MkvQacPkg7gCC-KczmR1PD8eNCJTG2LfyyTEvVSZI08jkB9yM6XguRNh2JX6LZ7tydodtkfdeAwSoj2fncHuZUGwYr7NBpSOdggQ");
        token.setScope("account:read_write block_trade:read_write custody:read_write mainaccount name:test session:apiconsole-ssgdmkb9ii trade:read_write wallet:read_write");
        token.setExpiresIn(900);
        return token;
    }

    private List<UserBalance> createUserBalances () {
        List<UserBalance> userBalanceList = new ArrayList<>();
        UserBalance userBalance1 = new UserBalance(Deribit.CURRENCY.BTC, 340.0);
        UserBalance userBalance2 = new UserBalance(Deribit.CURRENCY.ETH, 125.0);
        userBalanceList.add(userBalance1);
        userBalanceList.add(userBalance2);
        return userBalanceList;
    }
}
