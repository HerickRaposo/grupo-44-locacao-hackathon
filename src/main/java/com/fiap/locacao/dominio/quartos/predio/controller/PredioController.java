package com.fiap.locacao.dominio.quartos.predio.controller;

import com.fiap.locacao.dominio.quartos.predio.dto.PredioDTO;
import com.fiap.locacao.dominio.quartos.predio.service.PredioService;
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
public class PredioController {
    @Autowired
    private PredioService predioService;

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

    @GetMapping("/{id}")
    public ResponseEntity<PredioDTO> findById(@PathVariable Long id) {
        var predio = predioService.findById(id);
        return ResponseEntity.ok(predio);
    }


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

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody PredioDTO predioDTO, @PathVariable Long id) {
        List<String> violacoesToList = predioService.validate(predioDTO);
        if (!violacoesToList.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesToList);
        }
        var predioSaved = predioService.update(id, predioDTO);
        return  ResponseEntity.ok(predioSaved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        predioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
