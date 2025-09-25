package com.Barbearia.BarbeariaProject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.Barbearia.BarbeariaProject.dto.Agendamento.AgendamentoRequestDTO;
import com.Barbearia.BarbeariaProject.dto.Agendamento.AgendamentoResponseDTO;
import com.Barbearia.BarbeariaProject.entity.Agendamento;
import com.Barbearia.BarbeariaProject.entity.Barbeiro;
import com.Barbearia.BarbeariaProject.entity.Servico;
import com.Barbearia.BarbeariaProject.repository.IBarbeiroRepository;
import com.Barbearia.BarbeariaProject.repository.IServicoRepository;

@Mapper(componentModel = "spring", uses = {IClienteMapper.class, IBarbeiroMapper.class, IServicoMapper.class})
public abstract class IAgendamentoMapper {

    @Autowired
    private IBarbeiroRepository barbeiroRepository;

    @Autowired
    private IServicoRepository servicoRepository;

    @Mapping(source = "cliente", target = "cliente")
    @Mapping(source = "barbeiroId", target = "barbeiro")
    @Mapping(source = "servicoId", target = "servico")
    @Mapping(target = "dataHoraFim", ignore = true) // Será calculado no service
    @Mapping(target = "status", ignore = true) // Será definido no service
    @Mapping(target = "id", ignore = true)
    public abstract Agendamento toEntity(AgendamentoRequestDTO dto);

    public abstract AgendamentoResponseDTO toDTO(Agendamento agendamento);

    // Métodos auxiliares para o MapStruct usar
    // Ele vê que precisa de um Barbeiro e tem um Long (barbeiroId), então usa este método.
    protected Barbeiro longToBarbeiro(Long barbeiroId) {
        if (barbeiroId == null) {
            return null;
        }
        return barbeiroRepository.findById(barbeiroId).orElse(null); // ou lançar exceção
    }

    // O mesmo para Serviço
    protected Servico longToServico(Long servicoId) {
        if (servicoId == null) {
            return null;
        }
        return servicoRepository.findById(servicoId).orElse(null); // ou lançar exceção
    }

}
