package com.Barbearia.BarbeariaProject.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Barbearia.BarbeariaProject.dto.Cliente.ClienteRequestDTO;
import com.Barbearia.BarbeariaProject.dto.Cliente.ClienteResponseDTO;
import com.Barbearia.BarbeariaProject.entity.Cliente;
import com.Barbearia.BarbeariaProject.exception.ResourceNotFoundException;
import com.Barbearia.BarbeariaProject.mapper.IClienteMapper;
import com.Barbearia.BarbeariaProject.repository.IClienteRepository;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final IClienteRepository clienteRepository;
    private final IClienteMapper clienteMapper;

    @Transactional(readOnly = true)
    public ClienteResponseDTO buscarPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o ID: " + id));
        return clienteMapper.toDTO(cliente);
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> listarTodos() {
        return clienteRepository.findAll().stream()
                .map(clienteMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Método útil para encontrar um cliente pelo telefone ou criar um novo se não existir.
    @Transactional
    public Cliente findOrCreateCliente(ClienteRequestDTO clienteDTO) {
        return clienteRepository.findByTelefone(clienteDTO.getTelefone())
                .orElseGet(() -> {
                    Cliente novoCliente = clienteMapper.toEntity(clienteDTO);
                    return clienteRepository.save(novoCliente);
                });
    }
}
