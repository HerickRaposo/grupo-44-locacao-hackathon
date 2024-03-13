package com.fiap.locacao.adapters.dto;

import com.fiap.locacao.dominio.gestao.cliente.entities.Cliente;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CepDTO {
	@NotBlank(message = "O campo CEP é obrigatório seu preenchimento.")
	private String cep;
	private Cliente cliente;
}
