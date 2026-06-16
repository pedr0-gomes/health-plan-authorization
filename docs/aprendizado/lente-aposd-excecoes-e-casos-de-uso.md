# Lente APOSD — exceções e o que conta como caso de uso

> Nota-sombra de uma sessão de aprendizado situado (método `aprender`): revisão
> retrospectiva do diagrama de casos de uso (`docs/entrega/caso-de-uso.excalidraw`)
> sob a lente do cap. 10 de *A Philosophy of Software Design* (Ousterhout),
> *"Define Errors Out of Existence"*. As definições abaixo são minhas (Pedro),
> grelhadas contra a passagem do livro — não contra o conhecimento do Claude.

## Fonte (bitola)

NotebookLM "Filosofia do Design de Software", cap. 10. Tese: programadores
**lançam exceções demais**; a técnica nº 1 é **redefinir a semântica para que o
caso "erro" deixe de existir** (ex.: `unset` no Tcl retornando em silêncio se a
variável já não existe). Exceções são **caras** pro design (complexidade,
testes raros, interface rasa).

## Peça 1 — "id de beneficiário inexistente" pode deixar de ser erro?

Tentei aplicar a técnica nº 1: transformar `id inexistente` num desfecho de
`autorizar` (um valor `dado inválido` no mesmo enum de `negativa por cobertura`).

**Onde caiu:** isso só **troca o canal** do erro (de exceção pra valor de
retorno), não o **define pra fora**. O critério do `unset` é que o objetivo de
quem chamou foi alcançado de qualquer jeito. Aqui o objetivo era *uma decisão
sobre o beneficiário X*, e com `id 999` essa decisão é **inalcançável** —
`autorizar` não tem nada pra olhar (sem plano, sem carência, sem histórico). As
outras saídas são "olhei e a resposta é não, pelo motivo R"; `dado inválido`
seria "**não tive como produzir resposta nenhuma**". Categoria diferente.

**Conclusão:** `id inexistente` = pré-condição violada (falta o *sujeito* da
pergunta) = **exceção**. Minha régua original aguentou. Aqui o Ousterhout
**não mordeu** — e isso é resultado honesto, não fracasso. Casa com PRD §4.7.

## Peça 2 — a F6 "Sinalizar entrada inválida" é caso de uso?

Não. **Critério:** caso de uso = um **objetivo de um ator** que entrega
resultado observável de valor. O teste não é "o sistema faz isso?", é "**tem um
ator que quer isso?**". Na F4 o usuário vem atrás de uma decisão de autorização;
ninguém abre o sistema com o objetivo de "ser avisado que digitou errado".

**Armadilha que evitei:** separar por **frequência** (F4 sempre, F6 às vezes).
Condicional não desqualifica caso de uso — é o que o «extend» existe pra
modelar. O corte é o ator-com-objetivo, não sempre-vs-às-vezes.

Nome do conceito: **nível de objetivo do usuário** (Cockburn, *Writing
Effective Use Cases*).

## Redesenho

A F6 desenhada como caso de uso «extend» era o análogo, no diagrama, de "lançar
exceção demais": **exceção inflada como elemento de primeira classe**. Aqui o
Ousterhout **mordeu** — não na decisão exceção-vs-retorno (Peça 1), mas no
**lugar** onde a exceção apareceu.

- F6 **sai** do diagrama (bolha + seta «extend» removidas).
- "Sinalizar entrada inválida" vira **fluxo de exceção na descrição da F4**.
- F6 como *requisito* (avisar sem encerrar o programa) continua válido na
  Etapa 1 — o errado era desenhá-lo como bolha.

Isso não foi andar pra trás: o diagrama estava **destoando do PRD §4.7**, que já
tratava entrada inválida como exceção. Reconciliar requisito → caso de uso →
classe é **rastreabilidade**, o ciclo normal de modelagem iterativa.
