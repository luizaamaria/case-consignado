package com.example.clients;
import com.example.clients.Entites.Cliente;
import com.example.clients.Repository.ClienteRepository;
import com.example.clients.Services.ClienteService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@SpringBootTest
public class ClienteTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    public void testBuscarClientePorCPF() {
        String cpf = "123456789";
        Cliente clienteMock = new Cliente();
        clienteMock.setCpf(cpf);

        when(clienteRepository.findByCpf(cpf)).thenReturn(clienteMock);

        Cliente cliente = clienteService.buscarClientePorCPF(cpf);
        assertNotNull(cliente);
        assertEquals(cpf, cliente.getCpf());
    }

    @Test
    public void testListarTodosClientes() {
        Cliente cliente1 = new Cliente();
        Cliente cliente2 = new Cliente();
        List<Cliente> clientes = Arrays.asList(cliente1, cliente2);

        when(clienteRepository.findAll()).thenReturn(clientes);

        List<Cliente> clientesEncontrados = clienteService.listarTodosClientes();
        assertEquals(clientes.size(), clientesEncontrados.size());
    }

    @Test
    public void testCriarCliente() {
        Cliente cliente = new Cliente();

        when(clienteRepository.save(cliente)).thenReturn(cliente);
        Cliente clienteCriado = clienteService.criarCliente(cliente);

        assertNotNull(clienteCriado);
    }
}
