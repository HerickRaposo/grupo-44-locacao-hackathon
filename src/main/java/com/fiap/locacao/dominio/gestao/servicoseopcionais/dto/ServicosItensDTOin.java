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
public class ServicosItensDTOin {
	private String descricao;
	private BigDecimal valor;
	@Enumerated(EnumType.STRING)
	private TipoServicosItens tipoServicosItens;
	
	@JsonIgnore
	public ServicosItens getEntity() {
		ServicosItens servicosItens = new ServicosItens();
		servicosItens.setDescricao(this.descricao);
		servicosItens.setTipoServicosItens(this.tipoServicosItens);
		servicosItens.setValor(this.valor);
		return servicosItens;
	}
}
