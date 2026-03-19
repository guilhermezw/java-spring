package com.auth.security.jwt.exception.custom;

public class UsuarioNaoAutenticadoException extends RuntimeException {
    public UsuarioNaoAutenticadoException(String message) {
        super(message);
    }
}
