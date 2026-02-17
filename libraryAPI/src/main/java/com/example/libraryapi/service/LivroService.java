package com.example.libraryapi.service;

import com.example.libraryapi.model.GeneroLivro;
import com.example.libraryapi.model.Livro;
import com.example.libraryapi.model.Usuario;
import com.example.libraryapi.repository.LivroRepository;
import com.example.libraryapi.repository.specs.LivroSpecs;
import com.example.libraryapi.security.SecurityService;
import com.example.libraryapi.validator.LivroValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;



import java.util.Optional;
import java.util.UUID;

@Service
public class LivroService {

    private LivroRepository livroRepository;
    private LivroValidator livroValidator;
    private SecurityService securityService;


    public LivroService(LivroRepository livroRepository, LivroValidator livroValidator, SecurityService securityService) {
        this.livroRepository = livroRepository;
        this.livroValidator = livroValidator;
        this.securityService = securityService;
    }

    public Livro salvar(Livro livro){
        livroValidator.validar(livro);
        Usuario usuario = securityService.obterUsuarioLogado();
        livro.setUsuario(usuario);
        return livroRepository.save(livro);
    }

    public Optional<Livro> obterPorID(UUID id){
        return livroRepository.findById(id);
    }

    public void deletar(Livro livro){
        livroRepository.delete(livro);
    }

    // isbn , titulo , nome autor , genero , ano de publicação
    public Page<Livro> pesquisa(String isbn, String titulo, String nomeAutor, GeneroLivro genero, Integer anoPublicacao , Integer pagina , Integer tamanhoPagina){

        // select * from livro where isbn = :isbn and nomeAutor =
        /**
         * Specification<Livro> specs = Specification
         *                 .where(LivroSpecs.isbnEqual(isbn))
         *                 .and(LivroSpecs.tituloLike(titulo))
         *                 .and(LivroSpecs.generoEqual(genero))
         *                 ;
         */

        // select * from livro where 0 = 0
        Specification<Livro> specs = Specification.where((root, query, cb) -> cb.conjunction() );

        if(isbn != null){
            // query = query and isbn = :isbn
            specs = specs.and(LivroSpecs.isbnEqual(isbn));
        }

        if(titulo != null){
            specs = specs.and(LivroSpecs.tituloLike(titulo));
        }

        if(genero != null){
            specs = specs.and(LivroSpecs.generoEqual(genero));
        }

        if (anoPublicacao != null){
            specs = specs.and(LivroSpecs.anoPublicacoEqual(anoPublicacao));
        }

        if (nomeAutor != null){
            specs = specs.and(LivroSpecs.nomeAutorLike(nomeAutor));
        }

        Pageable pageRequest = PageRequest.of(pagina , tamanhoPagina);
        return livroRepository.findAll(specs , pageRequest);
    }

    public void atualizar(Livro livro) {
        if(livro.getId() ==  null){
            throw new IllegalArgumentException("Autor não encontrado");
        }
        livroValidator.validar(livro);
        livroRepository.save(livro);
    }
}
