package com.example.libraryapi.controller;

import com.example.libraryapi.controller.dto.CadastroLivroDTO;
import com.example.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import com.example.libraryapi.controller.mappers.LivroMapper;
import com.example.libraryapi.model.GeneroLivro;
import com.example.libraryapi.model.Livro;
import com.example.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/livros")
public class LivroController implements GenericController {

    private LivroService livroService;
    private LivroMapper mapper;

    public LivroController(LivroService livroService, LivroMapper mapper) {
        this.livroService = livroService;
        this.mapper = mapper;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('OPERADOR' , 'GERENTE')")
    public ResponseEntity<Object> salvar(@RequestBody @Valid CadastroLivroDTO dto) {
        Livro salvarLivro = mapper.toEntity(dto);
        livroService.salvar(salvarLivro);
        URI location = gerarHeaderLocation(salvarLivro.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(location);

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERADOR' , 'GERENTE')")
    public ResponseEntity<ResultadoPesquisaLivroDTO> obterDetalhes(@PathVariable("id") String id) {
        return livroService.obterPorID(UUID.fromString(id))
                .map(livro -> {
                    var dto = mapper.toDTO(livro);
                    return ResponseEntity.status(HttpStatus.OK).body(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERADOR' , 'GERENTE')")
    public ResponseEntity<Object> deletar(@PathVariable("id") String id) {
        return livroService.obterPorID(UUID.fromString(id))
                .map(livro -> {
                    livroService.deletar(livro);
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OPERADOR' , 'GERENTE')")
    public ResponseEntity<Page<ResultadoPesquisaLivroDTO>> pesquisa(
            @RequestParam(value = "isbn", required = false)
            String isbn,
            @RequestParam(value = "titulo", required = false)
            String titulo,
            @RequestParam(value = "nome-autor", required = false)
            String nomeAutor,
            @RequestParam(value = "genero", required = false)
            GeneroLivro genero,
            @RequestParam(value = "ano-publicacao", required = false)
            Integer anoPublicacao,
            @RequestParam(value = "pagina", defaultValue = "0")
            Integer pagina,
            @RequestParam(value = "tamanho-pagina", defaultValue = "10")
            Integer tamanhoPagina
    ) {
        Page<Livro> paginaResultado = livroService.pesquisa(isbn, titulo, nomeAutor, genero, anoPublicacao, pagina, tamanhoPagina);
        Page<ResultadoPesquisaLivroDTO> resultado = paginaResultado.map(mapper::toDTO);
        return ResponseEntity.ok(resultado);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERADOR' , 'GERENTE')")
    public ResponseEntity<Object> atualizar(@PathVariable("id") String id, @RequestBody @Valid CadastroLivroDTO dto) {
        return livroService.obterPorID(UUID.fromString(id))
                .map(livro -> {
                    Livro atualizarLivro = mapper.toEntity(dto);
                    livro.setDataPublicacao(atualizarLivro.getDataPublicacao());
                    livro.setIsbn(atualizarLivro.getIsbn());
                    livro.setPreco(atualizarLivro.getPreco());
                    livro.setGenero(atualizarLivro.getGenero());
                    livro.setTitulo(atualizarLivro.getTitulo());
                    livro.setAutor(atualizarLivro.getAutor());

                    livroService.atualizar(livro);

                    return ResponseEntity.noContent().build();

                }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
