package com.spring.keycloak.enums;

public enum Tipo {
    PREMIUM("Premium"),
    STANDARD("Standard");

    private final String texto;

    Tipo(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }
}
