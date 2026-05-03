package com.spring.keycloak.service;

import com.spring.keycloak.enums.Categoria;
import com.spring.keycloak.enums.Tipo;
import com.spring.keycloak.model.CursoModel;
import com.spring.keycloak.repository.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }


    public List<CursoModel> listaCursos() {
        return cursoRepository.findAll();
    }

    public List<CursoModel> listaCursoPremiun(){
        return cursoRepository.findCursoModelByTipo(Tipo.PREMIUM);
    }

    public List<CursoModel> listaCursoStandard(){
        return cursoRepository.findCursoModelByTipo(Tipo.STANDARD);
    }


}
