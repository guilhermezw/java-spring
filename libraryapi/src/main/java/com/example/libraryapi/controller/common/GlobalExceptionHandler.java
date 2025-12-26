package com.example.libraryapi.controller.common;

import com.example.libraryapi.controller.dto.ErroCampo;
import com.example.libraryapi.controller.dto.ErroResposta;
import com.example.libraryapi.exception.CampoInvalidadoException;
import com.example.libraryapi.exception.OperacaoNaoPermitidaException;
import com.example.libraryapi.exception.RegistroDuplicadoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErroResposta handMethodArgumentNotValidException(MethodArgumentNotValidException erro){
        List<FieldError> fieldErrors = erro.getFieldErrors();
        List<ErroCampo> listaErros = fieldErrors
                .stream()
                .map(fieldError -> new ErroCampo(fieldError.getField() , fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
        return  new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value() , "Erro de validação." , listaErros);
    }

    @ExceptionHandler(RegistroDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResposta handleRegistroDuplicadoException(RegistroDuplicadoException erro){
       return ErroResposta.conflito(erro.getMessage());
    }

    @ExceptionHandler(OperacaoNaoPermitidaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResposta handleOperacaoNaoPermitidaException(OperacaoNaoPermitidaException erro){
        return ErroResposta.respostaPadrao(erro.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErroResposta handleAccesDeniedException(AccessDeniedException erro){
        return new ErroResposta(HttpStatus.FORBIDDEN.value(), "Acesso negado!." , List.of());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroResposta handleErrosNaoTratado(RuntimeException erro){
        return new ErroResposta(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ocorreu um erro inesperado. Entre em contato com o suporte" , List.of());
    }

    @ExceptionHandler(CampoInvalidadoException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handleCampoInvalidoException(CampoInvalidadoException erro){
        return new ErroResposta(HttpStatus.UNAUTHORIZED.value(), "Erro de validação" , List.of(new ErroCampo(erro.getCampo() , erro.getMessage())));
    }
}
