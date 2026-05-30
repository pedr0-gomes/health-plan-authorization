# COLABORACAO.md — Brief operacional do assistente

> Documento vivo. Define como Claude e Pedro colaboram durante a construção do projeto.
> Complementa o `CLAUDE.md` local (regras curtas, sempre em contexto) e o `PRD.md` (roteiro de construção).

---

## 1. Unidade de ciclo de colaboração

**Default: fatia.** Cada fatia do PRD (§8) abre com uma rodada de provocação do assistente, fecha com revisão. Dentro da fatia, o assistente fica calado.

**Descida ao nível de subtarefa em dois gatilhos:**

1. Pedro chama.
2. A subtarefa toca um **conceito-chave do rubric Etapa 2** — encapsulamento sério, herança, polimorfismo (sobrescrita ou sobrecarga), classe abstrata, interface. Nesses pontos o assistente provoca antes de Pedro atacar, porque é onde o aprendizado se ganha ou se perde.

**Por quê:** ritmo sem burocracia; protege os pontos altos do rubric; respeita a fricção nos pontos baixos.

---

## 2. Diagramas e documentos do trabalho

A cerimônia do trabalho acadêmico é minha (Claude); Pedro revisa e cura.

- **Diagrama de caso de uso** e **documentação formal da Etapa 3** — Claude produz
  o rascunho a partir do que já está decidido (PRD, código). Pedro revisa.
- **Diagrama de classes — modo (b):** Claude rascunha renderizando o modelo já
  decidido no `PRD §4`. Pedro aprende corrigindo e questionando; Claude ensina a
  notação UML inline, na primeira vez que cada elemento aparece.
- **Guarda (a mesma do CLAUDE.md):** o rascunho só transcreve decisões já no
  `PRD §4`. Se desenhar exigir uma decisão de modelagem nova, Claude **para e
  grelha** — não decide de caneta.
- Nada de cerimônia entra como final sem o aval do Pedro.

---
