package com.fiap.locacao.dominio.gestao.cliente.dto;

import com.fiap.locacao.dominio.gestao.cliente.entities.Cliente;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ClientePatchDTO {


    private Long id;

    private String paisOrigem;
    private String cpf;
    private String passaPorte;
    private String nomeCompleto;
    private LocalDate dataNascimento;
    private String telefone;
    private String email;

    public ClientePatchDTO(Cliente entidade) {
        this.paisOrigem = entidade.getPaisOrigem();
        this.cpf = entidade.getCpf();
        this.passaPorte = entidade.getPassaPorte();
        this.nomeCompleto = entidade.getNomeCompleto();
        this.dataNascimento = entidade.getDataNascimento();
        this.telefone = entidade.getTelefone();
        this.email = entidade.getEmail();
    }


}
