package com.fiap.locacao.dominio.gestao.servicoseopcionais.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiap.locacao.dominio.gestao.servicoseopcionais.entity.ServicosItens;

public interface IServicosItensRepository extends JpaRepository<ServicosItens, Long>{

}
