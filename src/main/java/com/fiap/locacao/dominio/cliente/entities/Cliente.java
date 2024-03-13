package com.fiap.locacao.dominio.cliente.entities;


import com.fiap.locacao.dominio.endereco.entities.Endereco;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_cliente")
public class Cliente {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String paisOrigem;
    private String cpf;
    private String passaPorte;
    private String nomeCompleto;
    private LocalDate dataNascimento;
    private String telefone;
    private String email;

    @CreationTimestamp
    private Instant dataDeCriacao;

    @ManyToMany(mappedBy = "clientes")
    private List<Endereco> enderecos = new ArrayList<Endereco>();



}
