package com.fiap.locacao.dominio.gestao.quartos.localidade.entities;

import com.fiap.locacao.dominio.gestao.endereco.entities.Endereco;
import com.fiap.locacao.dominio.gestao.quartos.predio.entity.Predio;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Localidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @OneToOne(mappedBy = "localidade", cascade = CascadeType.ALL)
    private Endereco endereco;
    @OneToMany(mappedBy = "localidade")
    private List<Predio> listaPredios;
    private List<Long> idsAmenidades;
}
