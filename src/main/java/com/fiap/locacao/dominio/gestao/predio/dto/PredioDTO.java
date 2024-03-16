package com.fiap.locacao.dominio.gestao.predio.dto;

import com.fasterxml.jackson.annotation.*;
import com.fiap.locacao.dominio.gestao.localidade.dto.LocalidadeDTO;
import com.fiap.locacao.dominio.gestao.predio.entity.Predio;
import com.fiap.locacao.dominio.gestao.quarto.dto.QuartoDTO;
import com.fiap.locacao.dominio.gestao.quarto.entity.Quarto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PredioDTO {
    private Long id;
    private String nome;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<QuartoDTO> listaquartos;
    @NotNull(message = "Localidade não pode ser nulo")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalidadeDTO localidade;


    public PredioDTO(Predio entity) {
        this.id = entity.getId();
        this.localidade = new LocalidadeDTO(entity.getLocalidade());
        if (entity.getNome() != null){
            this.nome = entity.getNome();
        }
        if (entity.getListaquartos() != null) {
            if (listaquartos == null) {
                listaquartos = new ArrayList<>();
            }
            for (Quarto quarto : entity.getListaquartos()) {
                listaquartos.add(new QuartoDTO(quarto));
            }
        }
    }
}
