package com.fiap.locacao.dominio.gestao.cliente.controllers;

import com.fiap.locacao.Util.ObjectHelper;
import com.fiap.locacao.dominio.gestao.cliente.dto.ClienteDTO;
import com.fiap.locacao.dominio.gestao.cliente.dto.ClientePatchDTO;
import com.fiap.locacao.dominio.gestao.cliente.entities.Cliente;
import com.fiap.locacao.dominio.gestao.cliente.repositories.IClienteRepository;
import com.fiap.locacao.dominio.gestao.cliente.sevices.ClienteService;
import com.fiap.locacao.dominio.gestao.endereco.dto.EnderecoDTO;
import com.fiap.locacao.dominio.gestao.endereco.dto.Paginator;
import com.fiap.locacao.dominio.gestao.endereco.dto.RestDataReturnDTO;
import com.fiap.locacao.dominio.gestao.endereco.entities.Endereco;
import com.fiap.locacao.dominio.gestao.localidade.entities.Localidade;
import com.fiap.locacao.dominio.gestao.localidade.enumerations.Amenidades;
import com.fiap.locacao.dominio.gestao.predio.dto.PredioDTO;
import com.fiap.locacao.dominio.gestao.predio.entity.Predio;
import com.fiap.locacao.dominio.gestao.quarto.entity.Quarto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ClienteControllerTest {
    private MockMvc mockMvc;
    @Mock
    ClienteService clienteService;

    @Mock
    IClienteRepository repo;
    @InjectMocks
    ClienteController clienteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(clienteController).build();
    }

    @Test
    void testFindById() throws Exception {
        var id = 4L;
        var clienteDTO = ObjectHelper.gerarClienteDTO();

        when(clienteService.findById(id)).thenReturn(clienteDTO);

        mockMvc.perform(get("/clientes/{id}", id))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

        verify(clienteService, times(1)).findById(any(Long.class));
    }

    @Test
    void testUpdate() throws Exception{
        ClienteDTO clienteDTO = ObjectHelper.gerarClienteDTO();
        Long id = new Random().nextLong();
        when(clienteService.update(eq(5L), any(ClienteDTO.class))).thenReturn(clienteDTO);

        mockMvc.perform(put("/clientes/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ObjectHelper.asJsonString(clienteDTO)))
                .andExpect(status().isOk());

        verify(clienteService).update(eq(id), any(ClienteDTO.class));
    }

    @Test
    void testDelete() throws Exception{
        Long id = new Random().nextLong();
        Cliente cliente = ObjectHelper.gerarCliente();

        when(repo.findById(id)).thenReturn(Optional.ofNullable(cliente));
        when(repo.count()).thenReturn(0L);

        mockMvc.perform(delete("/clientes/{id}", id))
                .andExpect(status().isNoContent());

        verify(clienteService, times(1)).deletePessoa(id);
    }
}