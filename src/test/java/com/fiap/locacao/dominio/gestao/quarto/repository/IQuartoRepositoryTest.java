package com.fiap.locacao.dominio.gestao.quarto.repository;

import com.fiap.locacao.Util.ObjectHelper;
import com.fiap.locacao.dominio.gestao.localidade.services.LocalidadeService;
import com.fiap.locacao.dominio.gestao.predio.entity.Predio;
import com.fiap.locacao.dominio.gestao.predio.repositories.PredioService;
import com.fiap.locacao.dominio.gestao.predio.repository.IPredioRepository;
import com.fiap.locacao.dominio.gestao.quarto.controller.QuartoController;
import com.fiap.locacao.dominio.gestao.quarto.entity.Quarto;
import com.fiap.locacao.dominio.gestao.quarto.service.QuartoService;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class IQuartoRepositoryTest {
    private MockMvc mockMvc;
    @Mock
    IQuartoRepository repo;
    @InjectMocks
    QuartoService quartoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(repo).build();
    }

    @Test
    void testFindAll() throws Exception{
        List<Quarto> quartos = Arrays.asList(ObjectHelper.gerarQuarto(), ObjectHelper.gerarQuarto());
        Page<Quarto> paginaQuarto = new PageImpl<>(quartos);
        Specification<Quarto> specification = Specification.where(null);
        PageRequest pagina = PageRequest.of(0, 10);

        when(repo.findAll(any(Specification.class), any(PageRequest.class)))
                .thenReturn((Page<Quarto>) paginaQuarto);

        var resultadoObtido = repo.findAll(specification, pagina);

        verify(repo).findAll(any(Specification.class), any(PageRequest.class));
    }

    @Test
    void testFindById() throws Exception{
        var quarto = ObjectHelper.gerarQuarto();
        when(repo.findById(any(Long.class))).thenReturn(Optional.ofNullable(quarto));
        var resultado = repo.findById(quarto.getId());
        verify(repo, times(1)).findById(eq(quarto.getId()));
    }

    @Test
    void testInsert() throws Exception {
        Quarto quarto = ObjectHelper.gerarQuarto();
        when(repo.save(any(Quarto.class))).thenReturn(quarto);
        var resultadoObtido = repo.save(quarto);
        verify(repo, times(1)).save(any(Quarto.class));
    }

    @Test
    void testUpdate() throws Exception {
        Long id = 1L;
        Quarto quarto = ObjectHelper.gerarQuarto();
        when(repo.save(quarto)).thenReturn(quarto);
        var resultadoObtido = repo.save(quarto);
        verify(repo, times(1)).save(eq(quarto));
    }

    @Test
    void testDelete() throws Exception {
        Long id = 5L;
        doNothing().when(repo).deleteById(id);
        repo.deleteById(id);
        verify(repo, times(1));
    }
}

