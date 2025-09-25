package com.Barbearia.BarbeariaProject.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Barbearia.BarbeariaProject.dto.Agendamento.AgendamentoRequestDTO;
import com.Barbearia.BarbeariaProject.dto.Agendamento.AgendamentoResponseDTO;
import com.Barbearia.BarbeariaProject.service.AgendamentoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/agendamentos")
@RequiredArgsConstructor
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    // Endpoint para criar um novo agendamento
    // POST http://localhost:8080/api/agendamentos
    @PostMapping
    public ResponseEntity<AgendamentoResponseDTO> criarAgendamento(@Valid @RequestBody AgendamentoRequestDTO requestDTO) {
        AgendamentoResponseDTO agendamentoCriado = agendamentoService.criarAgendamento(requestDTO);
        // Retorna o status 201 Created, que é a melhor prática para criação de recursos
        return ResponseEntity.status(HttpStatus.CREATED).body(agendamentoCriado);
    }
}
