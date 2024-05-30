package com.example.simulacaoconsignado.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class SimulacaoConsignadoController {

    @GetMapping("calcular-consignado")
    String calcConsignado()
    {
        RestTemplate r = new RestTemplate();
        String client = r.getForObject("http://localhost:8080/client", String.class);

        // Logica consignado

        return client;
    }
}
