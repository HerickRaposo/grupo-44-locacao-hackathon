package com.fiap.locacao.adapters.dto;

import com.fiap.locacao.dominio.gestao.endereco.entities.Endereco;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Setter
public class EnderecoResultViaCepDTO {
	private String cep;
	private String logradouro;
	private String complemento;
	private String bairro;
	private String localidade;
	private String uf;
	private Integer ibge;
	private Integer gia;
	private Integer ddd;
	private Integer siafi;
	
	public Endereco getEndereco(CepDTO cepDTO) {
		Endereco endereco = new Endereco();
		endereco.setBairro(bairro);
		endereco.setCidade(localidade);
		endereco.setEstado(uf);
		endereco.setNumero(ibge);
		endereco.setRua(logradouro);
		endereco.setCep(cepDTO.getCep());
		endereco.setClientes(Arrays.asList(cepDTO.getCliente()));
		return endereco;
	}
	
	@Override
	public String toString() {
		return "EnderecoViaCep [cep=" + cep + ", logradouro=" + logradouro + ", complemento=" + complemento
				+ ", bairro=" + bairro + ", localidade=" + localidade + ", uf=" + uf + ", ibge=" + ibge + ", gia=" + gia
				+ ", ddd=" + ddd + ", siafi=" + siafi + "]";
	}
}
