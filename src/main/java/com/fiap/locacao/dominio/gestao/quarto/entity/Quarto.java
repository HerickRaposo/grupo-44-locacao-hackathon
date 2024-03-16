package com.fiap.locacao.dominio.gestao.quarto.entity;

import com.fiap.locacao.dominio.gestao.predio.entity.Predio;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Quarto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long tipo;
    private Boolean reservado;
    @ManyToOne
    @JoinColumn(name = "predio_id")
    private Predio predio;
    //private List<Servico> servicos;

}
