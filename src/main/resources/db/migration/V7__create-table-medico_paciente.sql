create table medico_paciente(
  id bigint not null auto_increment,
  id_paciente bigint not null,
  id_medico bigint not null,
  CONSTRAINT fk_paciente FOREIGN KEY (id_paciente) REFERENCES pacientes(id) ON DELETE CASCADE,
  CONSTRAINT fk_medico FOREIGN KEY (id_medico) REFERENCES medicos(id) ON DELETE CASCADE,
  UNIQUE (id_paciente, id_medico),

  primary key(id)

);