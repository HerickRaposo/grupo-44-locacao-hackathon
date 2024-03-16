package com.fiap.locacao.dominio.gestao.predio.controller;

import com.fiap.locacao.dominio.gestao.predio.dto.PredioDTO;
import com.fiap.locacao.dominio.gestao.predio.repositories.PredioService;
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
@RequestMapping(value = "/predio",produces = {"application/json"})
@Tag(name = "API QUARTO")
public class PredioController {
    @Autowired
    private PredioService predioService;

    @Operation(tags="Lista de Predios" ,summary = "Lista todos os predios cadastrados no banco de dados",method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A lot of Client has found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")})
    @GetMapping
    public ResponseEntity<Page<PredioDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "10") Integer linesPerPage,
            @RequestParam(value = "id_endereco", required = false) Long filtroIdEndereco
    ) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage);
        var predio = predioService.findAll(pageRequest,filtroIdEndereco);
        return ResponseEntity.ok(predio);
    }

    @Operation(summary = "Consulta predio passando id",method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A lot of Client has found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")})
    @GetMapping("/{id}")
    public ResponseEntity<PredioDTO> findById(@PathVariable Long id) {
        var predio = predioService.findById(id);
        return ResponseEntity.ok(predio);
    }


    @Operation(summary = "cadastro de predio",method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A lot of Client has found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")})
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody PredioDTO predioDTO) {
        List<String> violacoesToList = predioService.validate(predioDTO);
        if (!violacoesToList.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesToList);
        }
        var predioSaved = predioService.insert(predioDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand((predioSaved.getId())).toUri();
        return ResponseEntity.created(uri).body(predioSaved);
    }

    @Operation(summary = "Atualização de todos os campos de predio passando id",method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A lot of Client has found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")})
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody PredioDTO predioDTO, @PathVariable Long id) {
        List<String> violacoesToList = predioService.validate(predioDTO);
        if (!violacoesToList.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesToList);
        }
        var predioSaved = predioService.update(id, predioDTO);
        return  ResponseEntity.ok(predioSaved);
    }

    @Operation(summary = "Deleta predio passando id",method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A lot of Client has found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        predioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

