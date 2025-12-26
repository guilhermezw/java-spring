package com.example.arquiteturaspring.todos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @PostMapping("/salvar")
    public ResponseEntity<Map<String , Object>> salvar (TodoEntity todo){
        try{
            todoService.salvar(todo);
        }catch (IllegalArgumentException e){
            var mensagemErro = e.getMessage();
            throw new ResponseStatusException(HttpStatus.CONFLICT, mensagemErro);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("mensagem" , "Salvo" , "sucesso" , true));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String , Object>> atualizarStatus (@PathVariable ("id") Integer id , @RequestBody TodoEntity todo){
        todo.setId(id);
        todoService.atualizarStatus(todo);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("mensagem" , "Atualizado" , "sucesso" , true));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoEntity> buscar (@PathVariable ("id") Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(todoService.buscarPorId(id));
    }
}
