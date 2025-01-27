# Como Rodar o Projeto

Siga os passos abaixo para configurar, executar e testar o projeto.

## Configuração Inicial

1. Renomeie o arquivo `.env.example` como `.env`.
2. Preencha as variáveis de ambiente no arquivo `.env` de acordo com a sua configuração.

## Executando o Projeto

Para iniciar o projeto, execute o seguinte comando na raiz do repositório:

```bash
docker compose up
```

## Executando os Testes

Você pode rodar os testes de cada serviço individualmente, seguindo estas instruções:

### Quarkus Service

```bash
cd quarkus-service
quarkus test
```

### Spring Boot Service

```bash
cd spring-boot-service
mvn test
```

## Recriando as Imagens (opcional)

### Quarkus Service

```bash
cd spring-boot-service
mvn clean install
docker build -f Dockerfile -t matehusu/order-crud .
```

### Spring Boot Service

```bash
cd quarkus-service
mvn clean install
quarkus image
```
