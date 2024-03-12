package com.fiap.locacao.dominio.predio.repository;

import com.fiap.locacao.dominio.predio.entity.Predio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IPredioRepository extends JpaRepository<Predio, Long>, JpaSpecificationExecutor<Predio> {
}
