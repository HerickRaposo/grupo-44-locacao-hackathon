package com.fiap.locacao.dominio.gestao.quartos.predio.entity;

import com.fiap.locacao.dominio.gestao.endereco.entities.Endereco;
import com.fiap.locacao.dominio.gestao.quartos.localidade.entities.Localidade;
import com.fiap.locacao.dominio.gestao.quartos.quarto.entity.Quarto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Predio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @OneToMany(mappedBy = "predio")
    private List<Quarto> listaquartos;
    @ManyToOne
    @JoinColumn(name = "localidade_id") // Nome da coluna que faz referência ao Endereco
    private Localidade localidade;
}
