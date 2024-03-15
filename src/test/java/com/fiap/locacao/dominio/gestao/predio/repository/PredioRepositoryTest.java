package com.fiap.locacao.dominio.gestao.predio.repository;

import com.fiap.locacao.Util.ObjectHelper;
import com.fiap.locacao.dominio.gestao.cliente.entities.Cliente;
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
import org.junit.jupiter.api.AfterEach;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PredioRepositoryTest {
    private MockMvc mockMvc;
    @Mock
    IPredioRepository repo;
    @Mock
    LocalidadeService localidadeService;
    @InjectMocks
    PredioService predioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(repo).build();
    }

    @Test
    void testFindAll() {
        List<Predio> predios = Arrays.asList(ObjectHelper.gerarPredio(), ObjectHelper.gerarPredio());
        Page<Predio> paginaPredio = new PageImpl<>(predios);
        Specification<Predio> specification = Specification.where(null);
        PageRequest pagina = PageRequest.of(0, 10);

        when(repo.findAll(any(Specification.class), any(PageRequest.class)))
                .thenReturn((Page<Predio>) paginaPredio);

        var resultadoObtido = repo.findAll(specification, pagina);

        verify(repo).findAll(any(Specification.class), any(PageRequest.class));
    }

    @Test
    void testFindById() {
        var predio = ObjectHelper.gerarPredio();
        when(repo.findById(any(Long.class))).thenReturn(Optional.ofNullable(predio));
        var predioObtido = repo.findById(predio.getId());
        verify(repo, times(1)).findById(eq(predio.getId()));
    }

    @Test
    void testInsert() throws Exception {
        Predio predio = ObjectHelper.gerarPredio();
        when(repo.save(any(Predio.class))).thenReturn(predio);
        var resultadoObtido = repo.save(predio);
        verify(repo, times(1)).save(any(Predio.class));
    }

    @Test
    void testUpdate() {
        Long id = 1L;
        Predio predio = ObjectHelper.gerarPredio();
        PredioDTO predioDTO = new PredioDTO();
        BeanUtils.copyProperties(predio, predioDTO);
        when(repo.findById(id)).thenReturn(Optional.of(predio));
        when(repo.save(any(Predio.class))).thenReturn(predio);
        var resultadoObtido = predioService.update(id, predioDTO);
        verify(repo, times(1)).save(eq(predio));
    }

    @Test
    void testDelete() {
        Long id = 5L;
        doNothing().when(repo).deleteById(id);
        repo.deleteById(id);
        verify(repo, times(1));
    }
}