package com.example.arquiteturaspring;

import com.example.arquiteturaspring.todos.MailSender;
import com.example.arquiteturaspring.todos.TodoRepository;
import com.example.arquiteturaspring.todos.TodoService;
import com.example.arquiteturaspring.todos.TodoValidator;
import jakarta.persistence.EntityManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.Connection;

public class ExemploInjecaoDependencia {
    public static void main(String[] args) throws Exception {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("url");
        dataSource.setUsername("user");
        dataSource.setPassword("password");

        Connection connection = dataSource.getConnection();
        EntityManager entityManager = null;

        TodoRepository repository = null; // new SimpleJpaRepository<TodoRepository , Integer>();
        TodoValidator todoValidator = new TodoValidator(repository);
        MailSender sender = new MailSender();

        TodoService todoService = new TodoService(repository, todoValidator, sender);

        // Instanciar BeanGerenciado desta forma não é recomendado:
        // BeanGerenciado beanGerenciado = new BeanGerenciado(null);
        // beanGerenciado.setValidator(todoValidator);

        // Razões para evitar a injeção via setter:
        // 1. **Injeção de Dependência**: Preferir construtor para assegurar que todas as dependências estejam presentes.
        // 2. **Obrigatoriedade**: Ao usar setter, as dependências se tornam opcionais, o que pode levar a um estado inválido.
        // 3. **Clareza**: A injeção por construtor deixa claro que uma dependência é necessária para o funcionamento da classe.
        // 4. **Mudança de Implementação**: Com setters, a implementação pode ser mudada em runtime, o que não é seguro.

        // Recomendação: Use sempre injeção via construtor para manter a coerência e segurança do seu código.

    }
}
