# CONTEXT.md — mapa do projeto

Última atualização: 2026-05-30

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
| Enunciado (entra de fora) | `docs/enunciado/Etapas.md` | input externo — o problema dado pelo professor |
| Artefato de bloco | `docs/pesquisa/pesquisa-dominio.md` | saída do **Investigar** (Research) |
| Artefato de bloco | `docs/construcao/PRD.md` (+ §8) | borda da **Construir** (PRD + Kanban em fatias) |
| Config de colaboração | `CLAUDE.md`, `COLABORACAO.md` | governança (o "como", não um bloco) |
| Entregável (sai pra fora) | `docs/entrega/Etapa1-Proposta.md` + `.excalidraw` | output acadêmico — vai pro professor |

## Posição na esteira da Construir

```
Idea ✓ → Research ✓ → (Prototype: pulado) → PRD ✓ → Kanban ✓ (PRD §8) → Implementation ⬜ → QA ⬜
```

**Próximo bloco: Implementation** — a **Fatia 0** do `PRD §8` (scaffold Maven:
`pom.xml`, árvore de diretórios, JUnit 5). Execução: Claude. Da Fatia 1 em diante o
código é do Pedro (ver `COLABORACAO.md`).

**Bloqueado pelo gate da Etapa 1** (ver abaixo): não iniciar a Fatia 0 até a proposta
ser aprovada.

## Interfaces externas (onde o método não chega)

O método (`~/.claude/CONTEXT.md`) modela o loop interno de construção do Pedro. Um
trabalho acadêmico acrescenta duas interfaces que os cinco blocos não nomeiam:

- **Enunciado que entra** — `docs/enunciado/Etapas.md`. Restrição externa, a montante.
- **Entregável que sai** — `docs/entrega/` (Etapa 1 feita; Etapa 2 = UML + código;
  Etapa 3 = documentação completa). O Expor não cobre isso (é pra blog/narrativa
  pública, não pra formulário acadêmico).

Registrado como rachadura conhecida: se reaparecer em outro projeto acadêmico, vira
candidato a categoria nova no método.

## Decisões em curso / pendências

- **Gate da Etapa 1** — *(decidido 2026-05-30: **esperar aprovação**)*. A Etapa 2
  (modelagem + código), e portanto a Fatia 0, só começam depois que o professor
  aprovar `docs/entrega/Etapa1-Proposta.md`.
- **Skill `to-prd` desatualizada** — ela ainda escreve o PRD na raiz; aqui o PRD vive
  em `docs/construcao/`. Corrigir é trabalho transversal (sistema global), não deste
  projeto.
- **Home canônico de Research** — o método prevê `.claude/research/<slug>.md` para
  pesquisa consolidada pela skill `consolidar-pesquisa`. O `pesquisa-dominio.md` atual
  foi feito à mão; se rodarmos a skill, a saída dela vai pra lá.
- **`docs/adr/`** — convenção de ADR por-projeto prevista no método; ainda não usada.
