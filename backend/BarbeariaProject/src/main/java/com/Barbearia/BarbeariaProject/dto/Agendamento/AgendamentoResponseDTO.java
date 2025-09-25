package com.Barbearia.BarbeariaProject.dto.Agendamento;

import java.time.LocalDateTime;

import com.Barbearia.BarbeariaProject.dto.Barbeiro.BarbeiroResponseDTO;
import com.Barbearia.BarbeariaProject.dto.Cliente.ClienteResponseDTO;
import com.Barbearia.BarbeariaProject.dto.Servico.ServicoResponseDTO;

import lombok.Data;

@Data
public class AgendamentoResponseDTO {
    private Long id;
    private ClienteResponseDTO cliente; // Objeto completo do cliente
    private BarbeiroResponseDTO barbeiro; // Objeto completo do barbeiro
    private ServicoResponseDTO servico; // Objeto completo do serviço
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;
    private String status;
}
