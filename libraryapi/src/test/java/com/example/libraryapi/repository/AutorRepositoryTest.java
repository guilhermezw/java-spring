package com.example.libraryapi.repository;

import com.example.libraryapi.model.Autor;
import com.example.libraryapi.model.GeneroLivro;
import com.example.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    AutorRepository autorRepository;
    LivroRepository livroRepository;

    @Autowired
    public AutorRepositoryTest(AutorRepository autorRepository, LivroRepository livroRepository) {
        this.autorRepository = autorRepository;
        this.livroRepository = livroRepository;
    }

    @Test
    public void salvarTest(){
        Autor autor = new Autor();
        autor.setNome("João");
        autor.setNacionalidade("Brasileria");
        autor.setDataNascimento(LocalDate.of(1968 , 5 , 21));

        var usuarioSalvar = autorRepository.save(autor);
        System.out.println("Autor salvo: " + usuarioSalvar);
    }

    @Test
    public void atualizarTest(){
        var id = UUID.fromString("c1afae82-cfcc-4563-a229-87b5b706360d");

        Optional<Autor> findByUsuario = autorRepository.findById(id);
        if(findByUsuario.isPresent()){

            Autor usuarioEncontrado = findByUsuario.get();
            System.out.println("Dados do Usuário");
            System.out.println(usuarioEncontrado);

            usuarioEncontrado.setNome("Nicolas");
            usuarioEncontrado.setDataNascimento(LocalDate.of(1978 , 5, 23));

            var usuarioAtualizar = autorRepository.save(usuarioEncontrado);
            System.out.println("Autor atualizado: " + usuarioAtualizar);
        }

    }

    @Test
    public void deletarPorIdTest(){
        var id = UUID.fromString("c1afae82-cfcc-4563-a229-87b5b706360d");
        autorRepository.existsById(id);
    }

    @Test
    public void deletarTest(){
        var id = UUID.fromString("c1afae82-cfcc-4563-a229-87b5b706360d");
        var usuario = autorRepository.findById(id).get();
        autorRepository.delete(usuario);
    }

    @Test
    public void listarTest(){
        List<Autor> lista = autorRepository.findAll();
        lista.forEach(System.out::println);
    }

    @Test
    public void countTest(){
        System.out.println("Contagem de Usuários: " + autorRepository.count());
    }

    @Test
    public void salvarAutorComLivrosTest(){
        Autor autor = new Autor();
        autor.setNome("Enzo Martins");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1974 , 5 , 21));

        Livro livro = new Livro();
        livro.setIsbn("95433");
        livro.setTitulo("A sociedade");
        livro.setPreco(BigDecimal.valueOf(350));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setDataPublicacao(LocalDate.of(1999 , 7, 12));
        livro.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);

        autorRepository.save(autor);
        livroRepository.saveAll(autor.getLivros());
    }

    @Test
    public void listarLivrosAutor(){
        var id = UUID.fromString("5c9ca445-ad51-465e-9428-524d1a970fff");
        var autor = autorRepository.findById(id).get();

        List<Livro> livrosLista = livroRepository.findByAutor(autor);
        autor.setLivros(livrosLista);

        autor.getLivros().forEach(System.out::println);
    }
}

