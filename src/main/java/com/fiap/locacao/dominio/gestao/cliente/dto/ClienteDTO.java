package com.fiap.locacao.dominio.gestao.cliente.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fiap.locacao.dominio.gestao.cliente.entities.Cliente;
import com.fiap.locacao.dominio.gestao.endereco.dto.EnderecoDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ClienteDTO {
    private Long id;
    @NotNull(message = "País de origem não pode deve ser nulo")
    @NotBlank(message = "Você deve preenche um País de origem, não pode ser vazio")
    private String paisOrigem;
    @CPF
    private String cpf;
    @NotNull(message = "Passaporte não pode deve ser nulo")
    @NotBlank(message = "Você deve preenche um Passaporte, não pode ser vazio")
    private String passaPorte;
    @NotNull(message = "Nome não pode deve ser nulo")
    @NotBlank(message = "Você deve preenche um nome, não pode ser vazio")
    private String nomeCompleto;
    @NotNull(message = "Campo Data não pode deve ser nulo")
    @NotBlank(message = "Você deve preenche uma Data, não pode ser vazio")
    private LocalDate dataNascimento;
    @NotNull(message = "Telefone não pode deve ser nulo")
    @NotBlank(message = "Você deve preenche um Telefone, não pode ser vazio")
    private String telefone;
    @NotNull(message = "Email não pode deve ser nulo")
    @NotBlank(message = "Você deve preenche um email, não pode ser vazio")
    private String email;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<EnderecoDTO> enderecos = new ArrayList<>();


    public ClienteDTO(Cliente entidade) {
        this.id = entidade.getId();
        this.nomeCompleto = entidade.getNomeCompleto();
        this.dataNascimento = entidade.getDataNascimento();
        this.cpf = entidade.getCpf();
        this.email = entidade.getEmail();
        this.paisOrigem = entidade.getPaisOrigem();
        this.passaPorte = entidade.getPassaPorte();
        this.telefone = entidade.getTelefone();
    }

    public ClienteDTO(Cliente cliente, List<EnderecoDTO> enderecosDTO) {
        this(cliente);


        if (enderecosDTO!=null) {
            this.setEnderecos(enderecosDTO);
        }

    }
}
