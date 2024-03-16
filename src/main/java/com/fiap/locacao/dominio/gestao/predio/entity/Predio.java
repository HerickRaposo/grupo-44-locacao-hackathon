package com.fiap.locacao.dominio.gestao.predio.entity;

import com.fiap.locacao.dominio.gestao.localidade.entities.Localidade;
import com.fiap.locacao.dominio.gestao.quarto.entity.Quarto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
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
