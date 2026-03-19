package com.auth.security.jwt.exception.custom;

public class CampoInvalidoException extends RuntimeException {
    private String campo;

    public CampoInvalidoException(String campo , String message) {
        super(message);
        this.campo = campo;
    }

    public String getCampo() {
        return campo;
    }
}
