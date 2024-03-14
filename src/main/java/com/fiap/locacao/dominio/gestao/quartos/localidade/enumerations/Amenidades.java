package com.fiap.locacao.dominio.gestao.quartos.localidade.enumerations;

import com.fiap.locacao.dominio.gestao.quartos.quarto.enumerations.TipoQuarto;

import java.util.Arrays;
import java.util.List;

public enum Amenidades {
    PISCINA_ADULTO_AQUECIDA_COBERTA(1, "Piscina Adulto, aquecida e coberta"),
    PISCINA_ADULTO_NAO_AQUECIDA_AREA_ABERTA(2, "Piscina Adulto, não aquecida em área aberta"),
    PISCINA_INFANTIL_AQUECIDA_COBERTA(3, "Piscina Infantil, aquecida e coberta"),
    PISCINA_INFANTIL_NAO_AQUECIDA_AREA_ABERTA(4, "Piscina Infantil, não aquecida em área aberta"),
    RESTAURANTES_SELF_SERVICE(5, "2 x Restaurantes no estilo Self Service"),
    RESTAURANTE_A_LA_CARTE(6, "1 x Restaurante a La Carte"),
    AREA_KIDS(7, "1 x Área Kids, com brinquedoteca, vídeo games e biblioteca"),
    EQUIPE_ENTRETENIMENTO_INFANTIL(8, "Equipe de Entretenimento Infantil (diversas idades)");

    private final long id;
    private final String descricao;

    Amenidades(long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public static String buscarDescricaoAmenidade(Long codigo) {
        Amenidades amenidade = buscarNome(codigo);
        if (amenidade == null) {
            return null;
        }
        return amenidade.getDescricao();
    }

    public static Amenidades buscarNome(Long id) {
        if (id == null || id.equals(0L)) {
            return null;
        }

        return Arrays.asList(Amenidades.values()).stream().filter(amenidade -> (amenidade.getId() == id)).findFirst().orElse(null);
    }

    public static Long buscarIdAmenidade(String nome) {
        Amenidades amenidade = buscarId(nome);
        if (amenidade == null) {
            return null;
        }
        return amenidade.getId();
    }

    public static Amenidades buscarId(String nome) {
        if (nome == null || nome.isBlank()) {
            return null;
        }

        return Arrays.asList(Amenidades.values()).stream().filter(amenidade -> (amenidade.getDescricao().equals(nome))).findFirst().orElse(null);
    }

    public static List<Amenidades> listarAmenidades() {
        return Arrays.asList(Amenidades.values());
    }

    public long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }
}

