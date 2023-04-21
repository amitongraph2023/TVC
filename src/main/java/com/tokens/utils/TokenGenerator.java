package com.tokens.utils;

import java.util.Date;
import java.util.UUID;

public class TokenGenerator {

	private static final long EXPIRATION_TIME = 5 * 60 * 1000; // 5 minutes in milliseconds

    public static String generateToken(String key) {
        String token = UUID.randomUUID().toString();
        Date expirationDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
        String tokenWithExpiration = token + ":"+key+":" + expirationDate.getTime();
        //             token                  : expireTime
        //0b2c764c-ced0-46f4-9fa7-226ce63f2522:1682071997285
        return tokenWithExpiration;
    }
    
    public static boolean isTokenValid(String token) {
        if (token == null) {
            return false;
        }
        String[] tokenParts = token.split(":");
        if (tokenParts.length != 2) {
            return false;
        }
        String tokenValue = tokenParts[0];
        long expirationTime = Long.parseLong(tokenParts[1]);
        Date now = new Date();
        if (expirationTime < now.getTime()) {
            return false;
        }
        return true;
    }
}
