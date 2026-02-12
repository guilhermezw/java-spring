package api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handlerValidacao(MethodArgumentNotValidException erro) {

        List<ErroCampo> erros = erro.getFieldErrors()
                .stream()
                .map(e -> new ErroCampo(e.getField(), e.getDefaultMessage()))
                .toList();

        return new ErroResposta(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validação nos campos informados.",
                erros
        );
    }

    @ExceptionHandler({
            ErroBuscarException.class,
            InversaoHorario.class,
            MinutosException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResposta handlerBadRequest(RuntimeException erro) {
        return ErroResposta.respostaPadrao(erro.getMessage());
    }

    @ExceptionHandler({
            RegistroDuplicadoException.class,
            HorariosConflitantesException.class
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResposta handlerConflict(RuntimeException erro) {
        return ErroResposta.conflito(erro.getMessage());
    }

    @ExceptionHandler(CampoInvalidoException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_CONTENT)
    public ErroResposta handlerCampoInvalido(CampoInvalidoException erro) {
        return new ErroResposta(
                HttpStatus.UNPROCESSABLE_CONTENT.value(),
                "Validação falhou para o campo informado.",
                List.of(new ErroCampo(erro.getCampo(), erro.getMessage()))
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroResposta handlerErroInterno(Exception erro) {
        return new ErroResposta(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocorreu um erro inesperado no servidor. Contate o suporte.",
                List.of()
        );
    }
}
