package com.fiap.locacao.dominio.quartos.quarto.entity;

import com.fiap.locacao.dominio.quartos.predio.entity.Predio;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
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
