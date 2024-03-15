package com.fiap.locacao.dominio.gestao.predio.service;

import com.fiap.locacao.Util.ObjectHelper;
import com.fiap.locacao.dominio.gestao.cliente.dto.ClienteDTO;
import com.fiap.locacao.dominio.gestao.localidade.services.LocalidadeService;
import com.fiap.locacao.dominio.gestao.predio.dto.PredioDTO;
import com.fiap.locacao.dominio.gestao.predio.entity.Predio;
import com.fiap.locacao.dominio.gestao.predio.repositories.PredioService;
import com.fiap.locacao.dominio.gestao.predio.repository.IPredioRepository;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class PredioServiceTest {
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
        PageRequest pagina = PageRequest.of(0, 10);

        List<Predio> predios = Arrays.asList(ObjectHelper.gerarPredio(), ObjectHelper.gerarPredio());
        Page<Predio> paginaPredio = new PageImpl<>(predios);

        when(repo.findAll(any(Specification.class), any(PageRequest.class)))
                .thenReturn((Page<Predio>) paginaPredio);

        Page<PredioDTO> resultadoObtido = predioService.findAll(pagina, null);

        verify(repo).findAll(any(Specification.class), any(PageRequest.class));
    }

    @Test
    void testFindById() {
        var predio = ObjectHelper.gerarPredio();
        when(repo.findById(any(Long.class))).thenReturn(Optional.ofNullable(predio));
        var predioObtido = predioService.findById(predio.getId());
        verify(repo, times(1)).findById(eq(predio.getId()));
    }

    @Test
    void testUpdate() {
        Long id = 1L;
        Predio predio = ObjectHelper.gerarPredio();
        PredioDTO predioDTO = new PredioDTO();
        predioDTO.setId(id);
        BeanUtils.copyProperties(predio, predioDTO);

        when(repo.getReferenceById(any(Long.class))).thenReturn(predio);
        when(repo.save(any(Predio.class))).thenReturn(predio);


        var resultadoObtido = predioService.update(id, predioDTO);
        verify(repo, times(1)).save(any(Predio.class));
    }

    @Test
    void testDelete() {
        Long id = 5L;
        doNothing().when(repo).deleteById(id);
        predioService.delete(id);
        verify(repo, times(1));
    }
}
