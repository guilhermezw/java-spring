package com.example.libraryapi.exception;

public class CampoInvalidadoException extends RuntimeException{

    private String campo;

    public CampoInvalidadoException(String campo , String message) {
        super(message);
        this.campo = campo;
    }

    public String getCampo() {
        return campo;
    }
}
