package com.example.clients.Services;

import com.example.clients.Entites.Cliente;
import com.example.clients.Repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service // SERVICE TRATA A REGRA DE NEGOCIO

public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente buscarClientePorCPF(String cpf) {
        return clienteRepository.findByCpf(cpf);
        // retorna um cliente com base no cpf
    }

    public List<Cliente> listarTodosClientes() {
        return clienteRepository.findAll();
    }

    public Cliente criarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
}
