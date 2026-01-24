package com.example.aula.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UsuarioResponseDTO {
    // Define campos que serão exibidos.
    // Após consulta no banco de dados, retornarão: id, nome, email, senha.
    // Omite o id e senha

    private String nome;
    private String email;
}
