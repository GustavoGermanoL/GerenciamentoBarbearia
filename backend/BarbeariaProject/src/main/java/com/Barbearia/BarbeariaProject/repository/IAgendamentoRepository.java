package com.Barbearia.BarbeariaProject.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Barbearia.BarbeariaProject.entity.Agendamento;

@Repository
public interface IAgendamentoRepository extends JpaRepository<Agendamento, Long> {

    List<Agendamento> findByBarbeiroIdAndDataHoraInicioBetween(Long barbeiroId, LocalDateTime inicio, LocalDateTime fim);
}
