package com.Barbearia.BarbeariaProject.mapper;

import org.mapstruct.Mapper;

import com.Barbearia.BarbeariaProject.dto.Servico.ServicoRequestDTO;
import com.Barbearia.BarbeariaProject.dto.Servico.ServicoResponseDTO;
import com.Barbearia.BarbeariaProject.entity.Servico;

@Mapper(componentModel = "spring")
public interface IServicoMapper {

    Servico toEntity(ServicoRequestDTO dto);
    ServicoResponseDTO toDTO(Servico servico);


}
