package com.fiap.locacao.dominio.gestao.cliente.repositories;


import com.fiap.locacao.dominio.gestao.cliente.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente,Long> {
    boolean existsByCpf(String cpf);

    boolean existsByPassaPorte(String passaPort);
    boolean existsByEmail(String email);
}
