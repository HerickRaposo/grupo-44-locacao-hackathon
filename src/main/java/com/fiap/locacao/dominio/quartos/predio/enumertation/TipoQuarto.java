package com.fiap.locacao.dominio.quartos.predio.enumertation;

import java.util.Arrays;
import java.util.List;

public enum TipoQuarto {
    STANDARD_SINGLE(1L, "Standard Single", 2, 1, "54 pols.", "Simples", 350.00),
    STANDARD_DOUBLE(2L, "Standard Double", 4, 3, "54 pols.", "Simples", 450.00),
    LUXURY_SINGLE(3L, "Luxury Single", 2, 1, "62 pols.", "Luxo", 550.00),
    LUXURY_DOUBLE(4L, "Luxury Double", 4, 3, "62 pols.", "Luxo", 650.00),
    PREMIUM_SINGLE(5L, "Premium Single", 2, 1, "62 pols.", "Premium", 850.00),
    PREMIUM_DOUBLE(6L, "Premium Double", 4, 3, "62 pols.", "Premium", 950.00);

    private final long codigo;
    private final String descricao;
    private final int totalPeople;
    private final int totalBeds;
    private final String tvSize;
    private final String bathroomType;
    private final double valorDiaria;

    TipoQuarto(long codigo, String descricao, int totalPeople, int totalBeds, String tvSize, String bathroomType, double valorDiaria) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.totalPeople = totalPeople;
        this.totalBeds = totalBeds;
        this.tvSize = tvSize;
        this.bathroomType = bathroomType;
        this.valorDiaria = valorDiaria;
    }

    public static String buscarDescricaoTipoQuarto(Long codigo) {
        TipoQuarto tipo = buscarNome(codigo);
        if (tipo == null) {
            return null;
        }
        return tipo.getDescricao();
    }

    public static TipoQuarto buscarNome(Long codigo) {
        if (codigo == null || codigo.equals(0L)) {
            return null;
        }

        return Arrays.asList(TipoQuarto.values()).stream().filter(tipo -> (tipo.getCodigo() == codigo)).findFirst().orElse(null);
    }

    public static Long buscarCodigoTipoQuarto(String nome) {
        TipoQuarto tipo = buscarCodigo(nome);
        if (tipo == null) {
            return null;
        }
        return tipo.getCodigo();
    }

    public static TipoQuarto buscarCodigo(String nome) {
        if (nome == null || nome.isBlank()) {
            return null;
        }

        return Arrays.asList(TipoQuarto.values()).stream().filter(tipo -> (tipo.getDescricao().equals(nome))).findFirst().orElse(null);
    }

    public static List<TipoQuarto> listarTipoQuarto() {
        return Arrays.asList(TipoQuarto.values());
    }

    public long getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getTotalPeople() {
        return totalPeople;
    }

    public int getTotalBeds() {
        return totalBeds;
    }

    public String getTvSize() {
        return tvSize;
    }

    public String getBathroomType() {
        return bathroomType;
    }

    public double getValorDiaria() {
        return valorDiaria;
    }
}