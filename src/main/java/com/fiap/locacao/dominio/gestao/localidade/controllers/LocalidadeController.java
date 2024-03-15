package com.fiap.locacao.dominio.gestao.localidade.controllers;


import com.fiap.locacao.dominio.gestao.localidade.dto.LocalidadeDTO;
import com.fiap.locacao.dominio.gestao.localidade.services.LocalidadeService;
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
@RequestMapping(value = "/localidades",produces = {"application/json"})
@Tag(name = "API LOCALIDADES")
public class LocalidadeController {

    @Autowired
    private LocalidadeService localidadeService;

    @Operation(tags="Lista de Predios" ,summary = "Lista todos As localidade cadastrados no banco de dados",method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A lot of location has found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "location not found"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")})
    @GetMapping
    public ResponseEntity<Page<LocalidadeDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "10") Integer linesPerPage,
            @RequestParam(value = "id_endereco", required = false) Long filtroIdEndereco
    ) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage);
        var localidade = localidadeService.findAll(pageRequest,filtroIdEndereco);
        return ResponseEntity.ok(localidade);
    }

    @Operation(summary = "Consulta localidade passando id",method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A lot of location has found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "location not found"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")})
    @GetMapping("/{id}")
    public ResponseEntity<LocalidadeDTO> findById(@PathVariable Long id) {
        var localidade = localidadeService.findById(id);
        return ResponseEntity.ok(localidade);
    }


    @Operation(summary = "cadastro de localidade",method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A lot of location has found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "location not found"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")})
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody LocalidadeDTO localidadeDTO) {
        List<String> violacoesToList = localidadeService.validate(localidadeDTO);
        if (!violacoesToList.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesToList);
        }
        var localidadeSaved = localidadeService.insert(localidadeDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand((localidadeSaved.getId())).toUri();
        return ResponseEntity.created(uri).body(localidadeSaved);
    }

    @Operation(summary = "Atualização de todos os campos de localidade passando id",method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A lot of location has found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "location not found"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")})
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody LocalidadeDTO localidadeDTO, @PathVariable Long id) {
        List<String> violacoesToList = localidadeService.validate(localidadeDTO);
        if (!violacoesToList.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesToList);
        }
        var localidadeSaved = localidadeService.update(id, localidadeDTO);
        return  ResponseEntity.ok(localidadeSaved);
    }

    @Operation(summary = "Deleta predio passando id",method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A lot of Client has found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        localidadeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
