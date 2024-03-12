package com.fiap.locacao.dominio.quartos.predio.entity;

import com.fiap.locacao.dominio.quartos.quarto.entity.Quarto;
import jakarta.persistence.*;
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
    @OneToMany(mappedBy = "predio")
    private List<Quarto> listaquartos;
    //private Endereco endereco;
}
