
# Ambiente Local de Integração com Kafka, SQS, SNS e DynamoDB

Este projeto oferece uma infraestrutura local, baseada em Docker, para testes manuais com múltiplos serviços frequentemente usados em aplicações modernas. Ideal para desenvolvedores(as) que estão aprendendo ou iniciando no desenvolvimento de microsserviços com mensageria e persistência.

## O que este projeto oferece?

- Kafka Broker (mensageria)
- Kafka UI (interface gráfica para testes com Kafka)
- Kafka Connect (integração de dados)
- Schema Registry (gerência de schemas para mensagens Kafka)
- LocalStack com SQS, SNS e DynamoDB (simulando AWS localmente)
- Inicialização simples com Docker Compose

## Pré-requisitos

- Docker
- Git
- Git Bash (Windows)

## Como executar

1. Clone o projeto:
```bash
git clone <url-do-repo>
cd testcontainers-docker
```

2. Suba os serviços:
```bash
docker-compose -f docker-compose.full.yml up -d
```

3. Acesse os painéis:
- Kafka UI: [http://localhost:8080](http://localhost:8080)
- Schema Registry: `http://localhost:8085`
- Kafka Connect: `http://localhost:8083`
- LocalStack (CLI via AWS CLI): `http://localhost:4566`

4. Para resetar:
```bash
docker-compose -f docker-compose.full.yml down -v --remove-orphans
```

## Para que serve cada serviço?

### Kafka Broker
É o serviço principal de mensageria. Usado para transmitir eventos entre serviços de forma assíncrona.

**Exemplo real:** Um sistema de habilitação envia um evento "habilitação finalizada" que deve ser processado por diversos sistemas (notificações, banco de dados, auditoria).

### Kafka UI
Interface web para criar tópicos, publicar mensagens, e visualizar o que está sendo enviado e consumido.

### Kafka Connect
Permite conectar o Kafka com bancos de dados, S3, Elasticsearch, etc. Ele envia ou consome dados automaticamente de fontes externas.

**Exemplo real:** Você quer salvar automaticamente no PostgreSQL tudo que chegar em um tópico Kafka, sem escrever código Java.

### Schema Registry
Gerencia schemas (Avro, JSON, etc.) para garantir que as mensagens publicadas tenham o formato correto.

**Exemplo real:** Se um serviço espera receber `{ id: string, data: date }`, o Schema Registry garante que alguém não envie `{ nome: string }`.

### LocalStack (SQS, SNS, DynamoDB)
Simula os serviços da AWS para testes locais.

| Serviço     | Finalidade                                                                  |
|-------------|------------------------------------------------------------------------------|
| **SQS**     | Fila de mensagens. Kafka → processamento → SQS.                             |
| **SNS**     | Canal de publicação (fan-out). Ideal para eventos que precisam de "avisar vários". |
| **DynamoDB**| Banco NoSQL. Pode ser usado para guardar o estado da mensagem ou auditoria. |

## Exemplo de fluxo completo

1. Você publica um evento no tópico `efetivacao` via Kafka UI.
2. Um serviço consome esse evento, valida e salva no DynamoDB.
3. Em caso de sucesso, publica no SNS um evento "processado".
4. Esse evento chega em uma fila SQS, que será consumida por outro microserviço.

## Inicialização manual de recursos no LocalStack

```bash
# SQS FIFO
awslocal sqs create-queue --queue-name fila-processamento.fifo --attributes FifoQueue=true,ContentBasedDeduplication=true

# SNS
awslocal sns create-topic --name evento-processado

# DynamoDB
awslocal dynamodb create-table \
  --table-name eventos-processados \
  --attribute-definitions AttributeName=id,AttributeType=S \
  --key-schema AttributeName=id,KeyType=HASH \
  --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5
```

## Boas práticas para seu aprendizado

- Sempre nomeie seus tópicos e filas com propósito claro: `efetivacao`, `notificacao-envio`, etc.
- Teste mensagens com formatos válidos e inválidos.
- Acompanhe o fluxo visualmente com o Kafka UI.
- Use logs para validar falhas e acertos no processamento.

## Arquivos incluídos

- `docker-compose.full.yml`: infraestrutura completa
- `.env`: variáveis de ambiente para LocalStack
- `update_run.sh`: script de inicialização customizável
- `data/message.json`: exemplo de payload para testes
