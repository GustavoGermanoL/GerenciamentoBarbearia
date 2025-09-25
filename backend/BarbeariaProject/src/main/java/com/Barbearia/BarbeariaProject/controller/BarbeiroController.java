package com.Barbearia.BarbeariaProject.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Barbearia.BarbeariaProject.dto.Barbeiro.BarbeiroResponseDTO;
import com.Barbearia.BarbeariaProject.service.BarbeiroService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/barbeiros")
@RequiredArgsConstructor

public class BarbeiroController {

    private final BarbeiroService barbeiroService;

    // Endpoint para listar todos os barbeiros
    // GET http://localhost:8080/api/barbeiros
    @GetMapping
    public ResponseEntity<List<BarbeiroResponseDTO>> listarTodos() {
        List<BarbeiroResponseDTO> barbeiros = barbeiroService.listarTodos();
        return ResponseEntity.ok(barbeiros);
    }

    // Endpoint para buscar um barbeiro por ID
    // GET http://localhost:8080/api/barbeiros/1
    @GetMapping("/{id}")
    public ResponseEntity<BarbeiroResponseDTO> buscarPorId(@PathVariable Long id) {
        BarbeiroResponseDTO barbeiro = barbeiroService.buscarPorId(id);
        return ResponseEntity.ok(barbeiro);
    }

}
