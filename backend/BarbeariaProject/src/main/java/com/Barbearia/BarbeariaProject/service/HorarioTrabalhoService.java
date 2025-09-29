package com.Barbearia.BarbeariaProject.service;

import com.Barbearia.BarbeariaProject.dto.HorarioTrabalho.HorarioTrabalhoRequestDTO;
import com.Barbearia.BarbeariaProject.dto.HorarioTrabalho.HorarioTrabalhoResponseDTO;
import com.Barbearia.BarbeariaProject.entity.Barbeiro;
import com.Barbearia.BarbeariaProject.entity.HorarioTrabalho;
import com.Barbearia.BarbeariaProject.exception.RegraNegocioException;
import com.Barbearia.BarbeariaProject.exception.ResourceNotFoundException;
import com.Barbearia.BarbeariaProject.mapper.IHorarioTrabalhoMapper;
import com.Barbearia.BarbeariaProject.repository.IBarbeiroRepository;
import com.Barbearia.BarbeariaProject.repository.IHorarioTrabalhoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HorarioTrabalhoService {

    private final IHorarioTrabalhoRepository horarioTrabalhoRepository;
    private final IBarbeiroRepository barbeiroRepository;
    private final IHorarioTrabalhoMapper horarioTrabalhoMapper;

    @Transactional
    public HorarioTrabalhoResponseDTO definirOuAtualizarHorario(HorarioTrabalhoRequestDTO requestDTO) {
        // Valida se o barbeiro existe
        Barbeiro barbeiro = barbeiroRepository.findById(requestDTO.getBarbeiroId())
                .orElseThrow(() -> new ResourceNotFoundException("Barbeiro não encontrado com o ID: " + requestDTO.getBarbeiroId()));

        if (requestDTO.getHoraInicio().isAfter(requestDTO.getHoraFim())) {
            throw new RegraNegocioException("A hora de início não pode ser depois da hora de fim.");
        }

        // Verifica se já existe um horário para este barbeiro neste dia da semana para decidir se cria um novo ou atualiza
        Optional<HorarioTrabalho> horarioExistenteOpt = horarioTrabalhoRepository
                .findByBarbeiroIdAndDiaSemana(requestDTO.getBarbeiroId(), requestDTO.getDiaSemana());

        HorarioTrabalho horarioParaSalvar;

        if (horarioExistenteOpt.isPresent()) {
            // Atualiza o horário existente
            horarioParaSalvar = horarioExistenteOpt.get();
            horarioParaSalvar.setHoraInicio(requestDTO.getHoraInicio());
            horarioParaSalvar.setHoraFim(requestDTO.getHoraFim());
            horarioParaSalvar.setAtivo(true); // Garante que está ativo ao ser atualizado
        } else {
            // Cria um novo horário
            horarioParaSalvar = new HorarioTrabalho();
            horarioParaSalvar.setBarbeiro(barbeiro);
            horarioParaSalvar.setDiaSemana(requestDTO.getDiaSemana());
            horarioParaSalvar.setHoraInicio(requestDTO.getHoraInicio());
            horarioParaSalvar.setHoraFim(requestDTO.getHoraFim());
        }

        HorarioTrabalho horarioSalvo = horarioTrabalhoRepository.save(horarioParaSalvar);
        return horarioTrabalhoMapper.toDTO(horarioSalvo);
    }

    @Transactional(readOnly = true)
    public List<HorarioTrabalhoResponseDTO> listarHorariosPorBarbeiro(Long barbeiroId) {
        if (!barbeiroRepository.existsById(barbeiroId)) {
            throw new ResourceNotFoundException("Barbeiro não encontrado com o ID: " + barbeiroId);
        }

        List<HorarioTrabalho> horarios = horarioTrabalhoRepository.findAllByBarbeiroIdOrderByDiaSemana(barbeiroId);
        return horarios.stream()
                .map(horarioTrabalhoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void removerHorario(Long horarioId) {
        if (!horarioTrabalhoRepository.existsById(horarioId)) {
            throw new ResourceNotFoundException("Horário de trabalho não encontrado com o ID: " + horarioId);
        }
        horarioTrabalhoRepository.deleteById(horarioId);
    }
}