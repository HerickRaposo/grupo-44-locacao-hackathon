package com.fiap.locacao.dominio.gestao.quarto.dto;

import com.fiap.locacao.dominio.gestao.predio.dto.PredioDTO;
import com.fiap.locacao.dominio.gestao.quarto.entity.Quarto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuartoDTO {
    private Long id;
    @NotNull(message = "Id do tipo de quarto não pode ser nulo")
    private Long tipo;
    private Boolean reservado = false;
    @NotNull(message = "Identificador do predio não pode ser nulo")
    private PredioDTO predio;

    public QuartoDTO(Quarto entity){
        this.id = entity.getId();
        this.tipo = entity.getTipo();
        this.predio = new PredioDTO(entity.getPredio());
    }
}


