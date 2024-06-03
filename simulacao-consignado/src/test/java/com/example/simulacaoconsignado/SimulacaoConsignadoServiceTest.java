package com.example.simulacaoconsignado;

import com.example.simulacaoconsignado.Entites.Cliente;
import com.example.simulacaoconsignado.Entites.SimulacaoConsignado;
import com.example.simulacaoconsignado.Repository.SimulacaoConsignadoRepository;
import com.example.simulacaoconsignado.Services.SimulacaoConsignadoService;
import com.example.simulacaoconsignado.DTOs.SimulacaoRequestBodyDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class SimulacaoConsignadoServiceTest {

    @Mock
    private SimulacaoConsignadoRepository simulacaoConsignadoRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private SimulacaoConsignadoService simulacaoConsignadoService;

    @Test
    public void testCalcularSimulacao() {
        // Setup
        SimulacaoRequestBodyDTO requestDTO = new SimulacaoRequestBodyDTO();
        requestDTO.setCpf("12345678900");
        requestDTO.setValorSolicitado(5000);
        requestDTO.setQuantidadeParcelas(5);

        Cliente cliente = new Cliente();
        cliente.setCpf("12345678900");
        cliente.setCorrentista(true);
        cliente.setConvenio("EP");
        cliente.setSegmento("Varejo");

        ResponseEntity<Cliente> responseEntity = ResponseEntity.ok(cliente);

        when(restTemplate.getForEntity("http://localhost:8080/cliente/" + cliente.getCpf(), Cliente.class)).thenReturn(responseEntity);
        when(simulacaoConsignadoRepository.save(any(SimulacaoConsignado.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(restTemplate.postForObject(anyString(), any(), any())).thenReturn(null);

        SimulacaoConsignado result = simulacaoConsignadoService.calcularSimulacao(requestDTO);

        // Asserts
        assertEquals("12345678900", result.getCpf());
        assertEquals(5000, result.getValorSolicitado());
        assertEquals(5, result.getQuantidadeParcelas());
        assertEquals(0.0247, result.getTaxaJuros(), 0.001); // considerando a taxa calculada corretamente com o desconto
        assertEquals(Locale.US, "2.47%", result.getTaxaJurosFormatado());
        assertEquals(5617.5, result.getValorTotal(), 0.1);
        assertEquals(1123.5, result.getValorParcela(), 0.1);

        verify(restTemplate, times(1)).getForEntity(anyString(), eq(Cliente.class));
        verify(simulacaoConsignadoRepository, times(1)).save(any(SimulacaoConsignado.class));
        verify(restTemplate, times(1)).postForObject(anyString(), any(), any());
    }
}
