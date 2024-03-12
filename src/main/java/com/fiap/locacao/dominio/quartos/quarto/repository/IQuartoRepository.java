package com.fiap.locacao.dominio.quartos.quarto.repository;

import com.fiap.locacao.dominio.quartos.quarto.entity.Quarto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IQuartoRepository extends JpaRepository<Quarto, Long>, JpaSpecificationExecutor<Quarto> {
}
