package com.example.libraryapi.service;

import com.example.libraryapi.exception.OperacaoNaoPermitidaException;
import com.example.libraryapi.model.Autor;
import com.example.libraryapi.model.Usuario;
import com.example.libraryapi.repository.AutorRepository;
import com.example.libraryapi.repository.LivroRepository;
import com.example.libraryapi.security.SecurityService;
import com.example.libraryapi.validator.AutorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {

    private  AutorRepository autorRepository;
    private  AutorValidator autorValidator;
    private  LivroRepository livroRepository;
    private SecurityService securityService;

    public AutorService(AutorRepository autorRepository, AutorValidator autorValidator, LivroRepository livroRepository, SecurityService securityService) {
        this.autorRepository = autorRepository;
        this.autorValidator = autorValidator;
        this.livroRepository = livroRepository;
        this.securityService = securityService;
    }

    public Autor salvar(Autor autor){
        autorValidator.validar(autor);
        Usuario usuario = securityService.obterUsuarioLogado();
        autor.setUsuario(usuario);
        return autorRepository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id){
        return autorRepository.findById(id);
    }


    public void deletar(Autor autor){
        if(possuiLivro(autor)){
            throw new OperacaoNaoPermitidaException("Não é permitido excluir um Autor que possui livros cadastrados!");
        }
        autorRepository.delete(autor);
    }

    // Modo de Pesquisa Ineficiente
    public List<Autor> pesquisa (String nome , String nacionalidade){
        if(nome != null && nacionalidade != null){
            return autorRepository.findByNomeAndNacionalidade(nome , nacionalidade);
        }

        if(nome != null){
            return autorRepository.findByNome(nome);
        }

        if(nacionalidade != null){
            return autorRepository.findByNacionalidade(nacionalidade);
        }

        return autorRepository.findAll();
    }

    public void atualizar(Autor autor){
        if (autor.getId() == null){
            throw new IllegalArgumentException("Autor não encontrado");
        }
        autorValidator.validar(autor);
        autorRepository.save(autor);

    }

    // Modo de Pesquisa Eficiente
    public List<Autor> pesquisaByExample(String nome , String nacionalidade){
        var autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);

        // Cria um ExampleMatcher, que define COMO a comparação será feita
        ExampleMatcher matcher = ExampleMatcher
                .matching() // modo padrão: todos os campos informados devem bater (AND)
                .withIgnoreNullValues() //Ignora atributos nulos do objeto Autor
                                        // Se um campo for null, ele não entra query

                .withIgnoreCase() // Ignora diferença entre maiúsculas e minúsculas (case - insensitive)
                // Para Strings, usar "LIKE %valor%"
                // Ex: nome = "jo" vira LIKE '%jo'
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        // Criar o "exemplo (probe)
        // o objeto 'autor funciona como um filtro
        Example<Autor> autorExample = Example.of(autor , matcher);
        // Execulta a consulta no banco com base no exemplo
        return autorRepository.findAll(autorExample);
    }

    public boolean possuiLivro(Autor autor){
        return livroRepository.existsByAutor(autor);
    }
}
