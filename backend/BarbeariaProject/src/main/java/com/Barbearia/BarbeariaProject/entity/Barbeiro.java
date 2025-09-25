package com.Barbearia.BarbeariaProject.entity;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "tbl_barbeiros")
public class Barbeiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "ativo", nullable = false)
    private boolean ativo = true;

    @ManyToMany
    @JoinTable(
      name = "tbl_barbeiro_servicos", 
      joinColumns = @JoinColumn(name = "barbeiro_id"), 
      inverseJoinColumns = @JoinColumn(name = "servico_id"))
    private Set<Servico> servicos;

}
