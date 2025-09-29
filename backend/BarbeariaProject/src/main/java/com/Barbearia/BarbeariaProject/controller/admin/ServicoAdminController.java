package com.Barbearia.BarbeariaProject.controller.admin;

import com.Barbearia.BarbeariaProject.dto.Servico.ServicoRequestDTO;
import com.Barbearia.BarbeariaProject.dto.Servico.ServicoResponseDTO;
import com.Barbearia.BarbeariaProject.service.ServicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/servicos")
@RequiredArgsConstructor
public class ServicoAdminController {

    private final ServicoService servicoService;
    
    // Endpoint para ler todos os serviços (útil para o admin)
    @GetMapping
    public ResponseEntity<List<ServicoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(servicoService.listarTodos());
    }

    @PostMapping
    public ResponseEntity<ServicoResponseDTO> criarServico(@Valid @RequestBody ServicoRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicoService.criarServico(requestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicoResponseDTO> atualizarServico(@PathVariable Long id, @Valid @RequestBody ServicoRequestDTO requestDTO) {
        return ResponseEntity.ok(servicoService.atualizarServico(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarServico(@PathVariable Long id) {
        servicoService.deletarServico(id);
        return ResponseEntity.noContent().build();
    }
}