package com.Barbearia.BarbeariaProject.service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Barbearia.BarbeariaProject.dto.Barbeiro.BarbeiroRequestDTO;
import com.Barbearia.BarbeariaProject.dto.Barbeiro.BarbeiroResponseDTO;
import com.Barbearia.BarbeariaProject.entity.Barbeiro;
import com.Barbearia.BarbeariaProject.exception.ResourceNotFoundException;
import com.Barbearia.BarbeariaProject.mapper.IBarbeiroMapper;
import com.Barbearia.BarbeariaProject.repository.IBarbeiroRepository;
import com.Barbearia.BarbeariaProject.repository.IServicoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BarbeiroService {

    private final IBarbeiroRepository barbeiroRepository;
    private final IBarbeiroMapper barbeiroMapper;
    private final IServicoRepository servicoRepository;

    @Transactional(readOnly = true)
    public List<BarbeiroResponseDTO> listarTodos() {
        return barbeiroRepository.findAll().stream()
                .map(barbeiroMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BarbeiroResponseDTO buscarPorId(Long id) {
        Barbeiro barbeiro = barbeiroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Barbeiro não encontrado com o ID: " + id));
        return barbeiroMapper.toDTO(barbeiro);
    }

    @Transactional
public BarbeiroResponseDTO criarBarbeiro(BarbeiroRequestDTO requestDTO) {
    Barbeiro barbeiro = barbeiroMapper.toEntity(requestDTO);
    Barbeiro barbeiroSalvo = barbeiroRepository.save(barbeiro);
    return barbeiroMapper.toDTO(barbeiroSalvo);
}

@Transactional
public BarbeiroResponseDTO atualizarBarbeiro(Long id, BarbeiroRequestDTO requestDTO) {
    Barbeiro barbeiroExistente = barbeiroRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Barbeiro não encontrado com o ID: " + id));

    // O MapStruct é ótimo para criar, mas para atualizar, o controle manual é mais seguro
    barbeiroExistente.setNome(requestDTO.getNome());
    barbeiroExistente.setAtivo(requestDTO.isAtivo());

    // Atualiza os serviços associados
    if (requestDTO.getServicosIds() != null) {
        barbeiroExistente.getServicos().clear();
        barbeiroExistente.getServicos().addAll(new HashSet<>(servicoRepository.findAllById(requestDTO.getServicosIds())));
    }

    Barbeiro barbeiroAtualizado = barbeiroRepository.save(barbeiroExistente);
    return barbeiroMapper.toDTO(barbeiroAtualizado);
}

    @Transactional
    public void deletarBarbeiro(Long id) {
        if (!barbeiroRepository.existsById(id)) {
            throw new ResourceNotFoundException("Barbeiro não encontrado com o ID: " + id);
        }
        barbeiroRepository.deleteById(id);
    }
}
