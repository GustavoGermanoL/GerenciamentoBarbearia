package com.Barbearia.BarbeariaProject.service;

import com.Barbearia.BarbeariaProject.entity.Agendamento;
import com.Barbearia.BarbeariaProject.entity.HorarioTrabalho;
import com.Barbearia.BarbeariaProject.entity.Servico;
import com.Barbearia.BarbeariaProject.exception.ResourceNotFoundException;
import com.Barbearia.BarbeariaProject.repository.IAgendamentoRepository;
import com.Barbearia.BarbeariaProject.repository.IHorarioTrabalhoRepository;
import com.Barbearia.BarbeariaProject.repository.IServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DisponibilidadeService {

    private final IHorarioTrabalhoRepository horarioTrabalhoRepository;
    private final IAgendamentoRepository agendamentoRepository;
    private final IServicoRepository servicoRepository;

    public List<LocalTime> calcularHorariosDisponiveis(Long barbeiroId, Long servicoId, LocalDate data) {
        int diaDaSemana = data.getDayOfWeek().getValue() % 7; // Convertendo para 0=Domingo..6=Sábado

        // 1. Buscar informações essenciais
        Servico servico = servicoRepository.findById(servicoId)
                .orElseThrow(() -> new ResourceNotFoundException("Serviço não encontrado"));
        
        HorarioTrabalho horarioTrabalho = horarioTrabalhoRepository
                .findByBarbeiroIdAndDiaSemana(barbeiroId, diaDaSemana)
                .orElse(null); // Pode não trabalhar no dia

        if (horarioTrabalho == null || !horarioTrabalho.isAtivo()) {
            return new ArrayList<>(); // Retorna lista vazia se não trabalha no dia
        }

        // 2. Buscar agendamentos existentes
        LocalDateTime inicioDoDia = data.atStartOfDay();
        LocalDateTime fimDoDia = data.atTime(LocalTime.MAX);
        List<Agendamento> agendamentosDoDia = agendamentoRepository
                .findByBarbeiroIdAndDataHoraInicioBetween(barbeiroId, inicioDoDia, fimDoDia);

        // 3. Gerar e filtrar slots de horário
        List<LocalTime> horariosDisponiveis = new ArrayList<>();
        LocalTime slotAtual = horarioTrabalho.getHoraInicio();
        int duracaoServico = servico.getDuracaoMinutos();

        while (slotAtual.plusMinutes(duracaoServico).isBefore(horarioTrabalho.getHoraFim()) || slotAtual.plusMinutes(duracaoServico).equals(horarioTrabalho.getHoraFim())) {
            LocalDateTime inicioSlot = data.atTime(slotAtual);
            LocalDateTime fimSlot = inicioSlot.plusMinutes(duracaoServico);
            
            boolean ocupado = false;
            for (Agendamento agendamento : agendamentosDoDia) {
                // Verifica se o slot proposto conflita com algum agendamento existente
                if (inicioSlot.isBefore(agendamento.getDataHoraFim()) && fimSlot.isAfter(agendamento.getDataHoraInicio())) {
                    ocupado = true;
                    break;
                }
            }

            if (!ocupado) {
                horariosDisponiveis.add(slotAtual);
            }
            // Avança para o próximo slot, por exemplo, a cada 15 minutos
            slotAtual = slotAtual.plusMinutes(15); 
        }

        return horariosDisponiveis;
    }
}