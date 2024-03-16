package com.fiap.locacao.dominio.gestao.predio.repository;

import com.fiap.locacao.dominio.gestao.predio.entity.Predio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IPredioRepository extends JpaRepository<Predio, Long>, JpaSpecificationExecutor<Predio> {
}
