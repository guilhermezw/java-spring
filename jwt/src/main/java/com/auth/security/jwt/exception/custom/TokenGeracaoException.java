package com.auth.security.jwt.exception.custom;

public class TokenGeracaoException extends RuntimeException {
    public TokenGeracaoException(String message) {
        super(message);
    }
}
