package com.Barbearia.BarbeariaProject.service;

import com.Barbearia.BarbeariaProject.dto.Agendamento.AgendamentoRequestDTO;
import com.Barbearia.BarbeariaProject.dto.Agendamento.AgendamentoResponseDTO;
import com.Barbearia.BarbeariaProject.entity.Agendamento;
import com.Barbearia.BarbeariaProject.entity.Barbeiro;
import com.Barbearia.BarbeariaProject.entity.Cliente;
import com.Barbearia.BarbeariaProject.entity.Servico;
import com.Barbearia.BarbeariaProject.exception.RegraNegocioException;
import com.Barbearia.BarbeariaProject.exception.ResourceNotFoundException;
import com.Barbearia.BarbeariaProject.mapper.IAgendamentoMapper;
import com.Barbearia.BarbeariaProject.repository.IAgendamentoRepository;
import com.Barbearia.BarbeariaProject.repository.IBarbeiroRepository;
import com.Barbearia.BarbeariaProject.repository.IServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final IAgendamentoRepository agendamentoRepository;
    private final IBarbeiroRepository barbeiroRepository;
    private final IServicoRepository servicoRepository;
    private final IAgendamentoMapper agendamentoMapper;
    private final ClienteService clienteService; // Reutilizando o serviço de cliente
    private final DisponibilidadeService disponibilidadeService; // Reutilizando o serviço de disponibilidade

    @Transactional
    public AgendamentoResponseDTO criarAgendamento(AgendamentoRequestDTO requestDTO) {
        // 1. Buscar entidades relacionadas
        Barbeiro barbeiro = barbeiroRepository.findById(requestDTO.getBarbeiroId())
                .orElseThrow(() -> new ResourceNotFoundException("Barbeiro não encontrado"));
        Servico servico = servicoRepository.findById(requestDTO.getServicoId())
                .orElseThrow(() -> new ResourceNotFoundException("Serviço não encontrado"));
        
        // 2. Validações de Regra de Negócio
        if (!barbeiro.getServicos().contains(servico)) {
            throw new RegraNegocioException("O barbeiro selecionado não oferece este serviço.");
        }

        List<java.time.LocalTime> horariosDisponiveis = disponibilidadeService.calcularHorariosDisponiveis(
                barbeiro.getId(), servico.getId(), requestDTO.getDataHoraInicio().toLocalDate());

        if (!horariosDisponiveis.contains(requestDTO.getDataHoraInicio().toLocalTime())) {
            throw new RegraNegocioException("O horário solicitado não está disponível.");
        }

        // 3. Encontrar ou criar o cliente
        Cliente cliente = clienteService.findOrCreateCliente(requestDTO.getCliente());

        // 4. Mapear e Salvar
        Agendamento agendamento = agendamentoMapper.toEntity(requestDTO);
        agendamento.setCliente(cliente); // Atribui o cliente encontrado/criado

        // Calcular e definir data de fim e status
        LocalDateTime dataFim = requestDTO.getDataHoraInicio().plusMinutes(servico.getDuracaoMinutos());
        agendamento.setDataHoraFim(dataFim);
        agendamento.setStatus("Confirmado");

        Agendamento agendamentoSalvo = agendamentoRepository.save(agendamento);

        return agendamentoMapper.toDTO(agendamentoSalvo);
    }
}