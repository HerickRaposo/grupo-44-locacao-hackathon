package com.fiap.locacao.dominio.quartos.quarto.controller;

import com.fiap.locacao.dominio.quartos.quarto.dto.QuartoDTO;
import com.fiap.locacao.dominio.quartos.quarto.service.QuartoService;
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
public class QuartoController {
    @Autowired
    private QuartoService quartoService;

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

    @GetMapping("/{id}")
    public ResponseEntity<QuartoDTO> findById(@PathVariable Long id) {
        var quarto = quartoService.findById(id);
        return ResponseEntity.ok(quarto);
    }

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

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody QuartoDTO quartoDTO, @PathVariable Long id) {
        List<String> violacoesToList = quartoService.validate(quartoDTO);
        if (!violacoesToList.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesToList);
        }
        var quartpUpdated = quartoService.update(id, quartoDTO);
        return  ResponseEntity.ok(quartpUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        quartoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
