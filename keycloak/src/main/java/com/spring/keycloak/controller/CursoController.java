package com.spring.keycloak.controller;

import com.spring.keycloak.model.CursoModel;
import com.spring.keycloak.service.CursoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }


    @GetMapping
    public ResponseEntity<List<CursoModel>> listaCursos() {
        return ResponseEntity.status(HttpStatus.OK).body(cursoService.listaCursos());
    }

    @GetMapping("/premium")
    public ResponseEntity<List<CursoModel>> listaCursosPremium(){
        return ResponseEntity.status(HttpStatus.OK).body(cursoService.listaCursoPremiun());
    }

    @GetMapping("/standard")
    public ResponseEntity<List<CursoModel>> listaCursosStandard(){
        return ResponseEntity.status(HttpStatus.OK).body(cursoService.listaCursoStandard());
    }



}
