# CONTEXT.md — mapa do projeto

Última atualização: 2026-06-25

## O que é este projeto

Sistema de autorização de procedimentos por plano de saúde — trabalho acadêmico de
POO em Java. O objetivo real é **Pedro aprender a construir** (modelagem, engenharia,
Java), não o sistema pronto. Domínio detalhado no `docs/construcao/PRD.md`.

Este arquivo é o **mapa magro**: rotula cada artefato pelo bloco do método, marca onde
o projeto está na esteira e diz qual bloco vem agora. O conteúdo gordo vive nos docs
apontados — aqui não se duplica nada. (Método: ver `~/.claude/CONTEXT.md`.)

## Layout (pastas por natureza)

```
/  (raiz = governança + mapa)     CLAUDE.md · CONTEXT.md · COLABORACAO.md
docs/
├── enunciado/    Etapas.md                         (entra de fora)
├── pesquisa/     pesquisa-dominio.md               (Research / Investigar)
├── construcao/   PRD.md                            (Construir: PRD + Kanban §8)
└── entrega/      Etapa1 (.md + .excalidraw) · caso-de-uso · diagrama-de-classes  (sai pro professor)
```

A pasta agrupa por **natureza do artefato**, não por etapa. Governança fica na raiz
porque o tooling (`destilar-projeto`, `improve-codebase-architecture`) lê `CLAUDE.md`,
`CONTEXT.md` e companhia lá.

**Fluxo criação → entrega** *(convenção, 2026-06-03)*: `construcao/` é a bancada —
todo artefato nasce e é rascunhado ali. Ao concluir, migra para o local definitivo pela
sua natureza (tipicamente `entrega/`, o que sai pro professor). Foi assim com o
caso-de-uso: rascunhado em `construcao/`, movido para `entrega/caso-de-uso.excalidraw`
ao fechar.

## Mapa de artefatos → blocos

| Natureza | Arquivo | Bloco / papel |
|---|---|---|
| Enunciado (entra de fora) | `docs/enunciado/Etapas.md` | input *ideia/problema* da **Construir**, vindo de fora (ver §Interfaces externas) |
| Artefato de bloco | `docs/pesquisa/pesquisa-dominio.md` | saída do **Investigar** (Research) |
| Artefato de bloco | `docs/construcao/PRD.md` (+ §8) | borda da **Construir** (PRD + Kanban em fatias) |
| Config de colaboração | `CLAUDE.md`, `COLABORACAO.md` | governança (o "como", não um bloco) |
| Entregável (sai pra fora) | `docs/entrega/Etapa1-Proposta.md` + `.excalidraw` | output acadêmico — vai pro professor |

## Posição na esteira da Construir

```
Idea ✓ → Research ✓ → (Prototype: pulado) → PRD ✓ → Kanban ✓ (PRD §8) → Implementation ✓ → QA ✓
```

**Implementation concluída (2026-06-25)** — todas as 7 fatias do `PRD §8` (0–6)
entregues; 34 testes verdes; sistema roda fim-a-fim pelo menu CLI (`mvn exec:java`):
semeia cadastros, lista, autoriza (cobertura → carência → AP → coparticipação) e trata
erros de lookup na borda. QA via testes JUnit acompanhou cada fatia (TDD).

**▶ Retomar — Estudos Dirigidos (prioridade até 28/07/26)**

| ED | Conteúdo | Pasta de trabalho | Status |
|---|---|---|---|
| ED1 — UML | Casos de Uso + Diagrama de Classes | `docs/entrega/ed1-uml/` | ✅ concluído (2026-07-09) — `relatorio-ed1.pdf` gerado |
| ED2 — Design Patterns | Criacional + Estrutural + Comportamental | `docs/entrega/ed2-design-patterns/` | ✅ concluído (2026-07-09) — `relatorio-ed2.pdf` gerado |

Cadência planejada (2026-07-09):
- **Sessão A** — ED1 completo ✅
- **Sessão B** — Aprender: Design Patterns (3 grupos) ✅ concluído (2026-07-09)
- **Sessão C** — ED2 completo: andaime montado pelo Claude, Pedro ajusta

Padrões escolhidos para o ED2 (decidido Sessão B):
- Criacional → **Singleton** (âncora: `BeneficiarioRepository`)
- Estrutural → **Decorator** (âncora: `AutorizacaoService` + log)
- Comportamental → **Strategy** (já implementado: `CoparticipacaoPolicy`)

Paralelo: durante B/C emergem ideias para posts (Strategy → candidato ao Post #3 do Expor).

A modelagem UML da Etapa 2 está **completa** (ambos os diagramas em `docs/entrega/`):

- **Diagrama de caso de uso** — ✅ concluído 2026-06-03 (Pedro desenhou, Claude
  socrático ensinando a notação). `docs/entrega/caso-de-uso.excalidraw`.
- **Diagrama de classes** — ✅ concluído 2026-06-16 (modo (b): Claude rascunhou
  renderizando o `PRD §4`, Pedro corrigiu e fechou as 3 decisões de agregação/enum).
  `docs/entrega/diagrama-de-classes.excalidraw`.

**Gate da Etapa 1** *(aprovado 2026-06-03)*: a proposta foi aprovada pelo professor —
a Etapa 2 está liberada. Diagramas UML concluídos e implementação (Fatias 0–6) fechada.

## Interfaces externas (onde o método não chega)

O método (`~/.claude/CONTEXT.md`) modela o loop interno de construção do Pedro. Um
trabalho acadêmico encosta nele em dois pontos — só **um** é buraco real:

- **O problema que entra já tem nome.** O `docs/enunciado/Etapas.md` é o input
  *ideia/problema* da **Construir** (contrato da Construir no método), só vindo de
  fora em vez de partir do Pedro. Não é interface nova.
- **A avaliação que constrange e sai é o buraco real.** O `Etapas.md` não é só o
  problema inicial — é uma *rubric persistente* (a `COLABORACAO.md §1` a usa pra
  decidir quando descer ao nível de subtarefa), e `docs/entrega/` é um **entregável
  avaliado** que sai pro professor (Etapa 1 feita; Etapa 2 = UML + código; Etapa 3 =
  documentação). As saídas do método são "código validado" ou "post público" —
  nenhuma cobre uma rubric externa nem um entregável de nota. O Expor não serve (é
  blog/narrativa pública, não avaliação).

Registrado como rachadura conhecida: se a **interface de avaliação** (rubric que
constrange + entregável de nota) reaparecer em outro projeto acadêmico, vira
candidato a categoria nova no método.

## Decisões em curso / pendências

- **Gate da Etapa 1** — ✅ *aprovado 2026-06-03*. A proposta
  `docs/entrega/Etapa1-Proposta.md` foi aprovada pelo professor; a Etapa 2 (modelagem +
  código) está liberada. *(decisão original 2026-05-30: esperar aprovação antes de
  iniciar a Etapa 2.)*
- **Skill `to-prd` desatualizada** — ela ainda escreve o PRD na raiz; aqui o PRD vive
  em `docs/construcao/`. Corrigir é trabalho transversal (sistema global), não deste
  projeto.
- **Home canônico de Research = `docs/pesquisa/`** *(decidido 2026-05-30)*. Este
  projeto versiona todo artefato no repo, por natureza; a pesquisa fica junto do PRD,
  não em `.claude/research/`. A skill `consolidar-pesquisa` tem `.claude/research/<slug>.md`
  hardcoded — **mesma classe de fix transversal da `to-prd`** (skill assume caminho de
  raiz; o projeto sobrescreve o destino). Resolver no sistema global, não aqui.
- **`docs/adr/`** — convenção de ADR por-projeto prevista no método; ainda não usada.

