# Etapa 1 — Proposta do Sistema

**Disciplina:** Programação Orientada a Objetos (Java)
**Sistema:** Autorização de procedimentos por plano de saúde

> Documento da Etapa 1 (definição e apresentação do sistema), sujeito a validação
> e aprovação do professor. Detalha o objetivo, o cenário e as principais
> funcionalidades a serem implementadas na Etapa 2.

---

## 1. Objetivo

Desenvolver uma aplicação Java de linha de comando que **simula a decisão de
autorização de um procedimento de saúde por uma operadora de plano**, aplicando as
regras de negócio que regem essa decisão na prática brasileira (regulação da ANS,
Lei 9.656/98).

Dado um beneficiário, um procedimento e o contexto do atendimento, o sistema decide
se o procedimento é **autorizado**, **autorizado com coparticipação** ou **negado** —
e, quando negado, informa o motivo específico.

O domínio foi escolhido porque concentra **regras reais com variação comportamental
honesta** (planos diferentes cobrem coisas diferentes, calculam custos por fórmulas
diferentes), o que o torna um terreno natural para exercitar os conceitos de
orientação a objetos exigidos na Etapa 2: encapsulamento, herança, polimorfismo,
classes abstratas, interfaces, collections e tratamento de exceções.

---

## 2. Cenário detalhado

### 2.1. O problema do mundo real

No Brasil, planos de saúde são regulados pela Agência Nacional de Saúde Suplementar
(ANS). Quando um beneficiário precisa realizar um procedimento — uma consulta, um
exame, uma cirurgia, uma internação ou um parto —, a operadora não autoriza
automaticamente: ela avalia um conjunto de regras antes de liberar (ou negar) o
atendimento e antes de definir quanto o beneficiário paga por ele.

Essa avaliação é o coração do sistema. Ela depende de três informações:

- **O beneficiário** — em especial, há quanto tempo ele aderiu ao plano.
- **O plano** ao qual ele está vinculado — sua segmentação assistencial, seus prazos
  de carência e sua política de coparticipação.
- **O procedimento** e o **contexto do atendimento** — tipo do procedimento, data do
  evento, se é urgência/emergência e se há autorização prévia concedida.

### 2.2. As quatro regras de autorização

A decisão de autorizar é a composição de quatro regras, aplicadas em ordem. As três
primeiras podem **negar** o atendimento; a quarta define o **custo** quando ele é
autorizado.

| Ordem | Regra | Pergunta que responde | Resultado se falha |
|-------|-------|------------------------|--------------------|
| 1 | **Cobertura / Segmentação** | A segmentação do plano cobre este tipo de procedimento? | Negado por cobertura |
| 2 | **Carência** | Já decorreu o prazo de carência aplicável entre a adesão e o evento? | Negado por carência |
| 3 | **Autorização prévia** | Se o procedimento exige autorização prévia, ela foi concedida? | Negado por falta de autorização prévia |
| 4 | **Coparticipação** | Quanto o beneficiário paga por este procedimento? | (não nega — calcula o valor) |

**Segmentação assistencial** define o escopo de cobertura do plano. O sistema
contempla quatro segmentações, cada uma cobrindo um conjunto diferente de tipos de
procedimento:

- *Ambulatorial* — cobre consultas e exames.
- *Hospitalar sem obstetrícia* — cobre tudo, exceto parto.
- *Hospitalar com obstetrícia* — cobre tudo, incluindo parto.
- *Referência* — cobertura ampla (equivalente à hospitalar com obstetrícia no recorte
  deste sistema).

**Carência** é o período após a adesão em que o beneficiário ainda não pode usar
certas coberturas. O sistema usa três prazos parametrizados por plano: **urgência /
emergência (1 dia)**, **parto (300 dias)** e **demais situações** — consultas,
exames, cirurgias e internações — **(180 dias)**.

**Autorização prévia** é a análise administrativa que alguns procedimentos exigem
antes de serem liberados (tipicamente os de maior complexidade). No sistema, é
representada como uma exigência do procedimento que precisa estar satisfeita no
contexto do atendimento.

**Coparticipação** é o valor que o beneficiário paga a cada uso do plano, além da
mensalidade. O sistema contempla três políticas: sem coparticipação, percentual sobre
o valor do procedimento, e valor fixo por tipo de procedimento.

### 2.3. Atores

- **Usuário** — opera o sistema pelo terminal: consulta os cadastros e solicita
  autorizações.
- **Sistema** — reage às solicitações aplicando as regras de negócio e devolvendo a
  decisão.

### 2.4. Modo de operação

A aplicação carrega, na inicialização, um conjunto de cadastros pré-inseridos
(planos, beneficiários e procedimentos) e apresenta um menu textual em loop. Não há
cadastro interativo: o foco é a lógica de autorização, não a entrada de dados.

### 2.5. Recorte do domínio

Para manter o foco no aprendizado de orientação a objetos, o sistema simplifica
conscientemente o domínio real. Ficam **fora do escopo**: cobertura parcial temporária
(doenças preexistentes), diretrizes clínicas de utilização (DUT), rede credenciada e
área de abrangência geográfica, tetos de coparticipação, prazos de resposta da
operadora e persistência em banco de dados. Essas simplificações preservam a regra
essencial de cada eixo sem inflar a modelagem com estado temporal ou regras clínicas.

---

## 3. Principais funcionalidades

| # | Funcionalidade | Descrição |
|----|----------------|-----------|
| F1 | **Listar planos** | Exibe os planos cadastrados, com sua segmentação e política de coparticipação. |
| F2 | **Listar beneficiários** | Exibe os beneficiários cadastrados, com o plano ao qual cada um está vinculado e sua data de adesão. |
| F3 | **Listar procedimentos** | Exibe o catálogo de procedimentos, com tipo, valor-base e se exigem autorização prévia. |
| F4 | **Solicitar autorização de procedimento** | Recebe um beneficiário, um procedimento e o contexto do atendimento (data do evento, urgência, autorização prévia concedida), aplica as quatro regras em ordem e devolve a decisão. |
| F5 | **Apresentar o resultado da decisão** | Exibe se o procedimento foi autorizado, autorizado com coparticipação (com o valor) ou negado (com o motivo específico). |
| F6 | **Sinalizar entradas inválidas** | Informa ao usuário, de forma clara e sem encerrar o programa, quando um beneficiário, plano ou procedimento é referenciado por um código inexistente. |

> Versão visual editável desta tabela: `Etapa1-funcionalidades.excalidraw` (abrir em
> [excalidraw.com](https://excalidraw.com) → menu → *Open*). Cor com semântica:
> consultas (F1–F3), núcleo de decisão (F4), saída (F5), tratamento de erro (F6).

---

## 4. Resultado esperado da demonstração

Ao final da Etapa 2, o sistema será demonstrável de ponta a ponta: a partir dos
cadastros pré-inseridos, o usuário solicita autorizações em diferentes cenários
(coberto e não coberto, dentro e fora da carência, com e sem autorização prévia, com
políticas de coparticipação distintas) e observa cada decisão com seu motivo ou custo.
