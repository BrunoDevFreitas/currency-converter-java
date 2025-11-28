title: "Currency Converter Java"
description: |
  Um projeto simples e direto em Java, criado para treinar consumo de APIs externas,
  manipulação de JSON e boas práticas de estrutura de código. Aqui eu desenvolvi
  um conversor de moedas usando a ExchangeRate API, capaz de converter entre seis
  das principais moedas do mundo.
technologies:
  - Java 11+
  - HttpClient (Java)
  - ExchangeRate API
  - Regex para leitura do JSON
  - Git + GitHub
  - IntelliJ IDEA
currencies:
  USD: "Dólar Americano"
  EUR: "Euro"
  GBP: "Libra Esterlina"
  JPY: "Iene Japonês"
  BRL: "Real Brasileiro"
  CAD: "Dólar Canadense"
structure: |
  src/
    main/
      java/
        CurrencyConverter.java
features:
  - Lista as 6 moedas disponíveis
  - Permite escolher moeda de origem e destino
  - Permite digitar qualquer valor para conversão
  - Faz requisições usando o endpoint /pair da ExchangeRate API
  - Retorna o valor convertido formatado
  - Permite realizar múltiplas conversões em sequência
run_instructions: |
  1. Clone o repositório:
     git clone https://github.com/BrunoDevFreitas/currency-converter-java.git

  2. Abra o projeto no IntelliJ IDEA.

  3. Execute o arquivo CurrencyConverter.java.

  4. Certifique-se de que o Java 11+ está configurado como SDK do projeto.
api_example: |
  Exemplo de endpoint utilizado:
  https://v6.exchangerate-api.com/v6/SUA_KEY/pair/USD/BRL/100
console_example: |
  ====================================
        Conversor de Moedas (API)
  ====================================

  Escolha a moeda de ORIGEM:
  1 - Dólar Americano (USD)
  2 - Euro (EUR)
  5 - Real Brasileiro (BRL)
  ...

  Digite o valor a ser convertido em USD:
  100

  Resultado: 100 USD = 572.43 BRL
learning_goals:
  - Consumo de APIs externas
  - Manipulação de JSON
  - Estruturação e organização de código em Java
  - Versionamento e publicação no GitHub
contribution: "Sugestões e melhorias são bem-vindas."
contact:
  linkedin: "https://www.linkedin.com/in/bruno-pires-freitas"
  github: "https://github.com/BrunoDevFreitas"
