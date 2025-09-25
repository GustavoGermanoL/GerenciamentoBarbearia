package com.Barbearia.BarbeariaProject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Barbearia.BarbeariaProject.entity.HorarioTrabalho;

@Repository
public interface IHorarioTrabalhoRepository extends JpaRepository<HorarioTrabalho, Long>{

    Optional<HorarioTrabalho> findByBarbeiroIdAndDiaSemana(Long barbeiroId, Integer diaSemana);

}
