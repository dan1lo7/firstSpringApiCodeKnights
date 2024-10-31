package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.medico.*;
import med.voll.api.domain.medicopaciente.DadosMedicoPaciente;
import med.voll.api.domain.medicopaciente.MedicoPaciente;
import med.voll.api.domain.medicopaciente.MedicoPacienteRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoPacienteRepository repositoryMedicoPaciente;

    @Autowired
    private MedicoRepository repositoryMedico;

    @Autowired
    private PacienteRepository repositoryPaciente;


    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoMedico> cadastrar(@RequestBody @Valid DadosCadastroMedico dados,
            UriComponentsBuilder uriBuilder) {
        var medico = new Medico(dados);
        repositoryMedico.save(medico);
        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemMedico>> listar(Pageable pageable) {
        Page<DadosListagemMedico> page = repositoryMedico.findAllByAtivoTrue(pageable)
                .map(DadosListagemMedico::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoMedico> atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
        var medico = repositoryMedico.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        var medico = repositoryMedico.getReferenceById(id);
        medico.excluir();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoMedico> detalhar(@PathVariable Long id) {
        var medico = repositoryMedico.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @GetMapping("{id}/pacientes")
    public ResponseEntity mostrarLista (@PathVariable long id){
        var medicoPacienteList =  repositoryMedicoPaciente.findByMedicoId(id);

        return ResponseEntity.ok(medicoPacienteList);
    }

    @PostMapping("/pacientes")
    @Transactional
    public ResponseEntity criarMedicoPaciente(@RequestBody @Valid DadosMedicoPaciente dados) {
        var paciente = repositoryPaciente.getReferenceById(dados.pacienteId());
        var medico = repositoryMedico.getReferenceById(dados.medicoId());

        if (paciente == null || medico == null) {
            return ResponseEntity.badRequest().build();
        }

        var medicoPaciente = new MedicoPaciente();
        medicoPaciente.setPaciente(paciente);
        medicoPaciente.setMedico(medico);
        medicoPaciente.setDataConsulta(LocalDate.now());

        repositoryMedicoPaciente.save(medicoPaciente);

        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().build().toUri()).build();
    }
}