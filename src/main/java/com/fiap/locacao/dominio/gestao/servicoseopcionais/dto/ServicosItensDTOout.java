package com.fiap.locacao.dominio.gestao.servicoseopcionais.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fiap.locacao.dominio.gestao.servicoseopcionais.entity.ServicosItens;
import com.fiap.locacao.dominio.gestao.servicoseopcionais.enumerations.TipoServicosItens;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ServicosItensDTOout {
	private Long id;
	private String descricao;
	private BigDecimal valor;
	@Enumerated(EnumType.STRING)
	private TipoServicosItens tipoServicosItens;
	
	@JsonIgnore
	public ServicosItensDTOout getDTO(ServicosItens servicosItens) {
		this.setId(servicosItens.getId());
		this.setDescricao(servicosItens.getDescricao());
		this.setTipoServicosItens(servicosItens.getTipoServicosItens());
		this.setValor(servicosItens.getValor());
		return this;
	}
}
