package com.fiap.locacao.dominio.gestao.localidade.dto;

import com.fasterxml.jackson.annotation.*;
import com.fiap.locacao.dominio.gestao.endereco.dto.EnderecoDTO;
import com.fiap.locacao.dominio.gestao.localidade.enumerations.Amenidades;
import com.fiap.locacao.dominio.gestao.localidade.entities.Localidade;
import com.fiap.locacao.dominio.gestao.predio.dto.PredioDTO;
import com.fiap.locacao.dominio.gestao.predio.entity.Predio;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocalidadeDTO {
    private Long id;

    @NotNull(message = "Nome da localidade não pode ser nulo")
    private String nome;

    @NotNull(message = "Endereço não pode ser nulo")
    private EnderecoDTO enderecoDTO;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<PredioDTO> listaPredios;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Long> idsAmenidades;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Amenidades> amenidades;
//    private List<String> listaDescricaoAmenidades;

    public LocalidadeDTO(Localidade entity){
        this.id = entity.getId();
        this.nome = entity.getNome();

        if (entity.getListaPredios() != null && !entity.getListaPredios().isEmpty()){
            if (this.listaPredios == null){
                this.listaPredios = new ArrayList<>();
            }
            for (Predio predio: entity.getListaPredios()){
                this.listaPredios.add(new PredioDTO(predio));
            }
        }
        if (entity.getEndereco() != null){
            this.enderecoDTO = new EnderecoDTO(entity.getEndereco());

        }

        if (entity.getIdsAmenidades() != null && !entity.getIdsAmenidades().isEmpty()) {
            this.idsAmenidades = entity.getIdsAmenidades(); // Atribui a lista de IDs diretamente

            this.amenidades = entity.getIdsAmenidades().stream()
                    .map(Amenidades::buscarNome)
                    .collect(Collectors.toList());
        }
    }
}
