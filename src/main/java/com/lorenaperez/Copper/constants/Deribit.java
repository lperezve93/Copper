package com.lorenaperez.Copper.constants;

public interface Deribit {

    // ##### URLs #####
    String MAIN = "https://test.deribit.com/api/v2";

    String AUTH_URL = "/public/auth";

    String GET_DEPOSIT_URL = "/private/get_deposits";

    String GET_WITHDRAWAL_URL = "/private/get_withdrawals";

    // ##### URL Params #####
    String CLIENT_ID = "client_id";

    String CLIENT_CREDENTIALS = "client_credentials";

    String GRANT_TYPE = "grant_type";

    String CLIENT_ID_VALUE = "ewIeJJC7";

    String CLIENT_SECRECT_VALUE = "WLzZBYjTMnBqNMkuuxvPiaPFYlj3SW86NtebFRW1mLw";

    enum CURRENCY {BTC, ETH, SOL, USDC}

}
