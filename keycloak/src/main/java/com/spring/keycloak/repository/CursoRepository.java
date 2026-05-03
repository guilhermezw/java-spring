package com.spring.keycloak.repository;

import com.spring.keycloak.enums.Categoria;
import com.spring.keycloak.enums.Tipo;
import com.spring.keycloak.model.CursoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CursoRepository extends JpaRepository<CursoModel, UUID> {

    List<CursoModel> findCursoModelByTipo(Tipo tipo);
}
