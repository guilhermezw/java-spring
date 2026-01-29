package com.example.libraryapi.controller;

import com.example.libraryapi.controller.dto.AutorDTO;
import com.example.libraryapi.controller.mappers.AutorMapper;
import com.example.libraryapi.model.Autor;
import com.example.libraryapi.service.AutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/autores") //http://localhost:8080//autores
@Tag(name = "Autores")
@Slf4j
public class AutorController implements GenericController {


    private AutorService autorService;
    private AutorMapper mapper;


    public AutorController(AutorService autorService, AutorMapper mapper) {
        this.autorService = autorService;
        this.mapper = mapper;
    }

    @PostMapping
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Salvar" , description = "Cadastrar novo autor")
    @ApiResponses({
            @ApiResponse(responseCode = "201" , description = "Cadastrado com sucesso."),
            @ApiResponse(responseCode = "422" , description = "Erro de validação."),
            @ApiResponse(responseCode = "409" , description = "Autor já cadastrado.")
    })
    public ResponseEntity<Object> salvar(@RequestBody @Valid AutorDTO autorDTO) {
        log.info("Salvando Autor: {}", autorDTO.nome());

        Autor autor = mapper.toEntity(autorDTO);
        autorService.salvar(autor);
        URI location = gerarHeaderLocation(autor.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(location);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERADOR' , 'GERENTE')")
    @Operation(summary = "Obter Detalhes" , description = "Retorna os dados do autor pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200" , description = "Autor encontrado."),
            @ApiResponse(responseCode = "404" , description = "Autor não encontrado."),
    })
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);

        return autorService
                .obterPorId(idAutor)
                .map(autor -> {
                    AutorDTO dto = mapper.toDTO(autor);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Deletar" , description = "Deletar um autor existente")
    @ApiResponses({
            @ApiResponse(responseCode = "204" , description = "Deletado com sucesso."),
            @ApiResponse(responseCode = "404" , description = "Autor não encontrado."),
            @ApiResponse(responseCode = "400" , description = "Autor possui livro cadastrado.")
    })
    public ResponseEntity<Object> deletar(@PathVariable("id") String id) {
        log.info("Deletando Autor de ID: {}", id);

        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterPorId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            autorService.deletar(autorOptional.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OPERADOR' , 'GERENTE')")
    @Operation(summary = "Pesquisar" , description = "Realiza pesquisa de autores por parametros")
    @ApiResponses({
            @ApiResponse(responseCode = "200" , description = "Sucesso."),
    })
    public ResponseEntity<List<AutorDTO>> pesquisa(@RequestParam(value = "nome", required = false) String nome, @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {
        List<Autor> resultado = autorService.pesquisaByExample(nome, nacionalidade);
        List<AutorDTO> lista = resultado
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Atualizar" , description = "Atualiza um autor existente")
    @ApiResponses({
            @ApiResponse(responseCode = "204" , description = "Atualizado com sucesso."),
            @ApiResponse(responseCode = "404" , description = "Autor não encontrado."),
            @ApiResponse(responseCode = "409" , description = "Autor já cadastrado.")
    })
    public ResponseEntity<Object> atualizar(@PathVariable("id") String id, @RequestBody @Valid AutorDTO dto) {

        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterPorId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        var autor = autorOptional.get();
        autor.setNome(dto.nome());
        autor.setDataNascimento(dto.dataNascimento());
        autor.setNacionalidade(dto.nacionalidade());
        autorService.atualizar(autor);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();


    }
}
