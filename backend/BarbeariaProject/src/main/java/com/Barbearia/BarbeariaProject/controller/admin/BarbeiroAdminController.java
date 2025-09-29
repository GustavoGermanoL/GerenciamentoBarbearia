package com.Barbearia.BarbeariaProject.controller.admin;

import com.Barbearia.BarbeariaProject.dto.Barbeiro.BarbeiroRequestDTO;
import com.Barbearia.BarbeariaProject.dto.Barbeiro.BarbeiroResponseDTO;
import com.Barbearia.BarbeariaProject.service.BarbeiroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/barbeiros")
@RequiredArgsConstructor
public class BarbeiroAdminController {

    private final BarbeiroService barbeiroService;

    @PostMapping
    public ResponseEntity<BarbeiroResponseDTO> criarBarbeiro(@Valid @RequestBody BarbeiroRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(barbeiroService.criarBarbeiro(requestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BarbeiroResponseDTO> atualizarBarbeiro(@PathVariable Long id, @Valid @RequestBody BarbeiroRequestDTO requestDTO) {
        return ResponseEntity.ok(barbeiroService.atualizarBarbeiro(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarBarbeiro(@PathVariable Long id) {
        barbeiroService.deletarBarbeiro(id);
        return ResponseEntity.noContent().build();
    }
}