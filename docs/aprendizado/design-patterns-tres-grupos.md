# Design Patterns — os 3 grupos (nota-sombra)

Sessão B — 2026-07-09. Fonte: refactoring.guru + código do projeto.

---

## O que é um padrão de projeto

Definimos aquilo que vamos usar repetido para não ter que fazer uma prosa enorme toda vez. Em design de software, soluções para problemas frequentes se repetem — não são idênticas, mas têm uma forma que rege a solução. Essa forma nomeada é o padrão de projeto.

---

## Criacional

**Problema:** quem usa o objeto não deveria saber qual classe concreta está sendo instanciada.

O padrão criacional esconde do chamador o *como* o objeto é criado — a classe concreta fica invisível pra quem usa.

**Singleton:** garante que só existe uma instância de uma classe no sistema. Âncora no projeto: `BeneficiarioRepository` — instanciar dois repositórios quebraria o compartilhamento de estado (dois repositórios guardariam objetos em separado, causando inconsistência).

---

## Estrutural

**Problema:** como compor objetos e classes em estruturas maiores sem quebrar o que já existe e sem explodir a complexidade.

O mecanismo é **composição**: você monta comportamento novo juntando objetos, sem herança e sem modificar classes existentes.

**Decorator:** envolve o objeto original adicionando comportamento em cima — o chamador não percebe, recebe o mesmo tipo. Âncora no projeto: adicionar log de autorizações sem modificar `AutorizacaoService` — cria um wrapper que loga e delega.

---

## Comportamental

**Problema:** como distribuir tarefas e responsabilidades entre os objetos e definir como eles interagem para cumprir o propósito do sistema.

**Strategy:** especializa objetos onde cada um implementa uma variação de algoritmo, mas todos são vistos como um só pelo chamador — o sistema seleciona o especialista, o usuário só executa a tarefa. Âncora no projeto: `CoparticipacaoPolicy` — o serviço chama `.calcular()` sem saber se é 10%, 20% ou isento.

---

## Escolhas para o ED2

| Grupo | Padrão | Âncora |
|---|---|---|
| Criacional | Singleton | `BeneficiarioRepository` |
| Estrutural | Decorator | `AutorizacaoService` + log |
| Comportamental | Strategy | `CoparticipacaoPolicy` (já implementado) |
