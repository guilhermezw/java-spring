package api.repository;

import api.model.SalaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SalaRepository extends JpaRepository<SalaModel, Long> {
    Optional<SalaModel> findByNome(String nome);
}
