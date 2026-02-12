package api.repository;

import api.model.ReservaModel;
import api.model.SalaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservaRepository extends JpaRepository<ReservaModel, Long> {

    @Query("SELECT r FROM ReservaModel r WHERE r.sala.id = :sala AND r.inicio < :fim AND r.fim > :inicio")
    Optional<ReservaModel> consultarHorariosConflitantes(@Param("sala") Long salaId, @Param("inicio") LocalDateTime inicio , @Param("fim") LocalDateTime fim);
}
