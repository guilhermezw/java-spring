package api.exception;

import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;
import java.util.List;

public record ErroResposta(int status, String mensagem, LocalDateTime timestamp, List<ErroCampo> erros) {

    public ErroResposta(int status, String mensagem, List<ErroCampo> erros) {
        this(status, mensagem, LocalDateTime.now(), erros);
    }

    public static ErroResposta respostaPadrao(String mensagem) {
        return new ErroResposta(HttpStatus.BAD_REQUEST.value(), mensagem, List.of());
    }

    public static ErroResposta conflito(String mensagem) {
        return new ErroResposta(HttpStatus.CONFLICT.value(), mensagem, List.of());
    }
}

