package com.example.libraryapi.controller;

import com.example.libraryapi.controller.dto.UsuarioDTO;
import com.example.libraryapi.controller.mappers.UsuarioMapper;
import com.example.libraryapi.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private UsuarioService usuarioService;
    private UsuarioMapper mapper;

    public UsuarioController(UsuarioService usuarioService, UsuarioMapper mapper) {
        this.usuarioService = usuarioService;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody UsuarioDTO dto){
        var usuario = mapper.toEntity(dto);
        usuarioService.salvar(usuario);
    }
}
