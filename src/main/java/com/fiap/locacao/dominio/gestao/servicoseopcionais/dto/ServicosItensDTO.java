package com.fiap.locacao.dominio.gestao.servicoseopcionais.dto;

import java.math.BigDecimal;

import com.fiap.locacao.dominio.gestao.servicoseopcionais.enumerations.TipoServicosItens;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ServicosItensDTO {
	private Long id;
	private String descricao;
	private BigDecimal valor;
	@Enumerated(EnumType.STRING)
	private TipoServicosItens tipoServicosItens;
}
