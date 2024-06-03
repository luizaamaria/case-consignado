package com.contratocustodia.Services;

import com.contratocustodia.Entites.Contrato;
import com.contratocustodia.Entites.SimulacaoConsignado;
import com.contratocustodia.Repositories.ContratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ContratoService {

    @Autowired
    private ContratoRepository contratoCustodiaRepository;

    public ContratoService(ContratoRepository contratoCustodiaRepository) { this.contratoCustodiaRepository = contratoCustodiaRepository; }

    public Contrato novoContrato(Long id)
    {
        Contrato contrato = new Contrato();
        contrato.setDataContrato( LocalDate.now() );
        contrato.setSimulacaoId( id );
        return contratoCustodiaRepository.save( contrato );
    }

    public List<Contrato> contratos() { return contratoCustodiaRepository.findAll(); }
}
