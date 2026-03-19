package com.auth.security.jwt.exception.custom;

public class TokenInvalidoException extends RuntimeException {
    public TokenInvalidoException(String message) {
        super(message);
    }
}
