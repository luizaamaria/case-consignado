package com.example.simulacaoconsignado.Controllers;

import com.example.simulacaoconsignado.DTOs.SimulacaoRequestBodyDTO;
import com.example.simulacaoconsignado.Entites.SimulacaoConsignado;
import com.example.simulacaoconsignado.Exceptions.RequestApiException;
import com.example.simulacaoconsignado.Exceptions.UnknownApiException;
import com.example.simulacaoconsignado.Services.SimulacaoConsignadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class SimulacaoConsignadoController {

    // propriedade
    @Autowired
    private SimulacaoConsignadoService simulacaoConsignadoServices;
    // só pode ser acessada aqui nessa Classe

    // método
    @PostMapping("/calcular-consignado")
    public ResponseEntity<SimulacaoConsignado> calcularConsignado(@RequestBody SimulacaoRequestBodyDTO simulacaoRequestBodyDTO) {
        try {
            SimulacaoConsignado simulacaoConsignado = simulacaoConsignadoServices.calcularSimulacao(simulacaoRequestBodyDTO);

            if (simulacaoConsignado == null) {
                ResponseEntity.badRequest().build();
            }

            return ResponseEntity.ok(simulacaoConsignado);
        } catch (UnknownApiException e) {
            ResponseEntity.internalServerError().build();
        } catch (RuntimeException e) {
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/simulacoes")
    public ResponseEntity<List<SimulacaoConsignado>> listarTodasSimulacoes() {
        List<SimulacaoConsignado> simulacaoConsignado = simulacaoConsignadoServices.listarTodasSimulacoes();

        if (simulacaoConsignado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(simulacaoConsignado);
    }
}