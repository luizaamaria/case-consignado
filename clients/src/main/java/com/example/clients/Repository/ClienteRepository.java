package com.example.clients.Repository;

import com.example.clients.Entites.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// INTERFACE - quem implmentar essa interface tem a obrigação de implementar os metodos abaixo
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Cliente findByCpf(String cpf);
}
