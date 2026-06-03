# CONTEXT.md — mapa do projeto

Última atualização: 2026-06-03

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
└── entrega/      Etapa1-Proposta.md + .excalidraw  (sai pro professor)
```

A pasta agrupa por **natureza do artefato**, não por etapa. Governança fica na raiz
porque o tooling (`destilar-projeto`, `improve-codebase-architecture`) lê `CLAUDE.md`,
`CONTEXT.md` e companhia lá.

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
Idea ✓ → Research ✓ → (Prototype: pulado) → PRD ✓ → Kanban ✓ (PRD §8) → Implementation ⬜ → QA ⬜
```

**Próximo passo: os diagramas UML da Etapa 2.** UML já aprendido (fora desta esteira,
pelo bloco **Aprender**). Faltam dois artefatos, ambos andaime do Claude (ver `CLAUDE.md`):

- **Diagrama de caso de uso** — Claude desenha (documento do trabalho).
- **Diagrama de classes** — Claude rascunha em **modo (b)**: renderiza o modelo já
  fechado no `PRD §4`, Pedro corrige e aprende; decisão de modelagem nova → Claude
  para e grelha, não decide de caneta.

Só **depois** dos diagramas vem a **Implementation** — a **Fatia 0** do `PRD §8`
(scaffold Maven: `pom.xml`, árvore, JUnit 5). Da Fatia 1 em diante o código é do Pedro
(ver `COLABORACAO.md`).

**Gate da Etapa 1** *(aprovado 2026-06-03)*: a proposta foi aprovada pelo professor —
a Etapa 2 está liberada. Pode desenhar os diagramas e, depois, iniciar a Fatia 0.

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
