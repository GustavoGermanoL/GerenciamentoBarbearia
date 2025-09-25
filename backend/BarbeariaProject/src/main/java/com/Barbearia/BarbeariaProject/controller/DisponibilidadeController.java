package com.Barbearia.BarbeariaProject.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Barbearia.BarbeariaProject.service.DisponibilidadeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/disponibilidade")
@RequiredArgsConstructor
public class DisponibilidadeController {

    private final DisponibilidadeService disponibilidadeService;

    // Endpoint para calcular e retornar os horários disponíveis
    // GET http://localhost:8080/api/disponibilidade?barbeiroId=1&servicoId=1&data=2025-09-26
    @GetMapping
    public ResponseEntity<List<LocalTime>> getHorariosDisponiveis(
            @RequestParam Long barbeiroId,
            @RequestParam Long servicoId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        
        List<LocalTime> horarios = disponibilidadeService.calcularHorariosDisponiveis(barbeiroId, servicoId, data);
        return ResponseEntity.ok(horarios);
    }
}
