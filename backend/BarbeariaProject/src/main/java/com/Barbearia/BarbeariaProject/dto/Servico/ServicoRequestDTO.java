package com.Barbearia.BarbeariaProject.dto.Servico;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ServicoRequestDTO {

    @NotBlank(message = "O nome do serviço não pode estar em branco")
    private String nome;

    private String descricao;

    @NotNull(message = "O preço não pode ser nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "O preço deve ser maior que zero")
    private BigDecimal preco;

    @NotNull(message = "A duração não pode ser nula")
    @Positive(message = "A duração deve ser um número positivo")
    private Integer duracaoMinutos;
}
