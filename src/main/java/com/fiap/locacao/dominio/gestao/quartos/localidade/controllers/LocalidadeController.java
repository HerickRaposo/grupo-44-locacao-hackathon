package com.fiap.locacao.dominio.gestao.quartos.localidade.controllers;

import com.fiap.locacao.dominio.gestao.cliente.dto.ClienteDTO;
import com.fiap.locacao.dominio.gestao.cliente.dto.ClientePatchDTO;
import com.fiap.locacao.dominio.gestao.cliente.sevices.ClienteService;
import com.fiap.locacao.dominio.gestao.endereco.dto.RestDataReturnDTO;
import com.fiap.locacao.dominio.gestao.quartos.localidade.services.LocalidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping(value = "/localidades",produces = {"application/json"})
@Tag(name = "API LOCALIDADES")
public class LocalidadeController {

    private LocalidadeService localidadeService;

    @Operation(tags="Lista de localidades" ,summary = "Lista todos as localidades cadastradas no banco de dados",method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A lot of Client has found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")})
    @GetMapping
    public RestDataReturnDTO findAll(
            @RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
            @RequestParam(value = "tamanho", defaultValue = "10") Integer tamanho,
            @RequestParam(value = "filtroEndereco",required = false) Long filtroIdEndereco
    ){
        PageRequest pageRequest = PageRequest.of(pagina,tamanho);
        return localidadeService.findAll(pageRequest, filtroIdEndereco);
    }
    @Operation(summary = "Consulta cliente passando o id",method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client was found by Id"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")})
    @GetMapping("/{id}")
    public ClienteDTO getProductById(@PathVariable Long id) {
        return clienteService.findById(id);
    }

    @Operation(summary = "Cadastro de clientes",method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Client was created"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")})
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody @Valid ClienteDTO cliente){
        try {

            cliente = clienteService.insert(cliente);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}").buildAndExpand(cliente.getId()).toUri();
            return ResponseEntity.created(uri).body(cliente);
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Atualiza cliente em todos os campos passando o id do cliente",method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Found was Updated"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")})
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> update(@RequestBody ClienteDTO clienteDTO,@PathVariable Long id){
        return ResponseEntity.ok(clienteService.update(id,clienteDTO));
    }

    @Operation(summary = "Atualiza Cliente em um único campo desejado",method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client was update "),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")})
    @PatchMapping("/{id}")
    public ClientePatchDTO updateCondutorFiedls(@PathVariable Long id, @RequestBody Map<String, Object> fields){
        return clienteService.updatePessoaByFields(id,fields);
    }

    @Operation(summary = "Apaga cliente do banco de dados passando, buscanbdo pelo id",method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the book"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "500", description = "Erro no seervio")})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Long deleteProduct(@PathVariable Long id) {
        return clienteService.deletePessoa(id);
    }

}
