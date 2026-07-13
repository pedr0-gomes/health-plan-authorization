# health-plan-authorization

Sistema de autorização de procedimentos por plano de saúde, implementado em Java como projeto acadêmico de Programação Orientada a Objetos.

O objetivo real foi aprender a construir — modelagem, engenharia de software e Java — não entregar um sistema pronto.

## O que você vai encontrar

O código carrega as decisões de design que sustentam os posts desta série. As principais:

- **Strategy** (`PoliticaCoparticipacao`) — polimorfismo substituindo condicional; a regra de cálculo da coparticipação vive fora da classe que a usa.
- **Herança com propósito** (`Segmentacao`) — classe abstrata não como rascunho, mas como buraco deliberado onde o polimorfismo entra; interface (`PoliticaCoparticipacao`) como padrão, abstrata como exceção que paga herança única.
- **Exceções de domínio** — `BeneficiarioNaoEncontradoException`, `DadosInvalidosException`: erros que pertencem ao modelo, não à infraestrutura.
- **Composition root** (`Main`) — ponto único onde os objetos são montados e as dependências injetadas.

## Como rodar

Pré-requisito: Java 17+ e Maven.

```bash
mvn exec:java
```

O menu CLI semeia cadastros de exemplo e permite autorizar procedimentos fim-a-fim.

Para rodar os testes:

```bash
mvn test
```

## Posts

Série publicada a partir deste projeto:

- [Parei de ver objeto como struct](URL_POST_1) — a virada de procedural para OO na prática
- [A classe abstrata não é a peça principal — é a exceção cara](URL_POST_4) — método abstrato, polimorfismo e o discriminador abstrata vs. interface
