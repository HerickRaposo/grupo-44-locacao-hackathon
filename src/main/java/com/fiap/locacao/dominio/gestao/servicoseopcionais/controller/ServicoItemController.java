package com.fiap.locacao.dominio.gestao.servicoseopcionais.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.locacao.dominio.gestao.servicoseopcionais.dto.ServicosItensDTOin;
import com.fiap.locacao.dominio.gestao.servicoseopcionais.dto.ServicosItensDTOout;
import com.fiap.locacao.dominio.gestao.servicoseopcionais.service.ServicosItensService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/servicos-itens",produces = {"application/json"})
@Tag(name = "API QUARTO")
public class ServicoItemController {
	
	private @Autowired ServicosItensService servicosItensService;
	
	@PostMapping("/salvar")
	public ResponseEntity<ServicosItensDTOout> salvar(@RequestBody ServicosItensDTOin servicosItensDTO) {
		ServicosItensDTOout servicosItens = this.servicosItensService.salvar(servicosItensDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(servicosItens); 
	}
	
	@PutMapping("/atualizar/{id}")
	public ResponseEntity<ServicosItensDTOout> atualizar(@RequestBody ServicosItensDTOin servicosItensDTO,@PathVariable Long id) {
		ServicosItensDTOout servicosItens = this.servicosItensService.atualizar(servicosItensDTO,id);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(servicosItens); 
	}
	
	@DeleteMapping("/apagar/{id}")
	public ResponseEntity<String> apagar(@PathVariable Long id) {
		 String mensagem = this.servicosItensService.apagar(id);
		 return ResponseEntity.status(HttpStatus.OK).body(mensagem);
	}

}
