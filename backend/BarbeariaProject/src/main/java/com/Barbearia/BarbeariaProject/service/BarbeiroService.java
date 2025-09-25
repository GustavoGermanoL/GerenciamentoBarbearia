package com.Barbearia.BarbeariaProject.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Barbearia.BarbeariaProject.dto.Barbeiro.BarbeiroResponseDTO;
import com.Barbearia.BarbeariaProject.entity.Barbeiro;
import com.Barbearia.BarbeariaProject.exception.ResourceNotFoundException;
import com.Barbearia.BarbeariaProject.mapper.IBarbeiroMapper;
import com.Barbearia.BarbeariaProject.repository.IBarbeiroRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BarbeiroService {

    private final IBarbeiroRepository barbeiroRepository;
    private final IBarbeiroMapper barbeiroMapper;

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
}
