package com.fiap.locacao.dominio.gestao.localidade.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiap.locacao.dominio.gestao.endereco.dto.EnderecoDTO;
import com.fiap.locacao.dominio.gestao.localidade.enumerations.Amenidades;
import com.fiap.locacao.dominio.gestao.localidade.entities.Localidade;
import com.fiap.locacao.dominio.gestao.predio.dto.PredioDTO;
import com.fiap.locacao.dominio.gestao.predio.entity.Predio;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocalidadeDTO {
    private Long id;

    @NotNull(message = "Nome da localidade não pode ser nulo")
    private String nome;

    @NotNull(message = "Endereço não pode ser nulo")
    private EnderecoDTO enderecoDTO;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<PredioDTO> listaPredios;

//    @NotNull(message = "Ids amenidades não pode ser nulo")
//    @NotEmpty(message = "Ids amenidades não pode ser vazio")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Amenidades> idsAmenidades;

//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
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

//        if (entity.getIdsAmenidades() != null && !listaPredios.isEmpty()){
//            if (this.listaDescricaoAmenidades == null){
//                for (Long idAmenidade: entity.getIdsAmenidades()){
//                    String descricaoAmenidade = Amenidades.buscarDescricaoAmenidade(idAmenidade);
//                    if (descricaoAmenidade != null){
//                        this.listaDescricaoAmenidades.add(descricaoAmenidade);
//                    }
//                }
//            }
//        }
    }
}
