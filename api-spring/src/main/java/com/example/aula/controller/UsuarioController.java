package com.example.aula.controller;

import com.example.aula.dto.UsuarioRequestDTO;
import com.example.aula.dto.UsuarioResponseDTO;
import com.example.aula.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    // CREATE
    @PostMapping
    public ResponseEntity<Map<String, Object>> salvar(@Valid @RequestBody UsuarioRequestDTO dto) {
        usuarioService.salvarUsuario(dto);
        return ResponseEntity
                .created(null)
                .body(Map.of(
                        "mensagem", "Cadastrado com sucesso.",
                        "sucesso", true
                ));
    }
    // READ
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listar() {
        return ResponseEntity
                .ok()
                .body(usuarioService.listarTodos());
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequestDTO dto) {

        usuarioService.atualizarUsuario(id, dto);

        return ResponseEntity
                .ok()
                .body(Map.of(
                        "mensagem", "Atualizado com sucesso.",
                        "sucesso", true
                ));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> excluir(@PathVariable Long id) {
        usuarioService.excluirUsuario(id);
        return ResponseEntity
                .ok()
                .body(Map.of(
                        "mensagem", "Exluído com sucesso.",
                        "sucesso", true
                ));
    }

}
