package com.example.clients.Controllers;

import com.example.clients.Entites.Client;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ClientsController {

    @GetMapping("/client")
    Client one()
    {
        Client client = new Client("Luiza Maria");

        // Logica retornar client por ID (ou cpf), por ex
        return client;
    }
}
