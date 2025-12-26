package com.example.libraryapi.repository;

import com.example.libraryapi.model.Autor;
import com.example.libraryapi.model.GeneroLivro;
import com.example.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    LivroRepository livroRepository;
    AutorRepository autorRepository;

    @Autowired
    public LivroRepositoryTest(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    @Test
    public void salvarTest(){
        Livro livro = new Livro();
        livro.setIsbn("94395");
        livro.setTitulo("O Aprendiz");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.BIOGRAFIA);
        livro.setDataPublicacao(LocalDate.of(1990 , 2, 12));

        Autor autor = autorRepository
               .findById(UUID.fromString("c1bf1b12-b82b-48e5-9f87-546b25c70d2c"))
               .orElse(null);

        livro.setAutor(new Autor());

        livroRepository.save(livro);
    }

    @Test
    public void salvarAutoreLivroTest(){
        Livro livro = new Livro();
        livro.setIsbn("943654");
        livro.setTitulo("O Dia D");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.BIOGRAFIA);
        livro.setDataPublicacao(LocalDate.of(1997 , 2, 23));

        Autor autor = new Autor();
        autor.setNome("Henrique");
        autor.setNacionalidade("Brasileria");
        autor.setDataNascimento(LocalDate.of(1968 , 5 , 21));

        autorRepository.save(autor);

        livro.setAutor(autor);

        livroRepository.save(livro);
    }

    @Test
    public void salvarCascadeTest(){
        Livro livro = new Livro();
        livro.setIsbn("94367");
        livro.setTitulo("A Guerra");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.BIOGRAFIA);
        livro.setDataPublicacao(LocalDate.of(1990 , 2, 12));

        Autor autor = new Autor();
        autor.setNome("Nicolas");
        autor.setNacionalidade("Brasileria");
        autor.setDataNascimento(LocalDate.of(1968 , 5 , 21));

        livro.setAutor(autor);

        livroRepository.save(livro);
    }

    @Test
    public void deletarTest(){
        UUID id = UUID.fromString("0bcd5a84-c6ac-4cf8-b5fd-46a5d28922a3");
        livroRepository.deleteById(id);
    }

    @Test
    public void deletarCascade(){
        UUID id = UUID.fromString("0bcd5a84-c6ac-4cf8-b5fd-46a5d28922a3");
        livroRepository.deleteById(id);
    }

    @Test
    @Transactional
    public void buscarLivroTest(){
        UUID id = UUID.fromString("0bcd5a84-c6ac-4cf8-b5fd-46a5d28922a3");
        Livro livro = livroRepository.findById(id).orElse(null);
        System.out.println("Livro: ");
        System.out.println(livro.getTitulo());
        System.out.println("Autor: ");
        System.out.println(livro.getAutor().getNome());
    }

    @Test
    public void pesquisaPorTituloTest(){
        List<Livro> lista = livroRepository.findByTitulo("O Aprendiz");
        lista.forEach(System.out::println);
    }

    @Test
    public void pesquisaPorIsbnTest(){
        Optional<Livro> livro = livroRepository.findByIsbn("94395");
        livro.ifPresent(System.out::println);
    }

    @Test
    public void pesquisaPorTituloEPrecoTest(){
        var preco = BigDecimal.valueOf(100);
        String tituloPesquisa = "O Aprendiz";

        List<Livro> lista = livroRepository.findByTituloAndPreco(tituloPesquisa,preco);
        lista.forEach(System.out::println);
    }

    @Test
    public void pesquisaPorTituloOuIsbnTest(){
        String isbnPesquisa = "94395";
        String tituloPesquisa = "O Aprendiz";

        List<Livro> lista = livroRepository.findByTituloOrIsbn(tituloPesquisa,isbnPesquisa);
        lista.forEach(System.out::println);
    }

    @Test
    public void listarLivrosComQueryJPQL(){
        var resultado = livroRepository.listarTodosOrdenadoPorTituloAndPreco();
        resultado.forEach(System.out::println);
    }

    @Test
    public void listarAutoresDosLivros(){
        var resultado = livroRepository.listarAutoresDosLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    public void listarNomesDiferentesLivros(){
        var resultado = livroRepository.listarNomesDiferentesLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    public void listarGenerosDeLivrosAutoresBrasileiros(){
        var resultado = livroRepository.listarGenerosAutoresBrasileiros();
        resultado.forEach(System.out::println);
    }

    @Test
    public void listarPorGeneroQueryParamTest(){
        var resultado = livroRepository.findByGenero(GeneroLivro.CIENCIA , "dataPublicacao");
        resultado.forEach(System.out::println);
    }

    @Test
    public void listarPorGeneroPositionalParamTest(){
        var resultado = livroRepository.findByGeneroPositionalParameters(GeneroLivro.CIENCIA , "dataPublicacao");
        resultado.forEach(System.out::println);
    }

    @Test
    public void deletePorGeneroTest(){
        livroRepository.deleteByGenero(GeneroLivro.BIOGRAFIA);
    }

    @Test
    public void atualizarDataPublicacaoTest(){
        livroRepository.atualizarDataPublicacao(LocalDate.of(2025 , 12 , 12));
    }


}