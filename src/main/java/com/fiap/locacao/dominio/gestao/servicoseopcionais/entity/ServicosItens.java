package com.fiap.locacao.dominio.gestao.servicoseopcionais.entity;

import java.math.BigDecimal;

import com.fiap.locacao.dominio.gestao.servicoseopcionais.enumerations.TipoServicosItens;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_servicos_itens")
public class ServicosItens {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
    private Long id;
	@Column(name = "DESCRICAO",length = 200)
	private String descricao;
	@Column(name = "VALOR")
	private BigDecimal valor;
	@Column(name = "TIPO_SERVICOS_ITENS")
	@Enumerated(EnumType.STRING)
	private TipoServicosItens tipoServicosItens;
}
