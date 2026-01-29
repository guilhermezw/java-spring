package com.example.libraryapi.controller;

import com.example.libraryapi.model.Client;
import com.example.libraryapi.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/clients")
@Slf4j
public class ClientController {

    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


    @PostMapping
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Map<String , Object>> salvar(@RequestBody Client client) {
        log.info("Registrado novo Client: {} com scope: {}", client.getClientId() , client.getScope());
        clientService.salvar(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message" , "Client criado com sucesso", "success", true));
    }
}
