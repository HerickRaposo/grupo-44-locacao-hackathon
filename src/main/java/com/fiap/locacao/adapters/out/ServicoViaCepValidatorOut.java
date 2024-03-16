package com.fiap.locacao.adapters.out;

import com.fiap.locacao.adapters.dto.CepDTO;
import com.fiap.locacao.adapters.dto.EnderecoResultViaCepDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



@Service
public class ServicoViaCepValidatorOut {

	@Value("${api-comunicationcep.host}")
	private String apiHostce;

	@Value("${api-comunicationcep.host.json}")
	private String json;
	private final RestTemplate restTemplate=new RestTemplate();

	public EnderecoResultViaCepDTO validarEndereco(CepDTO cepDTO) {
		return this.restTemplate.getForEntity(
				apiHostce+cepDTO.getCep()+json,
				EnderecoResultViaCepDTO.class).getBody();
     }

	public EnderecoResultViaCepDTO populaCep(String cepDTO) {
		return this.restTemplate.getForEntity(
				apiHostce+cepDTO+json,
				EnderecoResultViaCepDTO.class).getBody();
	}
}
