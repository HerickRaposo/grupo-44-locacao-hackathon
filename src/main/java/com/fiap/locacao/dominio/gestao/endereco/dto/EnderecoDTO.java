package com.fiap.locacao.dominio.gestao.endereco.dto;


import com.fiap.locacao.dominio.gestao.endereco.entities.Endereco;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;


@Getter
@Setter
public class EnderecoDTO {
	
	private Long id;
	@NotBlank(message = "Ocampo Rua é obrigatório seu preenchimento.")
	private String rua;
	@Range(min = 1,message = "O Número da rua deve ser um número maior que 0")
	private Integer numero; 
	@NotBlank(message = "Ocampo Bairro é obrigatório seu preenchimento.")
	private String bairro;
	@NotBlank(message = "Ocampo Cidade é obrigatório seu preenchimento.")
	private String cidade;
	@NotBlank(message = "Ocampo Estado é obrigatório seu preenchimento.")
	private String estado;
	private String cep;



	public EnderecoDTO() {
		 
	}
	
	public EnderecoDTO(Endereco endereco) {
		this.id     = endereco.getId();
		this.rua    = endereco.getRua();
		this.numero = endereco.getNumero();
		this.bairro = endereco.getBairro();
		this.cidade = endereco.getCidade();
		this.estado = endereco.getEstado();
		this.cep    = endereco.getCep();
		//this.pessoas= endereco.getPessoas();
	}
	
	public Endereco getEndereco(EnderecoDTO enderecoDTO) {
		Endereco endereco=new Endereco();
		//endereco.setId(enderecoDTO.getId());
		endereco.setBairro(enderecoDTO.getBairro());
		endereco.setCidade(enderecoDTO.getCidade());
		endereco.setEstado(enderecoDTO.getEstado());
		endereco.setNumero(enderecoDTO.getNumero());
		endereco.setRua(enderecoDTO.getRua());
		return endereco;
	}


}
