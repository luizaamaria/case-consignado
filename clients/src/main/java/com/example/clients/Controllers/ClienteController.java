package com.example.clients.Controllers;

import com.example.clients.Entites.Cliente;
import com.example.clients.Services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/cliente/{cpf}")
    public Cliente identificarCliente(@PathVariable String cpf) {
        Cliente cliente = clienteService.buscarClientePorCPF(cpf);
        
        if (cliente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado.");
        }
        return cliente;
    }

    @GetMapping("/clientes")
    public List<Cliente> listarTodosClientes() {
        return clienteService.listarTodosClientes();
    }

    @PostMapping("/cliente")
    public Cliente criarCliente(@RequestBody Cliente cliente) {
        return clienteService.criarCliente(cliente);
    }
}
