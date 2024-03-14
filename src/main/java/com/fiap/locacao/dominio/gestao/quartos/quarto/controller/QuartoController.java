package com.fiap.locacao.dominio.gestao.quartos.quarto.controller;

import com.fiap.locacao.dominio.gestao.quartos.quarto.dto.QuartoDTO;
import com.fiap.locacao.dominio.gestao.quartos.quarto.service.QuartoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/quarto",produces = {"application/json"})
@Tag(name = "API QUARTO")
public class QuartoController {
    @Autowired
    private QuartoService quartoService;

    @Operation(tags="Lista de Quartos" ,summary = "Lista todos os quartos cadastrados no banco de dados",method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A lot of Client has found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")})
    @GetMapping
    public ResponseEntity<Page<QuartoDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "10") Integer linesPerPage,
            @RequestParam(value = "tipo_quarto", required = false) String tipoQuarto,
            @RequestParam(value = "reservado", required = false) Boolean reservado,
            @RequestParam(value = "id_predio", required = false) Long idPredio
    ) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage);
        var filtro = quartoService.retornaFiltroFormatado(tipoQuarto,reservado,idPredio);
        var quartos = quartoService.findAll(pageRequest,filtro);
        return ResponseEntity.ok(quartos);
    }

    @Operation(summary = "Consulta quarto passando id",method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A lot of Client has found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")})
    @GetMapping("/{id}")
    public ResponseEntity<QuartoDTO> findById(@PathVariable Long id) {
        var quarto = quartoService.findById(id);
        return ResponseEntity.ok(quarto);
    }

    @Operation(summary = "Cadastro de quartos",method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A lot of Client has found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")})
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody QuartoDTO quartoDTO) {
        List<String> violacoesToList = quartoService.validate(quartoDTO);
        if (!violacoesToList.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesToList);
        }
        var quartoSaved = quartoService.insert(quartoDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand((quartoSaved.getId())).toUri();
        return ResponseEntity.created(uri).body(quartoSaved);
    }

    @Operation(summary = "Atualiza todos campos do quarto passando o id",method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A lot of Client has found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")})
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody QuartoDTO quartoDTO, @PathVariable Long id) {
        List<String> violacoesToList = quartoService.validate(quartoDTO);
        if (!violacoesToList.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesToList);
        }
        var quartpUpdated = quartoService.update(id, quartoDTO);
        return  ResponseEntity.ok(quartpUpdated);
    }

    @Operation(summary = "Deleta quarto pelo id",method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A lot of Client has found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        quartoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
