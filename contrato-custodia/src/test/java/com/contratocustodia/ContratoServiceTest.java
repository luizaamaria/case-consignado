package com.contratocustodia;

import com.contratocustodia.Entites.Contrato;
import com.contratocustodia.Repositories.ContratoRepository;
import com.contratocustodia.Services.ContratoService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ContratoServiceTest {

    @Mock
    private ContratoRepository contratoRepository;

    @InjectMocks
    private ContratoService contratoService;

    @Test
    public void testNovoContrato() {
        // Setup
        Contrato contrato = new Contrato();
        contrato.setDataContrato(LocalDate.now());
        contrato.setSimulacaoId(1L);

        when(contratoRepository.save(any(Contrato.class))).thenReturn(contrato);

        // Act
        Contrato novoContrato = contratoService.novoContrato(1L);

        // Assert
        assertEquals(1L, novoContrato.getSimulacaoId());
        assertEquals(LocalDate.now(), novoContrato.getDataContrato());
        verify(contratoRepository, times(1)).save(any(Contrato.class));
    }

    @Test
    public void testContratos() {
        // Setup
        Contrato contrato1 = new Contrato();
        contrato1.setDataContrato(LocalDate.now());
        contrato1.setSimulacaoId(1L);

        Contrato contrato2 = new Contrato();
        contrato2.setDataContrato(LocalDate.now());
        contrato2.setSimulacaoId(2L);

        List<Contrato> contratos = Arrays.asList(contrato1, contrato2);

        when(contratoRepository.findAll()).thenReturn(contratos);

        List<Contrato> result = contratoService.contratos();

        assertEquals(2, result.size());
        verify(contratoRepository, times(1)).findAll();
    }
}
