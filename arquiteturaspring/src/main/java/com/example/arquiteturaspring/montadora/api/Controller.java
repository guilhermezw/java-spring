package com.example.arquiteturaspring.montadora.api;

import com.example.arquiteturaspring.montadora.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carro")
public class Controller {
    
    @Autowired
    // Essa anotação é usada quando você precisa especificar qual implementação de bean deseja injetar
    // @Qualifier("motorTurbo")
    @Aspirado
    private Motor motor;
    
    @PostMapping("/ligar")
    public CarroStatus ligarCarro(@RequestBody Chave chave){
        var carro= new HondaNSX(motor);
        return carro.darIgnicao(chave);
    }
}
