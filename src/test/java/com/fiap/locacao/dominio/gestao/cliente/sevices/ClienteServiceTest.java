package com.fiap.locacao.dominio.gestao.cliente.sevices;

import com.fiap.locacao.Util.ObjectHelper;
import com.fiap.locacao.dominio.gestao.cliente.dto.ClienteDTO;
import com.fiap.locacao.dominio.gestao.cliente.dto.ClientePatchDTO;
import com.fiap.locacao.dominio.gestao.cliente.entities.Cliente;
import com.fiap.locacao.dominio.gestao.cliente.repositories.IClienteRepository;
import com.fiap.locacao.dominio.gestao.endereco.dto.EnderecoDTO;
import com.fiap.locacao.dominio.gestao.endereco.dto.Paginator;
import com.fiap.locacao.dominio.gestao.endereco.dto.RestDataReturnDTO;
import com.fiap.locacao.dominio.gestao.endereco.repositories.IEEnderecoRepository;
import com.fiap.locacao.dominio.gestao.endereco.services.EnderecoService;
import com.fiap.locacao.dominio.gestao.localidade.services.LocalidadeService;
import com.fiap.locacao.dominio.gestao.predio.dto.PredioDTO;
import com.fiap.locacao.dominio.gestao.predio.entity.Predio;
import com.fiap.locacao.dominio.gestao.predio.repositories.PredioService;
import com.fiap.locacao.dominio.gestao.predio.repository.IPredioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;
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
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.*;

class ClienteServiceTest {
    private MockMvc mockMvc;
    @Mock
    IClienteRepository repo;
    @InjectMocks
    ClienteService clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(clienteService).build();
    }

    @Test
    void testFindById() {
        var cliente = ObjectHelper.gerarCliente();
        when(repo.findById(any(Long.class))).thenReturn(Optional.ofNullable(cliente));
        var resultado = clienteService.findById(cliente.getId());
        verify(repo, times(1)).findById(eq(cliente.getId()));
    }


    @Test
    void testDelete() {
        Long id = 5L;
        Cliente cliente = new Cliente();
        cliente.setId(id);
        when(repo.findById(any(Long.class))).thenReturn(Optional.of(cliente));
        doNothing().when(repo).deleteById(id);
        clienteService.deletePessoa(id);
        verify(repo, times(1));
    }
}

