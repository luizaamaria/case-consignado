# Case Consignado

## Descrição do Projeto

Este projeto é uma aplicação de simulação de empréstimos consignados. Ele é composto por três serviços principais:

1. **Serviço de Clientes**: Gerencia informações sobre os clientes.
2. **Serviço de Simulação de Consignado**: Realiza simulações de empréstimos com base nos dados dos clientes.
3. **Serviço de Contratos**: Gerencia a criação e armazenamento de contratos de empréstimos.

## Requisitos

- Java 17 (utilizei o OpenJDK 17)
- Maven 3.6.3+
- H2 Database

## Instalaçao no Linux

Atualize o sistema:

```sh
sudo apt update
sudo apt upgrade
```

Adicione o repositório do JDK 17
    
```sh
sudo add-apt-repository ppa:linuxuprising/java
```

Instale o JDK 17

```sh
sudo apt update
sudo apt install oracle-java17-installer --install-recommends
```

Verifique a versão do Java

```sh
java -version
```

Instale o Maven:

```sh
sudo apt install maven
```

## Instalaçao no Windows
[
### Instale o JDK 17:

    Baixe e instale o JDK 17 do site oficial da Oracle ou utilize um instalador como o AdoptOpenJDK.
    Após a instalação, configure a variável de ambiente JAVA_HOME apontando para o diretório de instalação do JDK.

### Instale o Maven:

    Baixe e instale o Maven 3.6.3+ do site oficial do Maven.
    Configure a variável de ambiente M2_HOME apontando para o diretório de instalação do Maven e adicione %M2_HOME%\bin ao PATH.

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

curl -X GET http://localhost:8082/contratos-custodia -H "Content-Type: application/json" -d '{
  status: '200 OK',
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