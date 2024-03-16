package com.fiap.locacao.dominio.gestao.servicoseopcionais.enumerations;

public enum TipoServicosItens {
	SERVICOS("Serviços"),ITENS("Itens");
	
	private String descricao;

	TipoServicosItens(String descricao) {
		this.descricao=descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
