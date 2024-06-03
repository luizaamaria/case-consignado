# Case Consignado

## Descrição do Projeto

Este projeto é uma aplicação de simulação de empréstimos consignados. Ele é composto por três serviços principais:

1. **Serviço de Clientes**: Gerencia informações sobre os clientes.
2. **Serviço de Simulação de Consignado**: Realiza simulações de empréstimos com base nos dados dos clientes.
3. **Serviço de Contratos**: Gerencia a criação e armazenamento de contratos de empréstimos.

## Requisitos

- Java 11+ (nesse projeto foi utilizado o Java 17)
- Maven 3.6.3+
- H2 Database

## Estrutura do Projeto

O projeto está estruturado da seguinte forma:

- `clientes-service`: Serviço de Clientes.
- `simulacao-service`: Serviço de Simulação de Consignado.
- `contrato-service`: Serviço de Contratos.

Cada serviço é uma aplicação Spring Boot independente.

## Configuração

### Configuração do Banco de Dados

Todos os serviços utilizam o banco de dados H2 para armazenamento. A configuração do banco de dados está definida no arquivo `application.properties` de cada serviço.

Exemplo de `application.properties`:

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```

## Executando os Serviços

1. **Clone o repositório**

```sh

git clone <URL do repositório>
cd <diretório do projeto>

```

2. **Compile e instale as dependências:**

```sh
mvn clean install
```
3. **Inicie cada serviço:**

#### Vá até o diretório de cada serviço e execute:

```sh
mvn spring-boot:run
```

Certifique-se de iniciar os serviços na seguinte ordem:

    clientes
    simulacao-consignado
    contrato-custodia

Testes

Para executar os testes unitários, utilize o comando:

```sh
mvn test
```

## Endpoints
Serviço de Clientes

    GET /cliente/{cpf}: Retorna as informações do cliente pelo CPF.
    GET /clientes: Retorna todos os clientes.
    POST /cliente: Cria um novo cliente.

Serviço de Simulação de Consignado

    POST /calcular-consignado: Realiza o calculo do consignado.
    GET /simulacoes: Retorna todas as simulações.

Serviço de Contratos

    POST /contrato-custodia: Cria um novo contrato.
    GET /contratos-custodia: Retorna todos os contratos.

## Exemplo de Uso
### Criando um Cliente

```sh

curl -X POST http://localhost:8080/cliente -H "Content-Type: application/json" -d '{
    "cpf": "12312312312",
    "nome": "Michael Jackson",
    "correntista": true,
    "segmento": "Varejo",
    "convenio": "EP"
}'
```
### Realizando uma Simulação

```sh

curl -X POST http://localhost:8081/calcular-consignado -H "Content-Type: application/json" -d '{
  "cpf": "12345678900",
  "valorSolicitado": 5000,
  "quantidadeParcelas": 5
}'
```

### Criando um Contrato

```sh

curl -X POST http://localhost:8082/contrato -H "Content-Type: application/json" -d '{
  "simulacaoId": 1
}'
```

## Controle de Versão da API

Para controlar a versão da API, você pode adicionar um prefixo de versão nos endpoints. Por exemplo:

```java
@RequestMapping("/api/v1/cliente")
public class ClienteController {
    // Endpoints
}
```