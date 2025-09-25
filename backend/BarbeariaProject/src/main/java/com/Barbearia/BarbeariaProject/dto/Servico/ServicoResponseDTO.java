package com.Barbearia.BarbeariaProject.dto.Servico;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ServicoResponseDTO {

    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Integer duracaoMinutos;
    
}
