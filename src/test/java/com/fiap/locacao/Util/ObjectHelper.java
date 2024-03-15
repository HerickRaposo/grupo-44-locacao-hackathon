package com.fiap.locacao.Util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.locacao.dominio.gestao.cliente.dto.ClienteDTO;
import com.fiap.locacao.dominio.gestao.cliente.entities.Cliente;
import com.fiap.locacao.dominio.gestao.endereco.dto.EnderecoDTO;
import com.fiap.locacao.dominio.gestao.endereco.entities.Endereco;
import com.fiap.locacao.dominio.gestao.localidade.dto.LocalidadeDTO;
import com.fiap.locacao.dominio.gestao.localidade.entities.Localidade;
import com.fiap.locacao.dominio.gestao.predio.dto.PredioDTO;
import com.fiap.locacao.dominio.gestao.predio.entity.Predio;
import com.fiap.locacao.dominio.gestao.quarto.dto.QuartoDTO;
import com.fiap.locacao.dominio.gestao.quarto.entity.Quarto;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class ObjectHelper {
    public static Cliente gerarCliente(){
        Random random = new Random();
        return Cliente.builder()
                .id(random.nextLong())
                .nomeCompleto("usuario")
                .email("usuario"+ random + "@exemple.com")
                .build();
    }
    public static ClienteDTO gerarClienteDTO(){
        Random random = new Random();
        return ClienteDTO.builder()
                .id(random.nextLong())
                .nomeCompleto("usuario")
                .email("usuario"+ random + "@exemple.com").build();
    }


    public static Predio gerarPredio(){
        Random random = new Random();
        return Predio.builder()
                .id(random.nextLong())
                .nome("Predio")
                .listaquartos(new ArrayList<>())
                .localidade(new Localidade()).build();
    }
    public static PredioDTO gerarPredioDTO(){
        Random random = new Random();
        return PredioDTO.builder()
                .id(random.nextLong())
                .nome("Predio").build();
    }
    public static QuartoDTO gerarQuartoDTO(){
        Random random = new Random();
        return QuartoDTO.builder()
                .id(random.nextLong())
                .tipo(random.nextLong(10L) + 1L)
                .reservado(random.nextBoolean())
                .predio(gerarPredioDTO()).build();
    }

    public static Quarto gerarQuarto(){
        Random random = new Random();
        return Quarto.builder()
                .id(random.nextLong())
                .tipo(random.nextLong(10L) + 1L)
                .reservado(random.nextBoolean())
                .predio(gerarPredio()).build();
    }

    public static String asJsonString(final Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
}
