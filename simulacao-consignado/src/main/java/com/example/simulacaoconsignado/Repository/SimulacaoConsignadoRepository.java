package com.example.simulacaoconsignado.Repository;

import com.example.simulacaoconsignado.Entites.SimulacaoConsignado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimulacaoConsignadoRepository extends JpaRepository<SimulacaoConsignado, Long> {
}