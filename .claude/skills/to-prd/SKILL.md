---
name: to-prd
description: Converte o contexto da conversa atual num PRD local (arquivo PRD.md na raiz do projeto). Adaptado de mattpocock/skills/engineering/to-prd para projetos solo de aprendizado — sem issue tracker, com seções extras de Mapa de conceitos OO e Roteiro de fatias/subtarefas. Use quando o usuário quiser consolidar decisões da conversa num documento mestre.
---

# to-prd (local, adaptada)

Sintetiza o contexto da conversa em um PRD escrito em `PRD.md` na raiz do projeto. **Não entreviste o usuário** — use o que já está no contexto.

Skill adaptada da original `engineering/to-prd` (mattpocock). Diferenças:
- Saída em arquivo local, não em issue tracker.
- Seções extras pensadas para projetos acadêmicos de POO (Mapa de conceitos OO obrigatórios, Roteiro de fatias e subtarefas).
- User Stories ficam preparadas pra virar diagrama de caso de uso UML.

## Processo

1. **Sintetize** o que já está no contexto (decisões, justificativas, alternativas recusadas). Não invente; se algo não foi decidido, anote como pendência na seção "Notas adicionais".
2. **Use o vocabulário do domínio** que o usuário já está usando (pesquisa de domínio do projeto, glossário, ADRs).
3. **Escreva o PRD** seguindo o template abaixo. Salve em `PRD.md` na raiz do projeto.
4. **Não inclua código** ou caminhos de arquivo no PRD — esses envelhecem rápido. Exceção: snippets curtos que codificam uma decisão melhor que prosa (assinatura de método, esqueleto de classe abstrata).
5. **Resposta final** ao usuário: lista curta com o que foi escrito + sugestão de próximos passos.

## Template

```markdown
# PRD — <nome do sistema>

> Documento mestre. Consolida decisões tomadas na(s) sessão(ões) de grill.
> Substitui DECISOES.md anteriores (que ficam como histórico).

---

## 1. Contexto e objetivo

Por que esse projeto existe. Cite o enunciado/rubric/etapas se houver.
Quem é o aluno/usuário, qual o cenário, qual o objetivo final.

## 2. Solução proposta

O QUE o sistema faz, em prosa curta. Sem detalhes de implementação.

## 3. Funcionalidades / Casos de uso

Lista numerada de user stories no formato:

  Como <ator>, quero <funcionalidade>, para <benefício>.

Cobrir TODOS os atores do sistema (usuário humano, o sistema em si reagindo
a uma regra). Lista extensiva — depois vira diagrama de caso de uso UML.

## 4. Decisões de modelagem e arquitetura

Decisões técnicas TOMADAS. Não exploratórias. Cada decisão com:
- O QUE foi decidido
- POR QUÊ (breve)
- ALTERNATIVAS recusadas (uma linha cada)

Inclua: módulos/entidades principais, relações entre elas, onde mora cada regra,
papéis de cada classe abstrata/interface.

## 5. Decisões de teste

- Que biblioteca, em que nível.
- Que cenários cobertos por fatia.
- Convenções (datas fixas, padrão de nomeação, casos de borda).

## 6. Mapa de conceitos OO obrigatórios

Apenas se o projeto for acadêmico com rubric que exige conceitos OO específicos.
Tabela: conceito do rubric → onde aparece no projeto → razão da escolha desse lugar.

## 7. Out of Scope

Lista do que NÃO entra no projeto, e por quê. Cada item: o que foi cortado +
razão da exclusão (geralmente: "extrapola escopo de aprendizado" ou
"adiciona complexidade sem ensinar conceito novo").

## 8. Roteiro de fatias e subtarefas

Sequência incremental de construção. Cada fatia com:
- Objetivo
- Subtarefas (granulares; cada uma com conceito-foco e critério de pronto)
- Entrega ao fim da fatia (o que o sistema sabe fazer)

## 9. Notas adicionais

- Pegadas didáticas a lembrar durante implementação
- Pendências em aberto
- Riscos
- Referências bibliográficas para conceitos formais nomeados
```

## Quando usar

- Após uma ou mais sessões de `grill-me` que produziram decisões claras.
- Quando o usuário disser algo como "vamos documentar isso" / "preciso de um documento mestre" / "registra essas peças".
- **Não** use para rascunhar ideias ainda em discussão — PRD documenta o que foi decidido, não o que está em aberto.

## Quando NÃO usar

- Decisões ainda exploratórias → continue `grill-me`.
- Documentação de código existente → use `analyze` ou similar.
- Documento de progresso/log → não é PRD; é changelog ou journal.
