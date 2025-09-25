package com.Barbearia.BarbeariaProject.dto.HorarioTrabalho;

import java.time.LocalTime;

import lombok.Data;

@Data
public class HorarioTrabalhoResponseDTO {

    private Long id;
    private Long barbeiroId;
    private Integer diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private boolean ativo;

}
