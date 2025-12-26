package com.example.arquiteturaspring;

import com.example.arquiteturaspring.todos.TodoEntity;
import com.example.arquiteturaspring.todos.TodoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

// @Lazy(false)
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
// @Scope(WebApplicationContext.SCOPE_REQUEST)
// @Scope(WebApplicationContext.SCOPE_SESSION)
// @Scope(WebApplicationContext.SCOPE_APPLICATION)
public class BeanGerenciado {
    @Autowired
    private TodoValidator validator;

    // Uso recomendado de @Autowired no construtor:
    @Autowired // Opcional, Spring injetará automaticamente.
    public BeanGerenciado(TodoValidator validator) {
        // Injeção de dependência via construtor promove:

        // 1. Obrigatoriedade: A classe precisa de certas dependências para ser criada.

        // 2. Imutabilidade: Dependências podem ser marcadas como 'final', não podendo ser alteradas.

        // 3. Facilidade de Testes: Melhora a testabilidade da classe ao permitir injeções diferentes.

        // Recomendação: Preferir injeção via construtor e, se usar Lombok, utilize @RequiredArgsConstructor para simplificar o código.
        this.validator = validator;
    }

    public void utilizar(){
        var todo = new TodoEntity();
        validator.validar(todo);
    }
    @Autowired
    public void setValidator(TodoValidator validator){
        this.validator = validator;
    }
}
