package com.example.simulacaoconsignado.Services;

import com.example.simulacaoconsignado.DTOs.SimulacaoRequestBodyDTO;
import com.example.simulacaoconsignado.Entites.Cliente;
import com.example.simulacaoconsignado.Entites.SimulacaoConsignado;
import com.example.simulacaoconsignado.Exceptions.RequestApiException;
import com.example.simulacaoconsignado.Exceptions.UnknownApiException;
import com.example.simulacaoconsignado.Repository.SimulacaoConsignadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@Service
public class SimulacaoConsignadoService {

    // propriedade = atributo
    @Autowired
    private SimulacaoConsignadoRepository simulacaoConsignadoRepository;

    @Autowired
    private RestTemplate restTemplate;

    public SimulacaoConsignadoService(
            SimulacaoConsignadoRepository simulacaoConsignadoRepository,
            RestTemplate                  restTemplate
    ) {
        this.simulacaoConsignadoRepository = simulacaoConsignadoRepository;
        this.restTemplate = restTemplate;
    }

    public SimulacaoConsignado calcularSimulacao(SimulacaoRequestBodyDTO simulacaoRequestBodyDTO) {
        String urlCliente = "http://localhost:8080/cliente/" + simulacaoRequestBodyDTO.getCpf();
        String urlContrato = "http://localhost:8082/contrato-custodia";
        // variavel porque é dentro do método

        // Faz uma chamada GET para a URL especificada e recebe a resposta em um ResponseEntity,
        // onde o corpo da resposta é convertido para um objeto do tipo Cliente.
        // busca nessa url e retorna um cliente
        ResponseEntity<Cliente> response = restTemplate.getForEntity(urlCliente, Cliente.class);
        // Extrai o objeto Cliente do corpo da resposta do ResponseEntity para acessar os dados recebidos da chamada.
        Cliente cliente = response.getBody();

        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new RequestApiException("Cliente não encontrado.");
        }

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new UnknownApiException("Erro desconhecido.");
        }

        // valor solicitado de 5000 em 5 meses, sendo um cliente correntista
        // do convenio de empresa privada e do segmento de varejo

        // 5 maior 24 -- 24 pois é do segmento varejo
        if (simulacaoRequestBodyDTO.getQuantidadeParcelas() > obterLimiteParcelas(cliente)) {
            throw new RuntimeException("Quantidade de parcelas excede o limite permitido para o segmento do cliente");
        }

        double taxa = calcularTaxa(cliente); // 0.026 * 0.95 -- 2.6%(0.026) pois o convenio é EP e 5%(0.95) de desconto pois é correntista
        double valorTotal = simulacaoRequestBodyDTO.getValorSolicitado() + (simulacaoRequestBodyDTO.getValorSolicitado() * taxa * simulacaoRequestBodyDTO.getQuantidadeParcelas()); //5000 + (5000 * (0.026 * 0.95) * 5) = 5617.5
        double valorParcela = valorTotal / simulacaoRequestBodyDTO.getQuantidadeParcelas(); // 5617.5 / 5 = 1123.5

        String taxaFormata = String.format("%.2f", taxa * 100) + "%";

        // criando um novo objeto de simulação de Consignado com base nos dados acima + o cliente
        SimulacaoConsignado simulacao = new SimulacaoConsignado();
        simulacao.setDataSimulacao(LocalDate.now());
        simulacao.setCpf(simulacaoRequestBodyDTO.getCpf());
        simulacao.setConvenio(cliente.getConvenio());
        simulacao.setValorSolicitado(simulacaoRequestBodyDTO.getValorSolicitado());
        simulacao.setQuantidadeParcelas(simulacaoRequestBodyDTO.getQuantidadeParcelas());
        simulacao.setTaxaJuros(taxa);
        simulacao.setTaxaJurosFormatado( taxaFormata );
        simulacao.setValorTotal(valorTotal);
        simulacao.setValorParcela(valorParcela);

        // salva e vira um registro no banco
        SimulacaoConsignado simulacaoConsignado = simulacaoConsignadoRepository.save(simulacao);

        restTemplate.postForObject(urlContrato, simulacaoConsignado.getId(), Void.class);

        return simulacaoConsignado;
    }

    private double calcularTaxa(Cliente cliente) {
        double taxa = switch (cliente.getConvenio()) {
            case "EP" -> 0.026;
            case "OP" -> 0.022;
            case "INSS" -> 0.016;
            default -> throw new RuntimeException("Convênio desconhecido");
        };
//        double taxa;
//        switch (cliente.getConvenio()) {
//            case "EP":
//                taxa = 0.026;
//                break;
//            case "OP":
//                taxa = 0.022;
//                break;
//            case "INSS":
//                taxa = 0.016;
//                break;
//            default:
//                throw new RuntimeException("Convênio desconhecido");
//        }

        if (cliente.isCorrentista()) {
            taxa *= 0.95;
        }

        return taxa;
    }

    private int obterLimiteParcelas(Cliente cliente) {
        if (!cliente.isCorrentista()) {
            return 12;
        }
        return switch (cliente.getSegmento()) {
            case "Varejo" -> 24;
            case "Uniclass" -> 36;
            case "Person" -> 48;
            default -> throw new RuntimeException("Segmento desconhecido");
        };

//        switch (cliente.getSegmento()) {
//            case "Varejo":
//                return 24;
//            case "Uniclass":
//                return 36;
//            case "Person":
//                return 48;
//            default:
//                throw new RuntimeException("Segmento desconhecido");
//        }
    }
    public List<SimulacaoConsignado> listarTodasSimulacoes() {
        return simulacaoConsignadoRepository.findAll();
    }
}
