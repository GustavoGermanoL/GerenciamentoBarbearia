package com.Barbearia.BarbeariaProject.mapper;

import org.mapstruct.Mapper;

import com.Barbearia.BarbeariaProject.dto.Cliente.ClienteRequestDTO;
import com.Barbearia.BarbeariaProject.dto.Cliente.ClienteResponseDTO;
import com.Barbearia.BarbeariaProject.entity.Cliente;

@Mapper(componentModel = "spring")
public interface IClienteMapper {

    Cliente toEntity(ClienteRequestDTO dto);
    ClienteResponseDTO toDTO(Cliente cliente);

}
