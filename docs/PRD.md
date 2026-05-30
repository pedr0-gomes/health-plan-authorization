# PRD — Sistema de Autorização de Plano de Saúde

> Documento mestre. Consolida as decisões tomadas nas sessões de grill (`/grill-me` sobre a pesquisa de domínio + `/grill-me` sobre as etapas do trabalho).
> Substitui `DECISOES.md`, que fica como histórico até descarte explícito.

---

## 1. Contexto e objetivo

Trabalho acadêmico de Programação Orientada a Objetos em Java. Conforme `Etapas.md`:

- **Etapa 1** — definir e apresentar o sistema (objetivo, cenário, funcionalidades). Sujeito a aprovação do professor.
- **Etapa 2** — modelagem UML (caso de uso + classe) + implementação que aborde **encapsulamento, construtores, herança, polimorfismo (sobrecarga e sobrescrita), classes abstratas, interface, arrays/collections, tratamento de exceções**.
- **Etapa 3** — documentação completa + apresentação prática com cadastros previamente inseridos.

**Domínio escolhido:** autorização de procedimentos por plano de saúde, conforme regras da ANS (Lei 9.656/98 e regulamentações posteriores). Fonte de domínio: `# Pesquisa_ Plano de Saúde Brasileiro como Domínio.md`.

**Objetivo de aprendizado (Pedro):** exercitar modelagem OO num domínio com regras reais e variação comportamental honesta, construindo o sistema do zero — sem que o assistente escreva código de modelagem ou lógica de negócio.

**Critério de sucesso do projeto:** ao fim, o sistema cumpre o rubric da Etapa 2 (todos os conceitos OO aparecem em lugares naturais), tem cobertura de testes para as regras de domínio, e é demonstrável via CLI com cadastros pré-inseridos.

---

## 2. Solução proposta

Aplicação Java de linha de comando que:

- Carrega na inicialização (`main`) um conjunto de cadastros pré-inseridos: planos, beneficiários, procedimentos.
- Apresenta um menu textual em loop com as opções: listar planos / listar beneficiários / listar procedimentos / solicitar autorização / sair.
- Na opção "solicitar autorização", recebe identificadores de beneficiário e procedimento + dados contextuais (data do evento, urgência, autorização prévia concedida), aplica as 4 regras de autorização em ordem, e exibe a decisão (autorizado, autorizado com coparticipação, ou negado com motivo específico).
- Sinaliza entradas inválidas via exceções customizadas capturadas na borda da aplicação.

---

## 3. Funcionalidades / Casos de uso

User stories (atores: **Usuário** humano operando o terminal; **Sistema** reagindo às regras de negócio):

1. Como **Usuário**, quero listar os planos cadastrados, para conhecer as opções disponíveis no sistema.
2. Como **Usuário**, quero listar os beneficiários cadastrados, para escolher um para a solicitação.
3. Como **Usuário**, quero listar os procedimentos cadastrados, para conhecer o catálogo.
4. Como **Usuário**, quero solicitar a autorização de um procedimento para um beneficiário em uma data específica, para saber se o atendimento é autorizado e quanto custaria.
5. Como **Sistema**, quero negar a autorização quando a segmentação do plano do beneficiário não cobrir o tipo do procedimento, para refletir a regra de cobertura/Rol da ANS.
6. Como **Sistema**, quero negar a autorização quando os dias decorridos entre a data de adesão do beneficiário e a data do evento não atingirem o prazo de carência aplicável (urgência, parto ou demais), para refletir os prazos contratuais.
7. Como **Sistema**, quero negar a autorização quando o procedimento exigir autorização prévia e ela não tiver sido concedida no contexto do atendimento, para refletir o fluxo administrativo.
8. Como **Sistema**, quero calcular o valor de coparticipação devido pelo beneficiário conforme a política do plano (sem coparticipação, percentual sobre valor-base, ou valor fixo por tipo), para informar o custo final no resultado autorizado.
9. Como **Sistema**, quero retornar um resultado rico contendo a decisão, o motivo da negativa (se houver) e o valor de coparticipação (se houver), para que a apresentação possa expor todos os detalhes ao usuário.
10. Como **Sistema**, quero lançar exceção quando o usuário referenciar um beneficiário, plano ou procedimento por código inexistente, para sinalizar pré-condição violada.
11. Como **Sistema**, quero recusar a criação de qualquer entidade com dados estruturalmente inválidos (valor-base não positivo, data nula, prazos negativos), para garantir invariantes desde o construtor.
12. Como **Usuário**, quero ver mensagem clara quando uma exceção for capturada na borda, para entender o que deu errado sem o programa terminar abruptamente.

---

## 4. Decisões de modelagem e arquitetura

### 4.1. Escopo de regras (4 eixos)

Quatro eixos de regra integram a operação de autorização, na ordem em que são aplicados:

1. **Cobertura + Segmentação** — o plano cobre este tipo de procedimento?
2. **Carência** — os dias decorridos satisfazem o prazo aplicável?
3. **Autorização prévia** — se o procedimento exige, ela foi concedida no contexto?
4. **Coparticipação** — qual valor o beneficiário deve pagar?

Cada eixo foi escolhido porque carrega naturalmente um conceito OO obrigatório do rubric (ver §6).

### 4.2. Entidades principais

- **Beneficiario** — `nome`, `dataAdesao`, `plano`. Associação 1→1 com Plano por composição direta. Operação `autorizar` recebe o Beneficiário e o plano vem por dentro (`getPlano()`).
- **Plano** — `nome`, `segmentacao`, três prazos de carência (`int`), `politicaCopart`. Expõe `cobre(p)`, `carenciaCumprida(...)`, `calcularCopart(p)` — delega às colaboradoras (Segmentação e Política).
- **Procedimento** — `codigo`, `descricao`, `tipo` (TipoProcedimento), `requerAutorizacaoPrevia`, `valorBase`. Validações no construtor.
- **TipoProcedimento** (enum) — `CONSULTA, EXAME, CIRURGIA, INTERNACAO, PARTO`.
- **ContextoAtendimento** — `dataEvento`, `urgenciaEmergencia`, `autorizacaoPreviaConcedida`. Agrupa parâmetros do atendimento concreto (em oposição a dados estruturais do Beneficiário/Procedimento). Tem **construtores sobrecarregados** para os casos comuns.
- **ResultadoAutorizacao** — `decisao`, `motivo`, `valorCoparticipacao`. Construtores sobrecarregados (autorizado com copart vs. negado com motivo).
- **Decisao** (enum) — `AUTORIZADO, NEGADO_COBERTURA, NEGADO_CARENCIA, NEGADO_AUTORIZACAO_PREVIA`.

### 4.3. Segmentação como classe abstrata

```
abstract class Segmentacao
  - nome, descricao (protected, no construtor protegido)
  - abstract cobre(Procedimento): boolean

  ├── Ambulatorial                        cobre se tipo ∈ {CONSULTA, EXAME}
  ├── HospitalarSemObstetricia            cobre se tipo ≠ PARTO
  ├── HospitalarComObstetricia            cobre tudo
  └── Referencia                          cobre tudo (idêntica a HCO no recorte; documentado)
```

**Por que abstrata e não interface:** há estado compartilhado (nome, descricao) — usar interface forçaria duplicação em cada implementação. Bloch *Effective Java* item 20.

**HCO ≡ Referência no método `cobre`:** decisão consciente. A diferença real entre as duas, na ANS, está em acomodação, urgência integral e abrangência — atributos cortados do escopo (§7). Cobertura sai idêntica no recorte; documentado como tal.

### 4.4. PoliticaCoparticipacao como interface

```
interface PoliticaCoparticipacao
  - calcular(Procedimento): double

  ├── SemCoparticipacao
  ├── CoparticipacaoPercentual         (campo: double percentual)
  └── CoparticipacaoFixaPorTipo        (campo: Map<TipoProcedimento, Double> tabela)
```

**Por que interface e não abstrata:** as implementações não compartilham estado (uma não tem nada, outra tem `percentual`, outra tem `Map`). Contrato puro, sem necessidade de herdar campos.

**Método único na interface (`calcular`):** interface representa **uma capacidade** (Bloch item 41), não uma família de utilitários. Não enriquecer com `getDescricao`, `isAplicavel`, etc.

### 4.5. Localização das regras (Tell-Don't-Ask)

- `ServicoAutorizacao.autorizar(Beneficiario, Procedimento, ContextoAtendimento)` orquestra o fluxo das 4 regras.
- `Plano.cobre(p)` delega à `segmentacao.cobre(p)`.
- `Plano.carenciaCumprida(...)` calcula com seus próprios prazos.
- `Plano.calcularCopart(p)` delega à `politicaCopart.calcular(p)`.

O serviço nunca extrai dados internos do Plano para decidir fora — sempre pergunta ao Plano. Princípio **Tell, Don't Ask** (Pragmatic Programmers, ~2003). Evita modelo anêmico (Fowler, 2003).

### 4.6. Repositórios por entidade

Três classes — `RepositorioPlano`, `RepositorioBeneficiario`, `RepositorioProcedimento` — cada uma encapsulando uma `List<>` privada. Métodos: `adicionar`, `listar`, `buscarPor*`. Sem interface compartilhada, sem generics — repetição honesta entre os três, com métodos próprios de busca por entidade (ex.: `RepositorioProcedimento.buscar(String codigo)` vs `buscar(TipoProcedimento tipo)` — sobrecarga honesta).

### 4.7. Exceções: escola Fowler

**Negativas de regra de negócio (carência, cobertura, autorização prévia) NÃO são exceções** — são valores de retorno via `ResultadoAutorizacao`. Negar autorização é fluxo normal, não erro.

**Exceções customizadas são reservadas para erros de pré-condição/lookup:**

- `BeneficiarioNaoEncontradoException`
- `ProcedimentoNaoEncontradoException`
- `PlanoNaoEncontradoException`
- `DadosInvalidosException` (validações de construtor)

Todas estendem `RuntimeException` (unchecked). Captura ocorre **apenas no `main`** (borda da aplicação) — não em cada camada intermediária. Bloch *Effective Java* items 69 e 70.

### 4.8. Camada de apresentação

Menu CLI em loop no `main`. Cadastros pré-inseridos via seed no próprio `main` (não há cadastro interativo). Opções: listar X (×3), solicitar autorização, sair. Try/catch envolve a chamada de autorização para capturar exceções de lookup e exibir mensagem ao usuário sem interromper o loop.

### 4.9. Layout do projeto e stack

| Item        | Escolha       |
|-------------|---------------|
| Build       | Maven         |
| Linguagem   | Java 21 (LTS) |
| Testes      | JUnit 5       |
| Asserts     | JUnit puros (sem AssertJ) |

```
src/
├── main/java/br/ufca/autorizacao/
│   ├── dominio/        (entidades, enums, segmentações, políticas, exceções)
│   ├── servico/        (ServicoAutorizacao)
│   ├── repositorio/    (Repositorio*)
│   └── apresentacao/   (Main, Menu)
└── test/java/br/ufca/autorizacao/
    └── ...
```

Organização **por camada arquitetural**, não por feature. Justificável para o porte do projeto (~20 classes).

---

## 5. Decisões de teste

- **Biblioteca:** JUnit 5; asserts puros.
- **Datas:** sempre fixas nos testes (`LocalDate.of(2025, 1, 1)`). Nunca `LocalDate.now()` no código de domínio ou de teste.
- **Granularidade:** testes unitários por regra (carência por tipo, cobertura por segmentação, cada política de coparticipação) + testes integrados pelo serviço.
- **Cobertura de borda:** sempre incluir cenário no **dia exato do prazo** (`dias == prazo`) para fixar a convenção `dias >= prazo → cumprida`.
- **Testes de exceção:** `assertThrows` para validações de construtor e lookup em repositório.

### Cenários canônicos de carência (cabeça da suíte)

| # | Tipo                  | Dias adesão→evento | Esperado         |
|---|-----------------------|--------------------|------------------|
| 1 | DEMAIS (ex.: CONSULTA)| 181                | AUTORIZADO       |
| 2 | DEMAIS                | 179                | NEGADO_CARENCIA  |
| 3 | DEMAIS                | 180                | AUTORIZADO (`>=`) |
| 4 | PARTO                 | 301                | AUTORIZADO       |
| 5 | PARTO                 | 299                | NEGADO_CARENCIA  |
| 6 | qualquer + urgência   | 1                  | AUTORIZADO       |
| 7 | qualquer + urgência   | 0                  | NEGADO_CARENCIA  |

---

## 6. Mapa de conceitos OO obrigatórios (rubric Etapa 2)

| Conceito do rubric          | Onde aparece no projeto                                                          | Por que aqui                                                  |
|-----------------------------|----------------------------------------------------------------------------------|---------------------------------------------------------------|
| Encapsulamento (modif. de acesso) | Todas as entidades com campos `private final`; Repositórios com `List<>` privada | Default do design; uso sério em repositórios                  |
| Construtores                | Todas as entidades; validações dentro dos construtores                           | Invariantes estabelecidas na construção                       |
| Herança                     | `Ambulatorial`, `HSO`, `HCO`, `Referencia` estendem `Segmentacao`                | Variação comportamental por subtipo                           |
| Polimorfismo (sobrescrita)  | `cobre(Procedimento)` sobrescrito em cada subclasse de `Segmentacao`             | Mesmo método, lógicas distintas                               |
| Polimorfismo (sobrecarga)   | Construtores de `ContextoAtendimento` (3 versões), `ResultadoAutorizacao` (2 versões), `RepositorioProcedimento.buscar(...)` (2 versões) | Atender casos de uso com assinaturas diferentes                |
| Classes abstratas           | `Segmentacao`                                                                    | Estado compartilhado + método obrigatoriamente sobrescrito    |
| Interface                   | `PoliticaCoparticipacao` (3 implementações)                                      | Contrato puro, sem estado herdado                             |
| Arrays e/ou Collections     | `List<Plano>`, `List<Beneficiario>`, `List<Procedimento>` (repositórios); `Map<TipoProcedimento, Double>` (CoparticipacaoFixaPorTipo) | Variedade de Collections (List + Map)                          |
| Tratamento de exceções      | Exceções customizadas (4 classes); try/catch no `main`; `assertThrows` nos testes | Erros de pré-condição vs valores de retorno (escola Fowler)   |

---

## 7. Out of Scope

| Cortado                                              | Razão                                                                                |
|------------------------------------------------------|--------------------------------------------------------------------------------------|
| CPT (Cobertura Parcial Temporária)                   | Exige modelagem temporal de 24 meses + diagnóstico ligado a DLP; outro domínio       |
| DUT (Diretrizes de Utilização) detalhadas            | Regras clínicas por procedimento; foge de modelagem OO básica                        |
| Modalidade de contratação como subtipo               | Afeta regras comerciais, não a lógica clínica de autorização                         |
| Área de abrangência geográfica                       | Atributo descritivo; não puxa polimorfismo                                            |
| Padrão de acomodação (enfermaria/apartamento)        | Idem                                                                                  |
| Rede credenciada (Prestador, Hospital)               | Adicionaria entidade sem novo conceito OO                                            |
| Histórico de utilização / tetos de coparticipação    | Exige estado financeiro agregado ao beneficiário ao longo do tempo                   |
| Entidade `PedidoAutorizacao` com máquina de estados  | Fluxo temporal; substituído por flag no `ContextoAtendimento`                        |
| Prazos de resposta da operadora (RN 259/2011)        | Estado temporal de pedido em análise; fora do recorte                                |
| Lei 14.454/2022 (procedimentos não listados)         | Exigiria equivalência terapêutica e regras clínicas                                  |
| Persistência (banco, arquivo)                        | Cadastros pré-inseridos no `main`; foco em OO, não em I/O                            |
| Cadastro interativo via menu                         | "Cadastros previamente inseridos" no enunciado; reduz código de UI                  |
| Generics (`Repositorio<T>`)                          | Não pedido pelo rubric; cada repositório tem métodos próprios — abstrair forçaria    |
| Update e Delete nos repositórios                     | Não exigido pela demo; CRUD completo é burocracia sem aprendizado novo               |

---

## 8. Roteiro de fatias e subtarefas

Construção incremental. Cada fatia mantém o sistema verde (`mvn test` passa).

### Fatia 0 — Scaffold *(execução: assistente)*

| # | Subtarefa | Pronto quando |
|---|---|---|
| 0.1 | `pom.xml` com Java 21 + JUnit 5 | `mvn -v` ok |
| 0.2 | Árvore de diretórios Maven + classes vazias | `mvn test` executa sem teste |

### Fatia 1 — Carência *(execução: Pedro)*

| # | Subtarefa | Conceito-foco | Pronto quando |
|---|---|---|---|
| 1.1 | `enum TipoProcedimento` | enum | compila |
| 1.2 | `enum Decisao` (valores iniciais) | enum | compila |
| 1.3 | `Procedimento` (sem `valorBase`, sem `requerAP` ainda) | encapsulamento + construtor | compila |
| 1.4 | `ContextoAtendimento` (só `dataEvento`, 1 construtor) | encapsulamento | compila |
| 1.5 | `ResultadoAutorizacao` (`decisao` + `motivo`, 1 construtor) | encapsulamento | compila |
| 1.6 | `Plano` (sem `segmentacao` nem `politicaCopart` ainda) | encapsulamento + construtor | compila |
| 1.7 | `Beneficiario` (com `plano`) | associação 1→1 | compila |
| 1.8 | Escrever **teste #1** da tabela de carência | TDD vermelho proposital | teste falha sem regra |
| 1.9 | Implementar `Plano.carenciaCumprida(...)` | método puro | teste #1 verde |
| 1.10 | Escrever testes #2 a #7 (bordas inclusas) | testes de borda | tudo verde |
| 1.11 | Criar `ServicoAutorizacao.autorizar(...)` delegando ao Plano | service layer + Tell-Don't-Ask | continua verde |
| 1.12 | Migrar testes para chamarem o serviço, não o Plano direto | testar pela porta certa | continua verde |

**Entrega:** sistema autoriza/nega por carência ponta a ponta.

### Fatia 2 — Cobertura + Segmentação *(Pedro)*

| # | Subtarefa | Conceito-foco |
|---|---|---|
| 2.1 | Classe abstrata `Segmentacao` (construtor protegido, método abstrato `cobre`) | classe abstrata |
| 2.2 | Subclasse `Ambulatorial` | herança + sobrescrita |
| 2.3 | Subclasse `HospitalarSemObstetricia` | herança + sobrescrita |
| 2.4 | Subclasse `HospitalarComObstetricia` | herança + sobrescrita |
| 2.5 | Subclasse `Referencia` (idêntica a HCO, documentado) | herança |
| 2.6 | Testes unitários do `cobre()` por subclasse | polimorfismo verificável |
| 2.7 | `Plano` ganha `segmentacao` e método `cobre(p)` delegando | Tell-Don't-Ask reforçado |
| 2.8 | `ServicoAutorizacao` checa cobertura **antes** de carência | ordem de regras |
| 2.9 | Ajustar testes da fatia 1 (passar segmentação que cobre tudo) | regressão controlada |
| 2.10 | Testes integrados de cobertura no serviço | |

**Entrega:** sistema também nega por procedimento não coberto.

### Fatia 3 — Autorização prévia *(Pedro)*

| # | Subtarefa | Conceito-foco |
|---|---|---|
| 3.1 | `Procedimento` ganha `requerAutorizacaoPrevia` | extensão de entidade |
| 3.2 | `ContextoAtendimento` ganha `autorizacaoPreviaConcedida` + **3 construtores sobrecarregados** | sobrecarga |
| 3.3 | `ServicoAutorizacao` adiciona checagem após carência | ordem de regras |
| 3.4 | Testes para os 2 casos novos | |

**Entrega:** sistema nega por falta de autorização prévia quando exigida.

### Fatia 4 — Coparticipação *(Pedro)*

| # | Subtarefa | Conceito-foco |
|---|---|---|
| 4.1 | `interface PoliticaCoparticipacao` | interface |
| 4.2 | `SemCoparticipacao` | implementação trivial |
| 4.3 | `CoparticipacaoPercentual` (campo `percentual`) | interface + estado próprio |
| 4.4 | `CoparticipacaoFixaPorTipo` (campo `Map<...>`) | interface + Map |
| 4.5 | `Procedimento` ganha `valorBase` | extensão |
| 4.6 | `Plano` ganha `politicaCopart` e `calcularCopart(p)` | composição + delegação |
| 4.7 | Testes unitários por política | polimorfismo verificável |
| 4.8 | `ResultadoAutorizacao` ganha `valorCoparticipacao` + **construtores sobrecarregados** | sobrecarga |
| 4.9 | `ServicoAutorizacao` calcula copart quando autoriza | fluxo completo |
| 4.10 | Atualizar `Decisao` com novos valores | |

**Entrega:** sistema retorna copart no resultado autorizado, varia conforme política do plano.

### Fatia 5 — Robustez (exceções + repositórios) *(Pedro)*

| # | Subtarefa | Conceito-foco |
|---|---|---|
| 5.1 | `DadosInvalidosException` | exceção customizada |
| 5.2 | Validações nos construtores das entidades | precondições |
| 5.3 | Testes `assertThrows` para validações | |
| 5.4 | `BeneficiarioNaoEncontradoException` | |
| 5.5 | `ProcedimentoNaoEncontradoException` | |
| 5.6 | `PlanoNaoEncontradoException` | |
| 5.7 | `RepositorioPlano` (List privada, métodos adicionar/listar/buscar) | encapsulamento sério + Collections |
| 5.8 | `RepositorioBeneficiario` | idem |
| 5.9 | `RepositorioProcedimento` com **sobrecarga** `buscar(String)` e `buscar(TipoProcedimento)` | sobrecarga 2ª vez |
| 5.10 | Testes dos 3 repositórios (lookup ok e falho) | |

**Entrega:** sistema sinaliza entradas inválidas de forma estruturada.

### Fatia 6 — Apresentação *(Pedro)*

| # | Subtarefa | Conceito-foco |
|---|---|---|
| 6.1 | Classe `Main` esqueleto | ponto de entrada |
| 6.2 | Seed de procedimentos (~10, cobrindo todos os tipos) | dados de demo |
| 6.3 | Seed de planos (~3 com segmentações e políticas distintas) | dados de demo |
| 6.4 | Seed de beneficiários (~5 com datas de adesão variadas) | dados de demo |
| 6.5 | Loop do menu com 5 opções | I/O básico |
| 6.6 | Opção "listar X" para cada repositório | uso de `listar()` |
| 6.7 | Opção "solicitar autorização" — input, chamada ao serviço, exibição | fluxo completo |
| 6.8 | `try/catch` no loop para exceções de lookup | tratamento de exceções na borda |
| 6.9 | Polimento (formatação de moeda, datas legíveis) | |

**Entrega:** sistema demonstrável fim-a-fim com cadastros pré-inseridos.

**Contagem total:** Fatia 0 (2) + 1 (12) + 2 (10) + 3 (4) + 4 (10) + 5 (10) + 6 (9) = **~57 subtarefas**.

---

## 9. Notas adicionais

### Pegadas didáticas a lembrar durante a implementação

- **Datas sempre por parâmetro**, nunca `LocalDate.now()` no domínio.
- **Construtores `protected` em `Segmentacao`** — nunca instanciada diretamente.
- **Método abstrato `cobre` sem corpo**; cada subclasse decide do zero (sem `super.cobre(...)`).
- **Ordem das checagens no serviço importa** — cobertura → carência → autorização prévia → coparticipação. Quando entrar a fatia 2, testes da fatia 1 podem precisar passar segmentação "cobre tudo" para isolar o aspecto carência.
- **Captura de exceção SÓ no `main`** — não em cada camada. Anti-padrão capturar para re-lançar.
- **Validação no construtor lança** `DadosInvalidosException`. Estado inválido não constrói.

### Pendências em aberto

- **UML** — diagrama de caso de uso e de classes pendentes. Produção é do Claude
  (ver `COLABORACAO.md §2`): caso de uso e doc da Etapa 3 ele rascunha sozinho; o de
  classes no modo (b) — ele rascunha do §4, Pedro corrige e aprende. Revisar contra
  este PRD antes da Etapa 2.
- **Confirmar com o professor:** a interpretação "cadastros previamente inseridos = seed no `main`, sem UI de cadastro" precisa ser validada para evitar surpresa na apresentação.

### Conceitos formais nomeados (referências)

- **Tell, Don't Ask** — *The Pragmatic Programmers* (Hunt & Thomas), c. 2003.
- **Anemic Domain Model** — Martin Fowler, [martinfowler.com/bliki/AnemicDomainModel.html](https://martinfowler.com/bliki/AnemicDomainModel.html), 2003.
- **Service Layer** — Fowler, *Patterns of Enterprise Application Architecture*, 2002.
- **Prefer interfaces to abstract classes (e as exceções)** — Joshua Bloch, *Effective Java*, item 20.
- **Use interfaces only to define types** — Bloch, item 41.
- **Use exceptions only for exceptional conditions** — Bloch, item 69.
- **Prefer runtime exceptions for programming errors** — Bloch, item 70.
- **Law of Demeter** — Lieberherr et al., 1988.
- **Tracer Bullets / Vertical Slicing** — Hunt & Thomas, *The Pragmatic Programmer*, 1999.
- **Maven Standard Directory Layout** — convenção do Apache Maven.
- **Rol de Procedimentos da ANS** — Lei 9.656/98, RN 465/2021, Lei 14.454/2022 (ver `# Pesquisa_ Plano de Saúde Brasileiro como Domínio.md`).

### Riscos

- **Sobrecarga forçada:** o domínio só pede sobrecarga natural em construtores. Se o professor cobrar sobrecarga de método de instância (não construtor), pode ser preciso adicionar uma a mais. Candidato: `RepositorioProcedimento.buscar(...)` já cobre isso na fatia 5.
- **HCO ≡ Referência:** se questionado na apresentação, defender com base no recorte documentado (§4.3 e §7).
- **Decisão "exceção vs valor de retorno":** se o professor for tradicional e esperar negativas como exceção, ter pronto o argumento da escola Fowler (Bloch item 69) — defesa explícita pesa a favor.
