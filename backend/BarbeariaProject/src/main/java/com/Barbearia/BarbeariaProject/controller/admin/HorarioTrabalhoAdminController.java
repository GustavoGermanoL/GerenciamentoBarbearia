package com.Barbearia.BarbeariaProject.controller.admin;

import com.Barbearia.BarbeariaProject.dto.HorarioTrabalho.HorarioTrabalhoRequestDTO;
import com.Barbearia.BarbeariaProject.dto.HorarioTrabalho.HorarioTrabalhoResponseDTO;
import com.Barbearia.BarbeariaProject.service.HorarioTrabalhoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/horarios-trabalho")
@RequiredArgsConstructor
public class HorarioTrabalhoAdminController {

    private final HorarioTrabalhoService horarioTrabalhoService;

    /**
     * Endpoint para definir ou atualizar o horário de trabalho de um barbeiro para um dia específico.
     * O Service decidirá se cria um novo registro ou atualiza um existente.
     */
    @PostMapping
    public ResponseEntity<HorarioTrabalhoResponseDTO> definirOuAtualizarHorario(@Valid @RequestBody HorarioTrabalhoRequestDTO requestDTO) {
        HorarioTrabalhoResponseDTO responseDTO = horarioTrabalhoService.definirOuAtualizarHorario(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    /**
     * Endpoint para listar todos os horários de trabalho de um barbeiro específico.
     */
    @GetMapping("/barbeiro/{barbeiroId}")
    public ResponseEntity<List<HorarioTrabalhoResponseDTO>> listarHorariosPorBarbeiro(@PathVariable Long barbeiroId) {
        List<HorarioTrabalhoResponseDTO> horarios = horarioTrabalhoService.listarHorariosPorBarbeiro(barbeiroId);
        return ResponseEntity.ok(horarios);
    }

    /**
     * Endpoint para remover um registro de horário de trabalho específico (ex: o barbeiro não trabalha mais às segundas).
     */
    @DeleteMapping("/{horarioId}")
    public ResponseEntity<Void> removerHorario(@PathVariable Long horarioId) {
        horarioTrabalhoService.removerHorario(horarioId);
        return ResponseEntity.noContent().build();
    }
}