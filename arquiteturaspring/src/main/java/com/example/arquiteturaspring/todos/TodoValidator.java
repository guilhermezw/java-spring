package com.example.arquiteturaspring.todos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TodoValidator {
    TodoRepository todoRepository;

    public TodoValidator(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public void validar(TodoEntity todo){
        if(existeTodoComDescricao(todo.getDescricao())){
            throw new IllegalArgumentException("Já existe um todo com esta descrição");
        }
    }

    private boolean existeTodoComDescricao(String descricao){
        return todoRepository.existsByDescricao(descricao);
    }
}
