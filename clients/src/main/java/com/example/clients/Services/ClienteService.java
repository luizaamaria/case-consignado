package com.example.clients.Services;

import com.example.clients.Entites.Cliente;
import com.example.clients.Repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente buscarClientePorCPF(String cpf) {
        return clienteRepository.findByCpf(cpf);
    }

    public boolean isCorrentista(Cliente cliente) {
        return cliente.isCorrentista();
    }

    public String obterSegmento(Cliente cliente) {
        return cliente.getSegmento();
    }

    public String obterConvenio(Cliente cliente) {
        return cliente.getConvenio();
    }

    public List<Cliente> listarTodosClientes() {
        return clienteRepository.findAll();
    }

    public Cliente criarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
}
