package med.voll.api.domain.medicopaciente;

import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.paciente.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicoPacienteRepository extends JpaRepository<MedicoPaciente, Long> {
    List<MedicoPaciente> findByMedicoId(Long medicoId);

    List<MedicoPaciente> findByPacienteId(Long pacienteId);
}

