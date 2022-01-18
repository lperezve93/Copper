package com.lorenaperez.Copper.constants;

import java.util.Arrays;

public interface Messages {

    // Error
    String INVALID_CURRENCY = "ERROR: invalid currency. Please use the following values " + Arrays.toString(Currency.values());
    String INVALID_PRIORITY = "ERROR: invalid priority. Please use the following values " + Arrays.toString(Priority.values());
}
