package med.voll.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Page<Medico> findAllByAtivoTrue(Pageable pageable);

//    @Query("SELECT m FROM Medico m JOIN FETCH m.pacientes WHERE m.id = :id")
//    Optional<Medico> findByIdWithPacientes(@Param("id") Long id);
}
