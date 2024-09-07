package com.tradingplatform.authentication.utils;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JwtRequest implements Serializable {
    private static final long serialVersionUID = 595425981647801034L;

    private String username;
    private String password;

}