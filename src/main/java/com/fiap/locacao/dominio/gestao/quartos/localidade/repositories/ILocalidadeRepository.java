package com.fiap.locacao.dominio.gestao.quartos.localidade.repositories;

import com.fiap.locacao.dominio.gestao.quartos.localidade.entities.Localidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ILocalidadeRepository extends JpaRepository<Localidade, Long>, JpaSpecificationExecutor<Localidade> {
}
