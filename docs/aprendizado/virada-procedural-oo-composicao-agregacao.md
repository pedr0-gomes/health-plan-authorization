# A virada procedural → OO (composição vs. agregação como veículo)

> Nota-sombra do bloco **Aprender** (2026-06-25). As definições abaixo são a
> **recontagem do Pedro nas próprias palavras**, peça a peça, cada uma grelhada
> contra a **bitola** = o modelo real do projeto (código + PRD §4 + diagrama),
> não contra conhecimento paramétrico do Claude. Âncora da 3ª rodada do Expor.

**Intuição de C que abriu a sessão (o erro produtivo):** "cada objeto é uma
estrutura de dados única, posso criar várias, não preciso me preocupar com a
relação entre elas." (Fundo procedural: C primeiro, OO agora.)

---

## Peça 1 — de struct a objeto

**Recontagem:** objeto = dado + comportamento no mesmo lugar. Isso é melhor que
o jeito C (funções por fora lendo os campos) por dois motivos: (1) **mudou a
regra, não preciso caçar todas as funções espalhadas** — a mudança fica
concentrada; (2) **protege o invariante** — ninguém de fora me coloca num estado
inválido sem passar pela porta (construtor/métodos).

**Bitola:** `Plano.cobre(p)` delega `segmentacao.cobre(p)` (não lê os campos pra
decidir = *Tell-Don't-Ask*, PRD §4.5); campos `private final`; validação
`carencia < 0` no construtor.

## Peça 2 — por que a relação entre objetos importa

**Recontagem:** importa porque o objeto guardado pode ser **o mesmo** (referência
compartilhada), não uma cópia — e "o mesmo" tem peso. **Identidade é endereço:**
dois objetos com dados iguais ainda são objetos diferentes; `==` entre eles dá
falso porque compara endereço, não conteúdo. A cabeça procedural não vê isso (lá,
dado igual *é* a mesma coisa).

**Bitola:** `this.segmentacao = segmentacao` guarda a referência; PRD §4.2
("compartilháveis entre planos").

## Peça 3 — composição vs. agregação

**Recontagem:** "um objeto guarda outro" vem em dois sabores. O que decide **não**
é a estrutura das classes (classe separada + atributo é igual nos dois) — é o
**teste**: *apaga o todo, a parte morre junto?*
- **Composição** (losango cheio): parte exclusiva, nasce e morre com o todo.
- **Agregação** (losango vazio): parte vem de fora, compartilhável, **sobrevive**
  ao todo. ← `Plano ◇ Segmentacao`. Apaga um Plano, a Segmentação não morre,
  porque não vive dentro dele e outros planos ainda a referenciam.

**Bitola:** PRD §4.2 (agregação); diagrama de classes (losango vazio).
**Aprofundar:** Fowler, *UML Distilled*, cap. de relacionamentos.

## Peça 4 — a conta que a agregação cobra (aliasing)

**Recontagem:** a agregação compartilha **o mesmo objeto** entre vários donos. Se
ele for **mutável**, mexer por um dono mexe pra todos — uma alteração via Plano A
aparece no Plano B, porque referenciam o mesmo objeto. Isso é **aliasing**. A
**defesa** é tornar a parte **imutável**: definida só na construção, sem setter.
Imutável ⇒ compartilhar é seguro.

**Bitola:** `Segmentacao` tem `nome`/`descricao` `private final` e nenhum setter.
**Aprofundar:** Bloch, *Effective Java*, item 17 ("Minimize mutability" / *value
object*).

---

## A virada (o fecho)

Objetos são mais **autônomos**, mas isso não dispensa relacioná-los — ao
contrário: **relacionar com consciência** (de quem é? quem compartilha?) é o que
separa "struct solta" de um **modelo**, e é o que evita redundância. A cabeça
procedural nunca fazia essa pergunta; a OO obriga.

E a correção load-bearing: **imutabilidade não é benefício de relacionar, é
defesa.** A ordem é "escolhi compartilhar (agregação) → **preciso** de
imutabilidade pra isso ser seguro", não "relaciono → ganho imutabilidade".
