package com.Barbearia.BarbeariaProject.service;

import com.Barbearia.BarbeariaProject.dto.Servico.ServicoRequestDTO;
import com.Barbearia.BarbeariaProject.dto.Servico.ServicoResponseDTO;
import com.Barbearia.BarbeariaProject.entity.Servico;
import com.Barbearia.BarbeariaProject.exception.RegraNegocioException;
import com.Barbearia.BarbeariaProject.exception.ResourceNotFoundException;
import com.Barbearia.BarbeariaProject.mapper.IServicoMapper;
import com.Barbearia.BarbeariaProject.repository.IServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServicoService {

    private final IServicoRepository servicoRepository;
    private final IServicoMapper servicoMapper;

    @Transactional
    public ServicoResponseDTO criarServico(ServicoRequestDTO requestDTO) {
        Servico servico = servicoMapper.toEntity(requestDTO);
        Servico servicoSalvo = servicoRepository.save(servico);
        return servicoMapper.toDTO(servicoSalvo);
    }

    @Transactional(readOnly = true)
    public List<ServicoResponseDTO> listarTodos() {
        return servicoRepository.findAll().stream().map(servicoMapper::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public ServicoResponseDTO atualizarServico(Long id, ServicoRequestDTO requestDTO) {
        Servico servicoExistente = servicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Serviço não encontrado com o ID: " + id));

        servicoExistente.setNome(requestDTO.getNome());
        servicoExistente.setDescricao(requestDTO.getDescricao());
        servicoExistente.setPreco(requestDTO.getPreco());
        servicoExistente.setDuracaoMinutos(requestDTO.getDuracaoMinutos());

        Servico servicoAtualizado = servicoRepository.save(servicoExistente);
        return servicoMapper.toDTO(servicoAtualizado);
    }

    @Transactional
    public void deletarServico(Long id) {
        if (!servicoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Serviço não encontrado com o ID: " + id);
        }
        try {
            servicoRepository.deleteById(id);
        } catch (Exception e) {
            throw new RegraNegocioException("Não é possível deletar o serviço pois ele está associado a agendamentos existentes.");
        }
    }
}