package com.contratocustodia.Controllers;

import com.contratocustodia.Entites.Contrato;
import com.contratocustodia.Entites.SimulacaoConsignado;
import com.contratocustodia.Services.ContratoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ContratoController {

    @Autowired
    private ContratoService contratoCustodiaService;

    @PostMapping("/contrato-custodia")
    public ResponseEntity<Contrato> create(@RequestBody Long id)
    {

        Contrato contrato = contratoCustodiaService.novoContrato(id);
        return ResponseEntity.ok(contrato);
    }

    @GetMapping("/contratos-custodia")
    public ResponseEntity<List<Contrato>> listarContratos() {
        List<Contrato> contrato = contratoCustodiaService.contratos();

        if (contrato.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(contrato);
    }
}
