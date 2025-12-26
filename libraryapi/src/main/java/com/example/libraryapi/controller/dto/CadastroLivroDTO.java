package com.example.libraryapi.controller.dto;

import com.example.libraryapi.model.GeneroLivro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CadastroLivroDTO(
        @NotBlank(message = "O isbn é obrigatório")
        @ISBN
        @Size(min = 5 , max = 20 , message = "O isbn deve ter entre 5 a 20 caracteres")
        String isbn ,
        @NotBlank(message = "O titulo é obrigatório")
        @Size(min = 5 , max = 150 , message = "O titulo deve ter entre 5 a 150 caracteres")
        String titulo,
        @NotNull
        @Past(message = "A data publicação não pode ser uma data futura")
        LocalDate dataPublicacao,
        GeneroLivro genero,
        BigDecimal preco,
        @NotNull
        UUID idAutor) {
}
