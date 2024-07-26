# Cálculo de Saving Plans para Migração de Intel para AMD no AWS

## Passos para Calcular Saving Plans de uma Migração Intel para AMD

### 1. Identificar o Uso Atual de EC2
- **Ferramenta**: AWS Cost Explorer
- **Passo**: Navegue até **Cost Management** > **Cost Explorer** no console da AWS.
- **Objetivo**: Obter uma visão clara do uso atual de instâncias EC2 com processadores Intel.

### 2. Estimar o Uso Equivalente em AMD
- **Ferramenta**: AWS Instance Types
- **Passo**: Determine os tipos de instâncias AMD equivalentes às suas instâncias Intel.
  - Exemplo: `m5.large` (Intel) é equivalente a `m5a.large` (AMD).

### 3. Simular o Custo de Uso de Instâncias AMD
- **Ferramenta**: AWS Cost Explorer
- **Passo**: No Cost Explorer, crie um relatório personalizado e adicione as instâncias AMD equivalentes à análise de custo.
- **Objetivo**: Comparar os custos das instâncias Intel atuais com os custos projetados das instâncias AMD.

### 4. Calcular Saving Plans
- **Ferramenta**: AWS Saving Plans Recommendations
- **Passo**: Navegue até **Cost Management** > **Saving Plans** > **Recommendations** no console da AWS.
- **Objetivo**: Obter recomendações personalizadas de Saving Plans com base no uso projetado de instâncias AMD.

### 5. Comparação de Custo Total
- **Ferramenta**: AWS Cost Explorer
- **Passo**: Compare o custo total projetado das instâncias Intel atuais versus instâncias AMD, incluindo os descontos dos Saving Plans recomendados.
- **Objetivo**: Determinar as economias potenciais da migração.

## Ferramentas e Recursos

### AWS Cost Explorer
- **Acesso**: Console da AWS > **Cost Management** > **Cost Explorer**
- **Funções**: Visualizar e analisar o uso e custo de instâncias EC2, criar relatórios personalizados, simular custos com diferentes tipos de instâncias.

### AWS Pricing Calculator
- **Acesso**: [AWS Pricing Calculator](https://calculator.aws/#/)
- **Funções**: Criar estimativas detalhadas de custos para diferentes tipos de instâncias e configurações, incluindo instâncias AMD.

### AWS Saving Plans Recommendations
- **Acesso**: Console da AWS > **Cost Management** > **Saving Plans** > **Recommendations**
- **Funções**: Obter recomendações de Saving Plans com base no uso histórico e simulado, comparar economias potenciais com diferentes tipos de Saving Plans.

## Exemplo Prático

### 1. Acessar AWS Cost Explorer
- No console da AWS, navegue até **Cost Management** > **Cost Explorer**.
- Crie um relatório para visualizar o uso atual de instâncias Intel.

### 2. Simular Uso de AMD
- No Cost Explorer, adicione instâncias AMD equivalentes.
  - Exemplo: Se você estiver usando `m5.large`, adicione `m5a.large`.

### 3. Ver Recomendações de Saving Plans
- Navegue até **Cost Management** > **Saving Plans** > **Recommendations**.
- Veja as recomendações de Saving Plans baseadas no uso simulado de instâncias AMD.

### 4. Comparar Custos
- Compare os custos totais de uso de instâncias Intel com e sem Saving Plans.
- Compare os custos totais de uso de instâncias AMD com e sem Saving Plans.

## Resumo

Para calcular as economias potenciais ao migrar de processadores Intel para AMD usando Saving Plans:

1. **Use o AWS Cost Explorer** para visualizar e simular custos.
2. **Utilize o AWS Pricing Calculator** para estimar custos detalhados.
3. **Acesse as recomendações de Saving Plans** no console da AWS para obter uma análise de economia personalizada.
4. **Compare os custos totais** e economias projetadas para tomar uma decisão informada.

Seguindo essas etapas e utilizando as ferramentas apropriadas, você pode calcular de forma eficaz as economias potenciais ao migrar para instâncias AMD em seu ambiente de desenvolvimento, aproveitando ao máximo os AWS Saving Plans.
