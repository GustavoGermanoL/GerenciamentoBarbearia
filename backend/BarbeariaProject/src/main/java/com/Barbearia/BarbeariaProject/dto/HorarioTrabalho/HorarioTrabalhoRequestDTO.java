package com.Barbearia.BarbeariaProject.dto.HorarioTrabalho;

import java.time.LocalTime;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class HorarioTrabalhoRequestDTO {
    @NotNull
    private Long barbeiroId;

    @NotNull
    @Min(value = 0, message = "O dia da semana deve ser entre 0 (Domingo) e 6 (Sábado)")
    @Max(value = 6, message = "O dia da semana deve ser entre 0 (Domingo) e 6 (Sábado)")
    private Integer diaSemana;

    @NotNull
    private LocalTime horaInicio;

    @NotNull
    private LocalTime horaFim;
    
}
