package com.example.libraryapi.controller.mappers;

import com.example.libraryapi.controller.dto.AutorDTO;
import com.example.libraryapi.model.Autor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AutorMapper {


    Autor toEntity(AutorDTO autorDTO);
    AutorDTO toDTO(Autor autor);
}
