package com.fiap.locacao.dominio.cliente.dto;

import com.fiap.locacao.dominio.cliente.entities.Cliente;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientePatchDTO {


    private Long id;

    private String nome;

    private String sobrenome;

    private String dataNascimento;
    private String sexo;

    public ClientePatchDTO(Cliente entidade){
        this.id = entidade.getId();
        this.nome = entidade.getNome();
        this.sobrenome = entidade.getSobrenome();
        this.dataNascimento = entidade.getDataNascimento();
        this.sexo = entidade.getSexo();
    }
}
