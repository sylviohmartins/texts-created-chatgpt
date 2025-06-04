# Resumo dos Principais Arquivos do Projeto SQS Payment Processor

Aqui está uma visão geral dos arquivos mais importantes criados para o projeto de exemplo de processamento de pagamentos em lote com SQS e Spring Boot:

1.  **`/pom.xml`**: Arquivo de configuração do Maven. Define as dependências do projeto (Spring Boot 3.4.5, Spring Cloud AWS 3.2.0, Testcontainers, Lombok, etc.), plugins e metadados do projeto.

2.  **`/src/main/resources/application.yml`**: Arquivo principal de configuração da aplicação Spring Boot. Contém configurações para a aplicação, AWS (região, credenciais - a serem preenchidas para ambiente real), SQS (configurações do listener como tamanho do lote, timeout), nomes das filas e configurações de logging.

3.  **`/src/main/java/com/example/sqspaymentprocessor/SqsPaymentProcessorApplication.java`**: Classe principal que inicializa a aplicação Spring Boot.

4.  **`/src/main/java/com/example/sqspaymentprocessor/domain/Payment.java`**: Classe de domínio que representa um evento de pagamento. Utiliza Lombok para reduzir código boilerplate.

5.  **`/src/main/java/com/example/sqspaymentprocessor/config/SqsConfig.java`**: Classe de configuração do Spring que define os beans necessários para a integração com SQS, como o `SqsAsyncClient`, `SqsTemplate` (para envio) e a configuração da fábrica de listeners (`SqsMessageListenerContainerFactory`).

6.  **`/src/main/java/com/example/sqspaymentprocessor/producer/PaymentProducer.java`**: Componente Spring responsável por enviar mensagens (individuais ou em lote) para a fila SQS principal, utilizando o `SqsTemplate`.

7.  **`/src/main/java/com/example/sqspaymentprocessor/consumer/PaymentConsumer.java`**: Componente Spring que contém o listener SQS (`@SqsListener`). Este método é invocado automaticamente pelo Spring Cloud AWS quando mensagens chegam na fila, recebendo-as em lote (configurado no `application.yml`) para processamento.

8.  **`/src/test/resources/application-test.yml`**: Arquivo de configuração específico para testes. Sobrescreve propriedades do `application.yml` principal, definindo credenciais dummy e configurações otimizadas para o ambiente de teste com LocalStack.

9.  **`/src/test/java/com/example/sqspaymentprocessor/config/LocalStackConfig.java`**: Configuração de teste que utiliza Testcontainers para iniciar um container Docker do LocalStack (simulador AWS) antes da execução dos testes de integração. A anotação `@ServiceConnection` conecta automaticamente a aplicação ao SQS dentro do LocalStack.

10. **`/src/test/java/com/example/sqspaymentprocessor/SqsPaymentProcessorApplicationTests.java`**: Classe de testes de integração. Utiliza JUnit 5, Spring Boot Test, Testcontainers e Awaitility para testar o fluxo completo de envio e recebimento de mensagens em lote, interagindo com o LocalStack.

Este conjunto de arquivos forma a base para uma aplicação robusta e performática de processamento em lote com SQS.
