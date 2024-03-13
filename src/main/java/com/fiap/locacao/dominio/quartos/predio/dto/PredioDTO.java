package com.fiap.locacao.dominio.quartos.predio.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fiap.locacao.dominio.quartos.predio.entity.Predio;
import com.fiap.locacao.dominio.quartos.quarto.dto.QuartoDTO;
import com.fiap.locacao.dominio.quartos.quarto.entity.Quarto;
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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<QuartoDTO> listaquartos;
    private Long idEndereco;
//    @NotNull("Endereço não pode ser nulo")
//    private EnderecoDTO endereco;


    public PredioDTO(Predio entity) {
        this.id = entity.getId();
        this.idEndereco = entity.getIdEndereco();
        //this.endereco = new EnderecoDTO(entity.getEndereco());
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
