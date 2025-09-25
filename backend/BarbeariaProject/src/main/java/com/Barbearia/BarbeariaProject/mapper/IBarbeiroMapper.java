package com.Barbearia.BarbeariaProject.mapper;


import com.Barbearia.BarbeariaProject.dto.Barbeiro.BarbeiroRequestDTO;
import com.Barbearia.BarbeariaProject.dto.Barbeiro.BarbeiroResponseDTO;
import com.Barbearia.BarbeariaProject.entity.Barbeiro;
import com.Barbearia.BarbeariaProject.repository.IServicoRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.HashSet;

// 👇 PASSO 1: Mude de 'interface' para 'abstract class'
@Mapper(componentModel = "spring", uses = IServicoMapper.class)
public abstract class IBarbeiroMapper {

    // PASSO 2: Agora a injeção de dependência é VÁLIDA
    @Autowired
    protected IServicoRepository servicoRepository;

    // PASSO 3: O MapStruct usará a 'expression' para chamar o repositório
    @Mapping(target = "servicos", expression = "java(new HashSet(servicoRepository.findAllById(dto.getServicosIds())))")
    public abstract Barbeiro toEntity(BarbeiroRequestDTO dto);

    // Este método não precisa de lógica customizada, então o MapStruct o implementa sozinho
    public abstract BarbeiroResponseDTO toDTO(Barbeiro barbeiro);
}