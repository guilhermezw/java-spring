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
    })
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErroResposta handlerForbiddenException(RuntimeException erro){
        return ErroResposta.resposta(HttpStatus.FORBIDDEN , erro.getMessage());
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
        return ErroResposta.resposta(HttpStatus.BAD_REQUEST , erro.getMessage());
    }


    @ExceptionHandler({
            TokenInvalidoException.class,
            UsuarioNaoAutenticadoException.class,
    })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErroResposta handlerUnauthorizedException(RuntimeException erro){
        return ErroResposta.resposta(HttpStatus.UNAUTHORIZED , erro.getMessage());
    }

    @ExceptionHandler({
            ConflitoDeDadosException.class,
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResposta handlerConflictException(RuntimeException erro) {
        return ErroResposta.resposta(HttpStatus.CONFLICT , erro.getMessage());
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
    public ErroResposta handlerErroInterno(Exception ex) {
        return new ErroResposta(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocorreu um erro inesperado no servidor. Contate o suporte.",
                List.of()
        );
    }

    @ExceptionHandler({
            InvalidBearerTokenException.class
    })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErroResposta handlerSecurityUnauthorizedException(Exception ex){
        return ErroResposta.resposta(HttpStatus.UNAUTHORIZED , "Token inválido ou expirado.");
    }

    @ExceptionHandler({
            AccessDeniedException.class,
            AuthorizationDeniedException.class,
    })
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErroResposta handlerSecurityForbiddenException(Exception ex){
        return ErroResposta.resposta(HttpStatus.FORBIDDEN , "Acesso negado: Você não tem permissão.");
    }

    @ExceptionHandler({
            AuthenticationException.class
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResposta handlerAuthException(Exception ex){
        return ErroResposta.resposta(HttpStatus.CONFLICT, "Email ou senha inválidos");
    }
}