package com.example.clients.Controllers;

import com.example.clients.Entites.Cliente;
import com.example.clients.Services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

// CONTROLLER - RECEBE UMA ROTA E DECIDE PRA ONDE ELA TEM QUE IR
@RestController
@RequestMapping("/api/v1")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/cliente/{cpf}")
    public ResponseEntity<Cliente> identificarCliente(@PathVariable String cpf) {
        Cliente cliente = clienteService.buscarClientePorCPF(cpf);

        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/clientes")
    public ResponseEntity<List<Cliente>> listarTodosClientes() {
        List<Cliente> clientes = clienteService.listarTodosClientes();

        if (clientes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(clientes);
    }

    @PostMapping("/cliente")
    public ResponseEntity<Cliente> criarCliente(@RequestBody Cliente cliente)
    {
        Cliente novoCliente = clienteService.criarCliente(cliente);

        if (novoCliente == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.created(
                URI.create("/cliente/" + novoCliente.getCpf())
        ).body(novoCliente);
    }
}
