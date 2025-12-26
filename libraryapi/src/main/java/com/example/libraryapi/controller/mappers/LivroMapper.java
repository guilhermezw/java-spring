package com.example.libraryapi.controller.mappers;

import com.example.libraryapi.controller.dto.CadastroLivroDTO;
import com.example.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import com.example.libraryapi.model.Livro;
import com.example.libraryapi.repository.AutorRepository;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring" , uses = AutorMapper.class)
public abstract class LivroMapper {

    @Autowired
    AutorRepository autorRepository;

    public abstract Livro toEntity(CadastroLivroDTO dto);

    @AfterMapping
    protected void mapAutor(CadastroLivroDTO dto, @MappingTarget Livro livro) {
        // Após o MapStruct criar o objeto Livro,
        // buscamos o Autor no banco usando o IdAutor do DTO
        livro.setAutor(
                autorRepository.findById(dto.idAutor()).orElse(null) // Se autor não existir como null (evita erro)
        );
    }

    public abstract ResultadoPesquisaLivroDTO toDTO(Livro livro);

}
