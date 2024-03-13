package com.fiap.locacao.dominio.gestao.endereco.entities;


import com.fiap.locacao.dominio.gestao.cliente.entities.Cliente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_endereco")
public class Endereco {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String cep;
	private String rua;
	private Integer numero; 
	private String bairro;
	private String cidade;
	private String estado;
	@ManyToMany
	@JoinTable(name = "tb_endereco_cliente",
			joinColumns = @JoinColumn(name = "endereco_id"),
			inverseJoinColumns = @JoinColumn(name = "cliente_id"))
	private List<Cliente> clientes= new ArrayList<>();



}