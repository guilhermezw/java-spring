package com.spring.keycloak.enums;

public enum Categoria {

    PROGRAMACAO("Programação"),
    DESIGN("Design & UX"),
    MARKETING("Marketing Digital"),
    NEGOCIOS("Negócios & Gestão"),
    DATA_SCIENCE("Data Science & IA"),
    IDIOMAS("Idiomas");

    private final String texto;

    Categoria(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }
}
