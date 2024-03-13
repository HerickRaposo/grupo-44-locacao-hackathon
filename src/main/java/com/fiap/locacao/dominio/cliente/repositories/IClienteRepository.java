package com.fiap.locacao.dominio.cliente.repositories;


import com.fiap.locacao.dominio.cliente.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente,Long> {
}
