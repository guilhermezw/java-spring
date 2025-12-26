package com.example.libraryapi.repository;

import com.example.libraryapi.model.Autor;
import com.example.libraryapi.model.GeneroLivro;
import com.example.libraryapi.model.Livro;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;



@Repository
public interface LivroRepository extends JpaRepository<Livro, UUID> , JpaSpecificationExecutor<Livro> {

    // Query Method
    // select * from livro where id_autor = id
    List<Livro> findByAutor(Autor autor);

    // select * from livro where titulo = titulo
    List<Livro> findByTitulo(String titulo);

    // select * from livro where isbn = isbn
    Optional<Livro> findByIsbn(String isbn);

    // select * from where titulo = ? and preco = ?
    List<Livro> findByTituloAndPreco(String titulo , BigDecimal preco);

    // select * from where titulo = ? and isbn = ?
    List<Livro> findByTituloOrIsbn(String titulo , String isbn);

    // select * from livro where data_publicaco between ? and ?
    List<Livro> findByDataPublicacaoBetween(LocalDate inicio , LocalDate fim);

    // JPQL -> Referencia as entidades e as propriedades
    // select l.* from livro as l order by l.titulo , l.preco
    @Query("select l from Livro as l order by l.titulo , l.preco")
    List<Livro> listarTodosOrdenadoPorTituloAndPreco();

    // select a.* from livro l join autor a an a.id = l.id_autor
    @Query("select a from Livro l join l.autor a")
    List<Autor> listarAutoresDosLivros();

    // select distinct l.* from livro l
    @Query("select distinct l.titulo from Livro l")
    List<String> listarNomesDiferentesLivros();

    @Query("""
        select l.genero
        from Livro l
        join l.autor a
        where a.nacionalidade = 'Brasileira'
        order by l.genero
    """)
    List<String> listarGenerosAutoresBrasileiros();

    // named parameters -> parametros nomeados
    @Query("select l from Livro l where l.genero = :genero order by :paramOrdenacao")
    List<Livro> findByGenero(@Param("genero") GeneroLivro generoLivro , @Param("paramOrdenacao") String nomePropriedade);

    // positional parameters
    @Query("select l from Livro l where l.genero = ?1 order by ?2")
    List<Livro> findByGeneroPositionalParameters(GeneroLivro generoLivro , String nomePropriedade);

    @Modifying
    @Transactional
    @Query("delete from Livro where genero = ?1")
    void deleteByGenero(GeneroLivro genero);

    @Modifying
    @Transactional
    @Query("update Livro set dataPublicacao = ?1")
    void atualizarDataPublicacao(LocalDate novaData);

    boolean existsByAutor(Autor autor);


}
