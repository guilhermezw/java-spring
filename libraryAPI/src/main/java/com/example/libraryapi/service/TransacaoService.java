package com.example.libraryapi.service;

import com.example.libraryapi.model.Autor;
import com.example.libraryapi.model.GeneroLivro;
import com.example.libraryapi.model.Livro;
import com.example.libraryapi.repository.AutorRepository;
import com.example.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransacaoService {

    private AutorRepository autorRepository;
    private LivroRepository livroRepository;


    public TransacaoService(AutorRepository autorRepository, LivroRepository livroRepository) {
        this.autorRepository = autorRepository;
        this.livroRepository = livroRepository;
    }

    @Transactional
    public void atualizacaoSemAtualizar(){
        var livro = livroRepository.findById(UUID.fromString("5479eb09-f329-4686-be62-1a70f010fb8a")).orElse(null);
        livro.setTitulo("Sapiens");
    }

    @Transactional
    public void executar(){
        // salvar o autor
        Autor autor = new Autor();
        autor.setNome("Francisco");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1974 , 5 , 21));
        autorRepository.save(autor);

        // salva o livro
        Livro livro = new Livro();
        livro.setIsbn("954557");
        livro.setTitulo("Brasil");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setDataPublicacao(LocalDate.of(1999 , 7, 12));
        livro.setAutor(autor);
        livroRepository.save(livro);

        if(autor.getNome().equals("José")){
            throw new RuntimeException("Rollback!");
        }
    }
}
