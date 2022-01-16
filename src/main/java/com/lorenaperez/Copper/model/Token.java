package com.lorenaperez.Copper.model;

import lombok.Data;

@Data
public class Token {

    private String accessToken;

    private String refreshToken;

    private Integer expiresIn;

    private String tokenType;

    private String scope;

}
