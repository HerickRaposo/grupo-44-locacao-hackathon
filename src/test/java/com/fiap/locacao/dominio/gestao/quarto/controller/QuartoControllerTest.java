package com.fiap.locacao.dominio.gestao.quarto.controller;

import com.fiap.locacao.Util.ObjectHelper;
import com.fiap.locacao.dominio.gestao.predio.dto.PredioDTO;
import com.fiap.locacao.dominio.gestao.quarto.dto.QuartoDTO;
import com.fiap.locacao.dominio.gestao.quarto.entity.Quarto;
import com.fiap.locacao.dominio.gestao.quarto.service.QuartoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class QuartoControllerTest {
    private MockMvc mockMvc;
    @Mock
    QuartoService quartoService;
    @InjectMocks
    QuartoController quartoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(quartoController).build();
    }

    @Test
    void testFindAll() throws Exception {
        // Given
        List<QuartoDTO> quartos = Arrays.asList(
                QuartoDTO.builder().id(1L).reservado(false).build(),
                QuartoDTO.builder().id(2L).reservado(true).build()
        );
        Page<QuartoDTO> fakePage = new PageImpl<>(quartos);

        when(quartoService.findAll(any(), any())).thenReturn(fakePage);

        // When
        mockMvc.perform(get("/quarto")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());

        verify(quartoService, times(1)).findAll(any(), any());
    }



    @Test
    void testFindById() throws Exception {
        var id = 4L;
        var quartoDto = ObjectHelper.gerarQuartoDTO();

        when(quartoService.findById(id)).thenReturn(quartoDto);

        mockMvc.perform(get("/quarto/{id}", id))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

        verify(quartoService, times(1)).findById(any(Long.class));
    }

    @Test
    void testInsert() throws Exception {
        QuartoDTO quartoDTO = ObjectHelper.gerarQuartoDTO();

        when(quartoService.insert(any(QuartoDTO.class))).thenReturn(quartoDTO);

        MvcResult result = mockMvc.perform(post("/quarto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ObjectHelper.asJsonString(quartoDTO)))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();


        verify(quartoService, times(1)).insert(any(QuartoDTO.class));
    }

    @Test
    void testUpdate() throws Exception{
        QuartoDTO quartoDTO = ObjectHelper.gerarQuartoDTO();
        Long id = new Random().nextLong();
        when(quartoService.update(eq(5L), any(QuartoDTO.class))).thenReturn(quartoDTO);

        mockMvc.perform(put("/quarto/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ObjectHelper.asJsonString(quartoDTO)))
                .andExpect(status().isOk());

        verify(quartoService).update(eq(id), any(QuartoDTO.class));
    }

    @Test
    void testDelete() throws Exception{
        Long id = new Random().nextLong();
        doNothing().when(quartoService).delete(id);

        mockMvc.perform(delete("/quarto/{id}", id))
                .andExpect(status().isNoContent());

        verify(quartoService, times(1)).delete(any(Long.class));
    }
}

