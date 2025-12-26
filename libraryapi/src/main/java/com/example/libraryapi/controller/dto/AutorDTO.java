package com.example.libraryapi.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(
        UUID id,
        @NotBlank(message = "Nome é obrigatorio")
        @Size(min = 2 , max = 100 , message = "O nome pode ter no máximo 100 caracteres")
        String nome ,
        @NotNull(message = "Data Nascimento é obrigatoria")
        @Past(message = "A data nascimento não pode ser uma data futura")
        LocalDate dataNascimento,
        @NotBlank(message = "Nacionalidade é obrigatoria")
        @Size(min = 2 , max = 50 , message = "A nacionalidade pode ter no máximo 50 caracteres")
        String nacionalidade) {

}
