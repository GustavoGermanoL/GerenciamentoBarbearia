package com.Barbearia.BarbeariaProject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.Barbearia.BarbeariaProject.dto.HorarioTrabalho.HorarioTrabalhoRequestDTO;
import com.Barbearia.BarbeariaProject.dto.HorarioTrabalho.HorarioTrabalhoResponseDTO;
import com.Barbearia.BarbeariaProject.entity.Barbeiro;
import com.Barbearia.BarbeariaProject.entity.HorarioTrabalho;
import com.Barbearia.BarbeariaProject.repository.IBarbeiroRepository;

@Mapper(componentModel = "spring")
public abstract class IHorarioTrabalhoMapper {
    @Autowired
    private IBarbeiroRepository barbeiroRepository;

    // Mapeamento do DTO de requisição para a Entidade
    @Mapping(source = "barbeiroId", target = "barbeiro")
    @Mapping(target = "id", ignore = true) // Ignora o ID na criação
    @Mapping(target = "ativo", ignore = true) // 'ativo' terá um valor padrão na entidade
    public abstract HorarioTrabalho toEntity(HorarioTrabalhoRequestDTO dto);

    @Mapping(source = "barbeiro.id", target = "barbeiroId")
    public abstract HorarioTrabalhoResponseDTO toDTO(HorarioTrabalho horarioTrabalho);

    // Método auxiliar que o MapStruct usará para converter o ID em uma entidade Barbeiro
    protected Barbeiro longToBarbeiro(Long barbeiroId) {
        if (barbeiroId == null) {
            // Você pode querer lançar uma exceção aqui se um barbeiro for sempre obrigatório
            return null;
        }
        // Busca o barbeiro no banco ou retorna nulo (ou lança exceção se não encontrado)
        return barbeiroRepository.findById(barbeiroId).orElse(null);
    }

}
