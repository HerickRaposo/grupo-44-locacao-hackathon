package com.fiap.locacao.dominio.gestao.cliente.repositories;

import com.fiap.locacao.Util.ObjectHelper;
import com.fiap.locacao.dominio.gestao.cliente.entities.Cliente;
import com.fiap.locacao.dominio.gestao.cliente.sevices.ClienteService;
import com.fiap.locacao.dominio.gestao.endereco.dto.EnderecoDTO;
import com.fiap.locacao.dominio.gestao.endereco.entities.Endereco;
import com.fiap.locacao.dominio.gestao.localidade.dto.LocalidadeDTO;
import com.fiap.locacao.dominio.gestao.localidade.entities.Localidade;
import com.fiap.locacao.dominio.gestao.localidade.enumerations.Amenidades;
import com.fiap.locacao.dominio.gestao.localidade.services.LocalidadeService;
import com.fiap.locacao.dominio.gestao.predio.dto.PredioDTO;
import com.fiap.locacao.dominio.gestao.predio.entity.Predio;
import com.fiap.locacao.dominio.gestao.predio.repositories.PredioService;
import com.fiap.locacao.dominio.gestao.predio.repository.IPredioRepository;
import com.fiap.locacao.dominio.gestao.quarto.dto.QuartoDTO;
import com.fiap.locacao.dominio.gestao.quarto.entity.Quarto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class IClienteRepositoryTest {
    private MockMvc mockMvc;
    @Mock
    IClienteRepository repo;
    @InjectMocks
    ClienteService clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(repo).build();
    }
    @Test
    void testFindAll() {
        List<Cliente> clientes = Arrays.asList(ObjectHelper.gerarCliente(), ObjectHelper.gerarCliente());
        Page<Cliente> page = new PageImpl<>(clientes, PageRequest.of(0, 10), clientes.size());

        when(repo.findAll(any(PageRequest.class))).thenReturn(page);
        var resultadoObtido = repo.findAll(PageRequest.of(0, 10));
        verify(repo).findAll(any(PageRequest.class));
    }

    @Test
    void testFindById() {
        var cliente = ObjectHelper.gerarCliente();
        when(repo.findById(any(Long.class))).thenReturn(Optional.ofNullable(cliente));
        var clienteObtido = repo.findById(cliente.getId());
        verify(repo, times(1)).findById(eq(cliente.getId()));
    }

    @Test
    void testInsert() {
        Cliente cliente = ObjectHelper.gerarCliente();
        when(repo.save(any(Cliente.class))).thenReturn(cliente);
        var clienteObtido = repo.save(cliente);
        verify(repo, times(1));
    }

    @Test
    void testUpdate() {
        Long id = 1L;
        Cliente cliente = ObjectHelper.gerarCliente();
        when(repo.save(cliente)).thenReturn(cliente);
        var resultadoObtido = repo.save(cliente);
        verify(repo, times(1)).save(eq(cliente));
    }

    @Test
    void testDelete() {
        Long id = 5L;
        doNothing().when(repo).deleteById(id);
        repo.deleteById(id);
        verify(repo, times(1));
    }
}

