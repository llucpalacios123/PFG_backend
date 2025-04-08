package com.lluc.backend.shopapp.shopapp.auth;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;

public class TokenJwtConfig {
    public final static SecretKey SECRET_KEY = Jwts.SIG.HS512.key().build();
    public final static String PREFIX_TOKEN = "Bearer ";
    public final static String HEADER_AUTHORIZATION = "Authorization";
    public final static String TOKEN_DELIMITER = ":";
    public final static Long EXPIRATION_TIME =  3600000l;
}
