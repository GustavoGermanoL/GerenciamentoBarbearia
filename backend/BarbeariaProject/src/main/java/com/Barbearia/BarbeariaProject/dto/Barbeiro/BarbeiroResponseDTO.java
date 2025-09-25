package com.Barbearia.BarbeariaProject.dto.Barbeiro;

import java.util.Set;

import com.Barbearia.BarbeariaProject.dto.Servico.ServicoResponseDTO;

import lombok.Data;

@Data
public class BarbeiroResponseDTO {

     private Long id;
    private String nome;
    private boolean ativo;
    private Set<ServicoResponseDTO> servicos; // Usando o DTO de serviço para aninhamento

}
