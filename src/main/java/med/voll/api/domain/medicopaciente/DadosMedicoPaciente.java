package med.voll.api.domain.medicopaciente;

import java.time.LocalDate;

public record DadosMedicoPaciente(long pacienteId, long medicoId, LocalDate dataConsulta) {
}
