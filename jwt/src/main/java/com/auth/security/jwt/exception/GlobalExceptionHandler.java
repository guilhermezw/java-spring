package com.auth.security.jwt.exception;

import com.auth.security.jwt.exception.custom.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handlerMethodArgumentNotValidException(MethodArgumentNotValidException erro) {
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
            AcessoNegadoException.class,
            AccessDeniedException.class,
            AuthorizationDeniedException.class,
    })
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErroResposta handlerForbiddenException(Exception erro){
        return ErroResposta.resposta(HttpStatus.FORBIDDEN , "Access denied: You do not have permission. " + erro.getMessage());
    }

    @ExceptionHandler({
            RecursoNaoEncontradoException.class,
            UsuarioNaoEncontradoException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErroResposta handlerNotFoundException(RuntimeException erro){
        return ErroResposta.resposta(HttpStatus.NOT_FOUND , erro.getMessage());
    }

    @ExceptionHandler({
            RegraNegocioException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResposta handlerBadRequestException(RuntimeException erro) {
        return ErroResposta.respostaPadrao(erro.getMessage());
    }

    @ExceptionHandler({
            TokenInvalidoException.class,
            UsuarioNaoAutenticadoException.class,
            InvalidBearerTokenException.class,
            AuthenticationException.class
    })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErroResposta handlerUnauthorizedException(Exception erro){
        return ErroResposta.resposta(HttpStatus.UNAUTHORIZED , "Invalid or expired token.");
    }

    @ExceptionHandler({
            ConflitoDeDadosException.class,
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResposta handlerConflictException(RuntimeException erro) {
        return ErroResposta.conflito(erro.getMessage());
    }

    @ExceptionHandler(CampoInvalidoException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handlerCampoInvalidoException(CampoInvalidoException erro) {
        return new ErroResposta(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
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