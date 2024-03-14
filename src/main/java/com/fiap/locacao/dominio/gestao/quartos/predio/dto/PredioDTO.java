package com.fiap.locacao.dominio.gestao.quartos.predio.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fiap.locacao.dominio.gestao.quartos.localidade.dto.LocalidadeDTO;
import com.fiap.locacao.dominio.gestao.quartos.predio.entity.Predio;
import com.fiap.locacao.dominio.gestao.quartos.quarto.dto.QuartoDTO;
import com.fiap.locacao.dominio.gestao.quartos.quarto.entity.Quarto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PredioDTO {
    private Long id;
    private String nome;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<QuartoDTO> listaquartos;
    @NotNull(message = "Localidade não pode ser nulo")
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
