package com.example.libraryapi.repository;

import com.example.libraryapi.service.TransacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class TransacoesTest {

    TransacaoService transacaoService;

    @Autowired
    public TransacoesTest(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    /**
     * Commit -> confirmar as alterações
     * Rallback -> desfazer as alterações
     */

    @Test
    public void transacaoSimples(){
        transacaoService.executar();

    }

    @Test
    public void transacaoEstadoManaged(){
        transacaoService.atualizacaoSemAtualizar();

    }
}
