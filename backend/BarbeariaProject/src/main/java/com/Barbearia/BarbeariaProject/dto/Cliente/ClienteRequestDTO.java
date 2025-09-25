package com.Barbearia.BarbeariaProject.dto.Cliente;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClienteRequestDTO {

    @NotBlank(message = "O nome não pode estar em branco")
    private String nome;

    @NotBlank(message = "O telefone não pode estar em branco")
    private String telefone;

}
