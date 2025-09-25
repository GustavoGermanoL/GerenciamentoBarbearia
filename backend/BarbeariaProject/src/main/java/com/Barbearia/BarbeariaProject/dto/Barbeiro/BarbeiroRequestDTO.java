package com.Barbearia.BarbeariaProject.dto.Barbeiro;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BarbeiroRequestDTO {

    @NotBlank(message = "O nome do barbeiro não pode estar em branco")
    private String nome;

    @NotNull
    private boolean ativo;

    // Apenas os IDs dos serviços que este barbeiro pode realizar
    private Set<Long> servicosIds; 

}
