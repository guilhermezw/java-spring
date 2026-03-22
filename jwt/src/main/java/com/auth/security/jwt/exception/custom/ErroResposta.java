package com.auth.security.jwt.exception.custom;

import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;
import java.util.List;

public record ErroResposta (int status, String mensagem, LocalDateTime timestamp, List<ErroCampo> erro) {

    public ErroResposta(int status, String mensagem, List<ErroCampo> erro) {
        this(status, mensagem, LocalDateTime.now(), erro);
    }

    public static ErroResposta resposta(HttpStatus status, String mensagem) {
        return new ErroResposta(status.value(), mensagem, List.of());
    }

}