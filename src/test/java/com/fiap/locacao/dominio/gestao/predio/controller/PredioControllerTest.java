package com.fiap.locacao.dominio.gestao.predio.controller;

import com.fiap.locacao.Util.ObjectHelper;
import com.fiap.locacao.dominio.gestao.predio.dto.PredioDTO;
import com.fiap.locacao.dominio.gestao.predio.repositories.PredioService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PredioControllerTest {
    private MockMvc mockMvc;
    @Mock
    PredioService predioService;
    @InjectMocks
    PredioController predioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(predioController).build();
    }

    @Test
    void testFindAll() throws Exception {
        // Given
        List<PredioDTO> predios = Arrays.asList(
                PredioDTO.builder().id(1L).nome("Predio 1").build(),
                PredioDTO.builder().id(2L).nome("Predio 2").build()
        );
        Page<PredioDTO> fakePage = new PageImpl<>(predios);

        when(predioService.findAll(any(), any())).thenReturn(fakePage);

        // When
        mockMvc.perform(get("/predio")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());

        // Then
        verify(predioService, times(1)).findAll(any(), any());
    }

    @Test
    void testFindById() throws Exception {
        var id = 4L;
        var predioDTO = ObjectHelper.gerarPredioDTO();

        when(predioService.findById(id)).thenReturn(predioDTO);

        mockMvc.perform(get("/predio/{id}", id))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

        verify(predioService, times(1)).findById(any(Long.class));
    }

    @Test
    void testInsert() throws Exception {
        PredioDTO predioDTO = ObjectHelper.gerarPredioDTO();

        when(predioService.insert(any(PredioDTO.class))).thenReturn(predioDTO);

        MvcResult result = mockMvc.perform(post("/predio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ObjectHelper.asJsonString(predioDTO)))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assertThat(responseBody).isNotEmpty().contains(predioDTO.getNome());

        verify(predioService, times(1)).insert(any(PredioDTO.class));
    }

    @Test
    void testUpdate() throws Exception{
        PredioDTO predioDTO = ObjectHelper.gerarPredioDTO();
        Long id = new Random().nextLong();
        when(predioService.update(eq(5L), any(PredioDTO.class))).thenReturn(predioDTO);

        mockMvc.perform(put("/predio/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ObjectHelper.asJsonString(predioDTO)))
                .andExpect(status().isOk());

        verify(predioService).update(eq(id), any(PredioDTO.class));
    }

    @Test
    void testDelete() throws Exception{
        Long id = new Random().nextLong();
        doNothing().when(predioService).delete(id);

        mockMvc.perform(delete("/predio/{id}", id))
                .andExpect(status().isNoContent());

        verify(predioService, times(1)).delete(any(Long.class));
    }
}