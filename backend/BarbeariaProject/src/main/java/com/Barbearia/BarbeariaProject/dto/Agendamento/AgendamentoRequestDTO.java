package com.Barbearia.BarbeariaProject.dto.Agendamento;

import java.time.LocalDateTime;

import com.Barbearia.BarbeariaProject.dto.Cliente.ClienteRequestDTO;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AgendamentoRequestDTO {
    // Em vez de passar o ID do cliente, podemos passar os dados para encontrá-lo ou criá-lo
    @NotNull(message = "Os dados do cliente são obrigatórios")
    private ClienteRequestDTO cliente;

    @NotNull(message = "O ID do barbeiro é obrigatório")
    private Long barbeiroId;

    @NotNull(message = "O ID do serviço é obrigatório")
    private Long servicoId;

    @NotNull(message = "A data e hora de início são obrigatórias")
    @Future(message = "A data do agendamento deve ser no futuro")
    private LocalDateTime dataHoraInicio;
    
}
