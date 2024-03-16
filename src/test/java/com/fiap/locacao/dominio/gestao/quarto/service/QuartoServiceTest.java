package com.fiap.locacao.dominio.gestao.quarto.service;

import com.fiap.locacao.Util.ObjectHelper;
import com.fiap.locacao.dominio.gestao.predio.dto.PredioDTO;
import com.fiap.locacao.dominio.gestao.predio.entity.Predio;
import com.fiap.locacao.dominio.gestao.quarto.dto.QuartoDTO;
import com.fiap.locacao.dominio.gestao.quarto.entity.Quarto;
import com.fiap.locacao.dominio.gestao.quarto.repository.IQuartoRepository;
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

import static org.mockito.Mockito.*;

class QuartoServiceTest {
    private MockMvc mockMvc;
    @Mock
    IQuartoRepository repo;
    @InjectMocks
    QuartoService quartoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(quartoService).build();
    }

    @Test
    void testFindAll() {
        PageRequest pagina = PageRequest.of(0, 10);

        List<Quarto> quartos = Arrays.asList(ObjectHelper.gerarQuarto(), ObjectHelper.gerarQuarto());
        Page<Quarto> paginaquartos = new PageImpl<>(quartos);

        when(repo.findAll(any(Specification.class), any(PageRequest.class)))
                .thenReturn((Page<Quarto>) paginaquartos);

        Page<QuartoDTO> resultadoObtido = quartoService.findAll(pagina, null);

        verify(repo).findAll(any(Specification.class), any(PageRequest.class));
    }

    @Test
    void testFindById() {
        var quarto = ObjectHelper.gerarQuarto();
        when(repo.findById(any(Long.class))).thenReturn(Optional.ofNullable(quarto));
        var predioObtido = quartoService.findById(quarto.getId());
        verify(repo, times(1)).findById(eq(quarto.getId()));
    }

    @Test
    void testUpdate() {
        Long id = 1L;
        Quarto quarto = ObjectHelper.gerarQuarto();
        QuartoDTO quartoDTO = new QuartoDTO();
        quartoDTO.setId(id);
        BeanUtils.copyProperties(quarto, quartoDTO);

        when(repo.getReferenceById(any(Long.class))).thenReturn(quarto);
        when(repo.save(any(Quarto.class))).thenReturn(quarto);


        var resultadoObtido = quartoService.update(id, quartoDTO);
        verify(repo, times(1)).save(any(Quarto.class));
    }

    @Test
    void testDelete() {
        Long id = 5L;
        doNothing().when(repo).deleteById(id);
        quartoService.delete(id);
        verify(repo, times(1));
    }
}

