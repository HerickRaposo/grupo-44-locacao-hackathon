package com.fiap.locacao.dominio.gestao.servicoseopcionais.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.locacao.dominio.gestao.endereco.dto.RestDataReturnDTO;
import com.fiap.locacao.dominio.gestao.servicoseopcionais.dto.ServicosItensDTOin;
import com.fiap.locacao.dominio.gestao.servicoseopcionais.dto.ServicosItensDTOout;
import com.fiap.locacao.dominio.gestao.servicoseopcionais.enumerations.TipoServicosItens;
import com.fiap.locacao.dominio.gestao.servicoseopcionais.service.ServicosItensService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/servicos-itens",produces = {"application/json"})
@Tag(name = "API Gestão de Serviços e Opcionais")
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
	
	@GetMapping("/buscarPorId")
	public ResponseEntity<ServicosItensDTOout> getID(@RequestParam Long id) {
		 ServicosItensDTOout  servicosItens = this.servicosItensService.buscarPorId(id);
		return ResponseEntity.status(HttpStatus.FOUND).body(servicosItens); 
	}
	
	@GetMapping("/buscar-todos")
	public RestDataReturnDTO getAll(@RequestParam(value = "pagina", defaultValue = "0") Integer pagina, @RequestParam(value = "tamanho", defaultValue = "10") Integer tamanho) {
		PageRequest pageRequest = PageRequest.of(pagina,tamanho);
		return this.servicosItensService.buscarTodos(pageRequest);
	}
	
	@GetMapping("/buscarPorTipo")
	public RestDataReturnDTO buscarPorTipo(@RequestParam(value = "pagina", defaultValue = "0") Integer pagina, @RequestParam(value = "tamanho", defaultValue = "10") Integer tamanho,@RequestParam TipoServicosItens tipoServicosItens) {
		PageRequest pageRequest = PageRequest.of(pagina,tamanho);
		return this.servicosItensService.buscarPorTipo(pageRequest,tipoServicosItens);
	}

}
