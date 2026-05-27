## 1\. Carência

### (a) Como funciona na prática

* Carência é o período após a contratação em que o beneficiário ainda não tem direito a usar determinadas coberturas, apesar de já pagar mensalidade.[^16](https://www.gov.br/ans/pt-br/assuntos/consumidor/carencia)
* Para planos “novos” (após 1999 ou adaptados), a Lei 9.656/98 e a ANS estabelecem prazos máximos: 24 horas para urgência e emergência, 300 dias para partos a termo, 180 dias para “demais situações” (consultas, exames, internações), e até 24 meses para cobertura parcial temporária (CPT) em doenças ou lesões preexistentes.[^17](https://bentecconsultoria.com.br/lei-9656-98-carencia-do-plano-de-saude/)[^6](https://www.gov.br/ans/pt-br/assuntos/consumidor/carencia)
* Urgência/emergência: após 24 horas, o plano é obrigado a dar cobertura mínima; em segmentação ambulatorial, a internação pode ser limitada às primeiras 12 horas, enquanto planos hospitalares podem garantir internação integral.[^16](https://www.gov.br/ans/pt-br/assuntos/consumidor/carencia)[^1](https://www.gov.br/ans/pt-br/assuntos/contratacao-e-troca-de-plano/dicas-de-como-escolher-um-plano-de-saude-1/segmentacao-assistencial)
* Regras de carência variam por forma de contratação: planos individuais e coletivos pequenos podem aplicar carência; coletivos empresariais com 30 ou mais beneficiários normalmente têm isenção de carência para quem entra em até 30 dias do contrato ou do vínculo com a empresa.[^4](https://www.gov.br/ans/pt-br/assuntos/consumidor/carencia)

Como regra de decisão (versão realista):

* Entrada: tipo de atendimento (urgência/emergência, parto, demais), indicação de doença preexistente/relacionada, data de contratação (início de vigência), data do evento.
* Passos principais:

  * Calcular dias decorridos entre a data do evento e a contratação.
  * Se urgência/emergência e dias ≥ 1, garantir ao menos a cobertura mínima exigida pela segmentação (p.ex. até 12h num plano só ambulatorial).[^6](https://www.gov.br/ans/pt-br/assuntos/contratacao-e-troca-de-plano/dicas-de-como-escolher-um-plano-de-saude-1/segmentacao-assistencial)[^16](https://www12.senado.leg.br/institucional/sis/noticias-comum/entenda-os-prazos-de-carencia-nos-procedimentos-em-saude-com-cobertura-do-sis)
  * Se parto a termo, exigir até 300 dias; partos prematuros com risco podem ser enquadrados como urgência, caindo na regra de 24 horas.[^18](https://www12.senado.leg.br/institucional/sis/noticias-comum/entenda-os-prazos-de-carencia-nos-procedimentos-em-saude-com-cobertura-do-sis)[^6](https://www.gov.br/ans/pt-br/assuntos/consumidor/carencia)
  * Para demais situações, exigir até 180 dias.
  * Se doença/lesão preexistente declarada e CPT contratual em vigor (até 24 meses), suspender a cobertura apenas para procedimentos ligados àquela DLP.[^19](https://www.youtube.com/watch?v=jZ5esOJ4Jo4)[^17](https://www.altamaisseguros.com.br/carencia-contratual-nos-planos-de-saude-entenda-quando-voce-pode-usar-o-plano-e-quando-ainda-nao-pode/)



### (b) Recorte enxuto para modelagem

Sugestão de simplificação:

* Manter apenas três prazos de carência globais, parametrizados no plano:

  * `carenciaUrgenciaEmergencia = 1 dia`
  * `carenciaParto = 300 dias`
  * `carenciaDemais = 180 dias`
* Ignorar CPT (24 meses para doenças preexistentes), isenções por tamanho de grupo, portabilidade de carências, detalhes de urgência obstétrica e exceções judiciais.

Justificativa:

* A lógica essencial que você quer exercitar é “entre data de contratação e data do evento, comparar diferença com um prazo parametrizado, dependendo do tipo de procedimento/atendimento”.
* Incluir CPT exigiria vincular diagnósticos à doença declarada, manter um estado temporal longo (24 meses) e adicionar ramificações clínicas que não agregam muito em termos de aprendizado de POO.
* Isenções por modalidade empresarial/coletiva adicionam muitas regras de negócio de RH e comercial, mas pouco em termos de desenho de classes para autorização clínica.

Modelagem sugerida (alto nível):

* `Plano` expõe algo como `prazoCarencia(tipoProcedimento)` que retorna um número de dias.
* A operação de autorização chama `estaCumpridaCarencia(beneficiario, procedimento, data)` como uma regra de decisão (possivelmente implementada por uma “policy” de carência).

\---

## 2\. Cobertura / Rol de Procedimentos

### (a) Como funciona na prática

* O Rol de Procedimentos e Eventos em Saúde da ANS é uma lista de consultas, exames, cirurgias e tratamentos que os planos são obrigados a cobrir, variando conforme a segmentação assistencial (ambulatorial, hospitalar com/sem obstetrícia, referência, odontológico).[^21](https://www.gov.br/ans/pt-br/assuntos/consumidor/o-que-o-seu-plano-de-saude-deve-cobrir-1)[^2](https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos)
* Cada item do Rol traz, além do código e descrição, a segmentação em que é de cobertura obrigatória (colunas como AMB, HCO, HSO, REF) e, para vários procedimentos, Diretrizes de Utilização (DUT) que estabelecem critérios clínicos (idade, quadro clínico, número máximo de sessões, etc.).[^13](https://www.ans.gov.br/images/stories/Legislacao/rn/rn465/Anexo_I_-_Rol_de_Procedimentos_RN_465.2021.pdf)[^2](https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos)
* O Rol vale para planos novos/adaptados, e passou a ser “referência básica” mínima, com possibilidade de cobertura de procedimentos não listados quando equivalentes em eficácia e recomendados por diretrizes científicas, após alterações na Lei 9.656/98 (Lei 14.454/2022).[^22](https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos)
* Segmentações:

  * Ambulatorial: cobre consultas, exames e procedimentos que não exigem internação, com urgência/emergência limitada às primeiras 12h.[^23](https://www.gov.br/ans/pt-br/assuntos/contratacao-e-troca-de-plano/dicas-de-como-escolher-um-plano-de-saude-1/segmentacao-assistencial)
  * Hospitalar sem obstetrícia: cobre internações, exceto parto.[^1](https://www.gov.br/ans/pt-br/assuntos/contratacao-e-troca-de-plano/dicas-de-como-escolher-um-plano-de-saude-1/segmentacao-assistencial)
  * Hospitalar com obstetrícia: inclui parto e cobertura ao recém-nascido por 30 dias, além da internação.[^23](https://www.gov.br/ans/pt-br/assuntos/contratacao-e-troca-de-plano/dicas-de-como-escolher-um-plano-de-saude-1/segmentacao-assistencial)
  * Referência: combina ambulatorial + hospitalar com obstetrícia, com acomodação em enfermaria e cobertura integral de urgência/emergência após 24h.[^3](https://www.gov.br/ans/pt-br/assuntos/contratacao-e-troca-de-plano/dicas-de-como-escolher-um-plano-de-saude-1/segmentacao-assistencial)

Onde está a variação de comportamento:

* Um mesmo procedimento pode ser coberto ou não conforme a segmentação; p.ex., internação depende de plano hospitalar ou referência.[^3](https://www.gov.br/ans/pt-br/assuntos/contratacao-e-troca-de-plano/dicas-de-como-escolher-um-plano-de-saude-1/segmentacao-assistencial)
* O atendimento de urgência em plano ambulatorial é limitado em tempo, enquanto em plano hospitalar não há limite de internação por lei.[^1](https://www.gov.br/ans/pt-br/assuntos/contratacao-e-troca-de-plano/dicas-de-como-escolher-um-plano-de-saude-1/segmentacao-assistencial)
* DUTs introduzem lógica condicional por procedimento (se não cumpre os critérios da DUT, a operadora pode negar ou postergar a cobertura).[^24](https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos)[^13](https://www.unimednortepaulista.com.br/arquivos/Diretrizes-da-ANS.pdf)



### (b) Recorte enxuto para modelagem

Sugestão de simplificação:

* Representar o Rol como:

  * Uma entidade `Procedimento` com: `codigoRol`, `tipo` (consulta, exame, cirurgia, internação, parto, odontológico), `segmentacoesQueCobrem` (conjunto de segmentações).
  * Opcionalmente, um flag `requerAutorizacaoPrevia`.
* Ignorar:

  * Lei 14.454/2022 (cobertura de tratamentos não listados, equivalência terapêutica etc.).
  * DUTs detalhadas – basta saber que alguns procedimentos “têm critérios extras”, mas você pode representá-los como um simples flag para diferenciar o fluxo.
  * Diferença entre Rol mínimo e coberturas contratuais adicionais: assuma que o contrato não adiciona nada além do Rol.

Justificativa:

* O essencial para modelagem de autorização é: “este plano, com esta segmentação, cobre ou não este procedimento?”.
* DUTs exigem modelar critérios clínicos (diagnóstico, resultados de exames, número histórico de sessões), o que é outro domínio (clínico) que rapidamente extrapola um exercício de POO básico.
* A distinção “Rol mínimo x coberturas extra” é importante na vida real, mas pouco relevante para treinar polimorfismo — você pode modelar isso, se quiser, como um simples atributo `listaDeProcedimentosExtras` no plano mais tarde.

Modelagem sugerida:

* Uma hierarquia ou estratégia para `Segmentacao` com método `cobre(Procedimento)` faz sentido como ponto de variação de comportamento.
* Alternativamente, deixar `segmentacao` como `enum` e codificar a regra em uma classe de serviço (menos OO “puro”, mas mais simples).

\---

## 3\. Coparticipação

### (a) Como funciona na prática

* Coparticipação é o valor pago pelo beneficiário à operadora a cada utilização do plano (consulta, exame, internação), além da mensalidade; funciona como um “compartilhamento de custos”.[^15](https://legismap.com.br/component/legismap_ferramentas/?task=generatePdf.getPdf&artigo=47532&Itemid=101)[^14](https://eltonfernandes.com.br/coparticipacao-plano-de-saude)
* A ANS, pela RN 433/2018 (que chegou a ser suspensa judicialmente, mas é uma boa referência técnica), definiu percentuais máximos (coparticipação até 40% do valor do procedimento), tetos mensais e anuais de exposição financeira, e isenção de coparticipação em uma lista de procedimentos preventivos e tratamentos de doenças crônicas.[^9](https://fortplanos.com.br/blog/coparticipacao-plano-de-saude)
* Formas de cálculo típicas:

  * Percentual sobre o valor do procedimento (ex.: 30% do valor cobrado pelo prestador ou da tabela da operadora).
  * Valor fixo por tipo de procedimento (ex.: R$ 20 por consulta, R$ 50 por exame de alta complexidade).
  * Tabelas mistas (percentual com valor mínimo ou máximo, ou faixas por tipo de serviço).[^14](https://fortplanos.com.br/blog/coparticipacao-plano-de-saude)
* Coparticipação não muda a existência da cobertura; ela afeta a parte do custo a ser paga pelo beneficiário, e está ligada à ideia de mecanismo financeiro de regulação do uso.[^7](https://legismap.com.br/component/legismap_ferramentas/?task=generatePdf.getPdf&artigo=47532&Itemid=101)



### (b) Recorte enxuto para modelagem

Sugestão de simplificação:

* Representar coparticipação como uma política associada ao plano:

  * `tipoModelo`: `SEM\_COPARTICIPACAO`, `PERCENTUAL`, `FIXO\_POR\_TIPO`.
  * Para `PERCENTUAL`: campo `percentualCoparticipacao`.
  * Para `FIXO\_POR\_TIPO`: mapa `tipoProcedimento -> valorFixo`.
* Ignorar:

  * Tetos mensal/anual de exposição financeira.
  * Lista de procedimentos isentos (preventivos, crônicos).
  * Distinção técnica entre coparticipação e franquia.

Justificativa:

* Para efeitos de POO, a graça está em ter um método `calcularCoparticipacao(procedimento, valorBase)` que pode variar de implementação conforme o plano.
* Tetos mensais e anuais exigiriam acumular uso ao longo do tempo e carregar estado financeiro do beneficiário (foge do foco “autorização de um procedimento isolado”).
* Isenções por tipo de procedimento são interessantes, mas você pode simular isso mais tarde com uma simples regra “se tipo == PREVENTIVO, coparticipação = 0”.

Modelagem sugerida:

* Interface `PoliticaCoparticipacao` com implementações: `SemCoparticipacao`, `CoparticipacaoPercentual`, `CoparticipacaoFixaPorTipo`.
* O `Plano` delega ao objeto `politicaCoparticipacao` o cálculo final.

\---

## 4\. Autorização prévia

### (a) Como funciona na prática

* Autorização prévia é o processo administrativo em que a operadora analisa um pedido médico antes de liberar um procedimento, especialmente exames de alta complexidade, internações eletivas e cirurgias.[^12](https://blog.ilovesaude.com.br/post/quando-plano-de-saude-pode-exigir-autorizacao-previa)[^2](https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos)
* Ela serve para:

  * Confirmar se o procedimento está coberto pelo plano e pela segmentação.
  * Verificar se prestador/hospital é credenciado na área de abrangência.
  * Avaliar se a indicação atende às Diretrizes de Utilização da ANS (DUT) ou protocolos internos.[^11](https://centralsaudecaixa.com.br/faq/o-que-sao-e-para-que-servem-as-duts/)[^2](https://www.unimednortepaulista.com.br/arquivos/Diretrizes-da-ANS.pdf)
  * Combater fraudes e uso desnecessário.[^11](https://www.geap.org.br/wp-content/uploads/Guia-de-leitura-ANS.pdf)
* A ANS, pela RN 259/2011, define prazos máximos para a operadora responder: alguns materiais técnicos citam, por exemplo, 3 dias úteis para exames laboratoriais/imagem simples, 10 dias para procedimentos de maior complexidade em ambulatório, e prazos mais longos para procedimentos de alta complexidade, com urgência/emergência devendo ser atendida imediatamente.[^25](https://julianokubaskiadvocacia.com.br/prazos-para-liberacao-de-procedimentos-por-planos-de-saude/)
* Não confundir: um procedimento pode estar no Rol (logo, ter cobertura obrigatória) e ainda assim exigir autorização prévia; a negativa só é legítima se houver fundamento (não cumprimento de DUT, carência, exclusão contratual, CPT etc.).[^2](https://www.unimednortepaulista.com.br/arquivos/Diretrizes-da-ANS.pdf)[^11](https://blog.ilovesaude.com.br/post/quando-plano-de-saude-pode-exigir-autorizacao-previa)

Diferença de fluxo por tipo de procedimento:

* Procedimentos simples (consultas, exames básicos): em geral, não exigem autorização prévia; o controle é feito apenas na hora do atendimento (cobertura + carência).[^12](https://www.gov.br/ans/pt-br/assuntos/consumidor/o-que-o-seu-plano-de-saude-deve-cobrir-1)
* Procedimentos de alto custo/complexidade (cirurgias, ressonância, tomografia, quimioterapia, internação eletiva): em geral exigem autorização prévia, com análise médica e administrativa antes da realização.[^13](https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos)[^11](https://blog.ilovesaude.com.br/post/quando-plano-de-saude-pode-exigir-autorizacao-previa)



### (b) Recorte enxuto para modelagem

Sugestão de simplificação:

* Atribuir a cada `Procedimento`:

  * `requerAutorizacaoPrevia: boolean`.
* Implementar dois fluxos:

  * Fluxo direto: se `requerAutorizacaoPrevia == false`, a autorização ocorre “no ato” do atendimento, com checagem de cobertura + carência + coparticipação.
  * Fluxo de pedido: se `true`, criar uma entidade `PedidoAutorizacao` com estados (`SOLICITADO`, `EM\_ANALISE`, `AUTORIZADO`, `NEGADO`, `EXPIRADO`), onde a análise aplica as mesmas regras, possivelmente com um passo extra “verificar critérios clínicos/DUT” modelado como simples regra booleana.

Ignorar:

* Prazos de resposta (3, 10, 21 dias etc.) e consequências do descumprimento.
* Possibilidade de recurso administrativo e negativa tácita.
* Detalhamento das DUTs.

Justificativa:

* O que mais interessa para seu domínio é a existência de dois fluxos distintos: com e sem autorização prévia, com tipos de entidades diferentes (`Atendimento` vs `PedidoAutorizacao`).
* Prazos e recursos judiciais adicionam estado temporal e integrações externas, que são mais temas de sistemas distribuídos do que de modelagem OO básica.

Modelagem sugerida:

* `Procedimento.requerAutorizacaoPrevia()` decide o fluxo.
* A operação central de autorização orquestra `PedidoAutorizacao` quando necessário.

\---

## 5\. Modalidades e segmentações de plano

### (a) Como funciona na prática

Modalidade de contratação (ANS – RN 557/2022):

* Planos são classificados em individual/familiar, coletivo empresarial e coletivo por adesão para fins de contratação.[^5](https://www.gov.br/ans/pt-br/centrais-de-conteudo/o-que-o-beneficiario-precisa-saber-pdf)
* Individual/familiar: contratado diretamente pelo beneficiário; carências e reajustes seguem regras específicas; em geral há carência conforme Lei 9.656/98.[^4](https://www.gov.br/ans/pt-br/assuntos/consumidor/carencia)
* Coletivo empresarial: contratado pela empresa para seus funcionários; em contratos com 30 ou mais beneficiários, há isenção de carência para quem ingressa dentro de 30 dias da celebração do contrato ou da vinculação à empresa.[^6](https://www.gov.br/ans/pt-br/centrais-de-conteudo/o-que-o-beneficiario-precisa-saber-pdf)
* Coletivo por adesão: contratado por entidades de classe, sindicatos etc.; carências e cobertura seguem o contrato, respeitando limites da ANS.[^27](https://www.gov.br/ans/pt-br/centrais-de-conteudo/o-que-o-beneficiario-precisa-saber-pdf)

Segmentação assistencial:

* Define o escopo de cobertura (ambulatorial, hospitalar com/sem obstetrícia, odontológico, referência e combinações).[^23](https://www.geap.org.br/wp-content/uploads/Guia-de-leitura-ANS.pdf)[^1](https://www.gov.br/ans/pt-br/assuntos/contratacao-e-troca-de-plano/dicas-de-como-escolher-um-plano-de-saude-1/segmentacao-assistencial)
* Segmentação referência: cobre ambulatorial + hospitalar com obstetrícia, com cobertura integral de urgência/emergência após 24 horas.[^3](https://www.gov.br/ans/pt-br/assuntos/contratacao-e-troca-de-plano/dicas-de-como-escolher-um-plano-de-saude-1/segmentacao-assistencial)
* Segmentação hospitalar com obstetrícia adiciona regras específicas para parto e recém-nascido (30 dias com cobertura e possibilidade de inclusão como dependente sem carência).[^23](https://www.gov.br/ans/pt-br/assuntos/contratacao-e-troca-de-plano/dicas-de-como-escolher-um-plano-de-saude-1/segmentacao-assistencial)

Outros atributos relevantes:

* Área de abrangência (nacional, grupo de estados, estadual, municipal) define onde a operadora garante as coberturas contratadas.[^7](https://www.gov.br/ans/pt-br/assuntos/consumidor/o-que-o-seu-plano-de-saude-deve-cobrir-1)
* Padrão de acomodação (enfermaria/ apartamento) e rede credenciada são relevantes para experiência e custo, mas não alteram muito a lógica booleana de “cobre ou não cobre” o procedimento (exceto que precisa ser prestador credenciado na área).[^7](https://www.gov.br/ans/pt-br/assuntos/consumidor/o-que-o-seu-plano-de-saude-deve-cobrir-1)

Onde está a variação comportamental real:

* Segmentação assistencial impacta diretamente nas regras de cobertura, urgência/emergência e parto (como discutido em Cobertura/Rol).
* Modalidade de contratação afeta regras de carência, rescisão e reajuste, mas não muda o algoritmo clínico de autorização, exceto se você quiser modelar isenção de carência para contratos empresariais grandes.[^4](https://www.gov.br/ans/pt-br/assuntos/consumidor/carencia)



### (b) Recorte enxuto para modelagem

Sugestão:

* Tratar “modalidade de contratação” como atributo simples:

  * `enum ModalidadeContratacao { INDIVIDUAL, COLETIVO\_EMPRESARIAL, COLETIVO\_ADESAO }`.
* Tratar “segmentação assistencial” como ponto de variação comportamental (candidato a herança ou estratégia):

  * `interface Segmentacao { boolean cobre(Procedimento, ContextoAtendimento); }`
  * Implementações: `Ambulatorial`, `HospitalarSemObstetricia`, `HospitalarComObstetricia`, `Referencia`.

Ignorar:

* Regras detalhadas de carência por modalidade; você pode ter uma configuração de carência padrão por plano e só.
* Acomodação, abrangência, subtipo de plano odontológico etc., a não ser como atributos.

Justificativa:

* Modalidade de contratação afeta mais aspectos comerciais e de relacionamento com o contratante do que a lógica clínica de autorização; OO por herança aqui tenderia a criar classes “inchadas” sem ganho real de polimorfismo.
* Segmentação, por outro lado, muda decisões de cobertura e regras de urgência/internação, o que é excelente para polimorfismo (métodos diferentes para planos ambulatoriais vs hospitalares).

\---

## Operação de autorização de procedimento (passo a passo)

Aqui está uma versão “canônica”, já pensada para virar um método central (ex.: `autorizar(procedimento, beneficiario, prestador, data, contexto)`):

1. **Receber o pedido**

   * Entradas mínimas: Beneficiário, Plano associado, Procedimento (incluindo tipo, código Rol, se exige autorização prévia), Prestador, Data do evento, indicador de urgência/emergência.[^2](https://www.gov.br/ans/pt-br/assuntos/contratacao-e-troca-de-plano/dicas-de-como-escolher-um-plano-de-saude-1/segmentacao-assistencial)[^3](https://www.gov.br/ans/pt-br/assuntos/consumidor/o-que-o-seu-plano-de-saude-deve-cobrir-1)
2. **Validar beneficiário e plano**

   * Verificar se o beneficiário está ativo e vinculado ao plano na data do evento.[^7](https://www.geap.org.br/wp-content/uploads/Guia-de-leitura-ANS.pdf)
   * Verificar se o plano está vigente (sem cancelamento) na data do evento.[^7](https://www.geap.org.br/wp-content/uploads/Guia-de-leitura-ANS.pdf)
3. **Verificar rede credenciada e abrangência**

   * Checar se o prestador está na rede credenciada do plano, dentro da área geográfica de abrangência (ex.: plano municipal não cobre prestador em outro estado).[^3](https://www.geap.org.br/wp-content/uploads/Guia-de-leitura-ANS.pdf)
   * Se não estiver, pode haver nega­tiva ou outra política (reembolso), que você pode ignorar no exercício e tratar como “negado por rede não credenciada”.
4. **Checar cobertura (Rol + segmentação)**

   * Verificar se o procedimento está no Rol ou na lista de procedimentos cobertos pelo plano.[^2](https://www.gov.br/ans/pt-br/assuntos/consumidor/o-que-o-seu-plano-de-saude-deve-cobrir-1)
   * Perguntar à segmentação se ela cobre esse tipo de procedimento no contexto (ex.: `segmentacao.cobre(procedimento, contexto)`):

     * Ambulatorial pode negar internações, mas cobrir consultas/exames.[^1](https://www.gov.br/ans/pt-br/assuntos/contratacao-e-troca-de-plano/dicas-de-como-escolher-um-plano-de-saude-1/segmentacao-assistencial)
     * Hospitalar cobre internações, e hospitalar com obstetrícia cobre parto.[^23](https://www.gov.br/ans/pt-br/assuntos/contratacao-e-troca-de-plano/dicas-de-como-escolher-um-plano-de-saude-1/segmentacao-assistencial)
   * Se não coberto: retornar decisão `NEGADO` com motivo “procedimento não coberto”.
5. **Checar carência**

   * Calcular dias de vigência do contrato até a data do evento.[^8](https://www.gov.br/ans/pt-br/assuntos/consumidor/carencia)
   * De acordo com o tipo de atendimento (urgência/emergência, parto, demais), comparar com os prazos de carência do plano:

     * Se urgência/emergência, após 24h deve haver ao menos a cobertura mínima exigida (que depende da segmentação).[^16](https://www.gov.br/ans/pt-br/assuntos/consumidor/carencia)[^1](https://www.gov.br/ans/pt-br/assuntos/contratacao-e-troca-de-plano/dicas-de-como-escolher-um-plano-de-saude-1/segmentacao-assistencial)
     * Se parto a termo, exigir até 300 dias.[^6](https://www.gov.br/ans/pt-br/assuntos/consumidor/carencia)
     * Demais situações, até 180 dias.[^8](https://www.gov.br/ans/pt-br/assuntos/consumidor/carencia)
   * Se a carência não estiver cumprida e não for urgência/emergência com regra especial, retornar `NEGADO` com motivo “carência não cumprida”.
6. **Checar autorização prévia**

   * Se o procedimento exige autorização prévia:

     * Se o fluxo atual é um pedido prévio (antes do atendimento), abrir/analisar `PedidoAutorizacao`, incluindo, se você quiser, uma validação simplificada de “critérios clínicos” (simulação de DUT).[^13](https://blog.ilovesaude.com.br/post/quando-plano-de-saude-pode-exigir-autorizacao-previa)[^2](https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos)
     * Se o atendimento está ocorrendo e não há autorização prévia válida, retornar `NEGADO` com motivo “não há autorização prévia para procedimento que a exige”.[^12](https://blog.ilovesaude.com.br/post/quando-plano-de-saude-pode-exigir-autorizacao-previa)
   * Se não exige autorização prévia, seguir para o próximo passo.
7. **Calcular coparticipação**

   * Obter o valor-base do procedimento (tabela da operadora ou do prestador; você pode abstrair isso como um parâmetro).[^15](https://legismap.com.br/component/legismap_ferramentas/?task=generatePdf.getPdf&artigo=47532&Itemid=101)[^14](https://eltonfernandes.com.br/coparticipacao-plano-de-saude)
   * Perguntar à política de coparticipação do plano qual valor o beneficiário deve pagar, dado o tipo de procedimento e o valor-base (ou apenas o tipo, se você preferir).
   * Registrar esse valor como `valorCoparticipacao`.
8. **Gerar decisão final**

   * Se chegou até aqui sem bloqueio:

     * Se `valorCoparticipacao > 0`: decisão lógica é “autorizado”, com um atributo “coparticipação = X”; em termos de interface, você pode apresentar como “AUTORIZADO COM COPARTICIPAÇÃO”.
     * Se `valorCoparticipacao == 0`: “AUTORIZADO”.
   * Se em algum ponto anterior houve bloqueio: “NEGADO”, com um motivo padronizado (cobertura, carência, autorização prévia, rede, etc.).
9. **Registrar e notificar**

   * Registrar a decisão em uma entidade de histórico (opcional no exercício).
   * Notificar prestador/beneficiário, conforme seu modelo de interação.

Em termos de modelagem, essa operação é o lugar natural para orquestrar várias “policies” ou “services” especializados (carência, cobertura, coparticipação, autorização prévia), o que se presta muito bem a testes unitários e uso de interfaces/abstrações.

\---

## Mapa domínio → modelagem (dado, comportamento, regra; herança x associação)

### Tabela de conceitos principais

|Conceito (real)|Papel no sistema didático|Herança/polimorfismo ou associação?|Comentário de modelagem|
|-|-|-|-|
|Beneficiário|Entidade de domínio (estado próprio)|Associação com Plano|Armazena dados de identificação, status (ativo/inativo), data de adesão, possivelmente vínculos familiares.|
|Plano de saúde (contrato)|Entidade agregadora|Associação com Segmentação, Modalidade, Política de Carência, Política de Coparticipação|É o núcleo de configuração de regras; raramente se beneficia de herança diretamente.|
|Modalidade de contratação (individual, coletivo etc.)|Atributo de plano|Apenas `enum` (dado)|Muda regras de carência/reajuste, mas você provavelmente não vai modelar isso em profundidade; não justifica subclasses.|
|Segmentação assistencial (ambulatorial, hospitalar, referência)|Ponto de variação de comportamento|Bom candidato a herança/estratégia (`Segmentacao` com subclasses)|Muda `cobre(procedimento)` e regras de urgência/internação; forte impacto na autorização.|
|Área de abrangência (nacional, estadual etc.)|Atributo de plano|`enum` + lógica simples em serviço|Regra de rede/geo é simples: “prestador está dentro da abrangência?”; não precisa polimorfismo.|
|Rede credenciada (prestadores)|Coleção associada ao plano|Associação (Plano–Prestador)|Entidades `Prestador`, `Hospital`, `Laboratorio` podem existir, mas comportam-se de modo semelhante no seu recorte; herança é opcional.|
|Procedimento (item do Rol)|Entidade de domínio|Pode ter subtipos por tipo (consulta, exame, cirurgia) se você quiser, mas não é obrigatório|Dados: código Rol, descrição, tipo, flag `requerAutorizacaoPrevia`, conjunto de segmentações que o cobrem.|
|Rol de Procedimentos ANS|Tabela de referência|Repositório/tabela estática|É uma fonte de dados, não um objeto com comportamento sofisticado.|
|Carência|Regra de decisão temporal|Estratégia associada ao plano|Interface `PoliticaCarencia` com variações simples; pode ser tabelada por tipo de procedimento.|
|Doença/lesão preexistente (CPT)|Regra complexa opcional|Melhor ignorar ou representar como simples flag|Se for modelar, vale mais como regra em um `ValidadorCPT` do que como herança.|
|Coparticipação|Regra de cálculo financeiro|Forte candidato a estratégia polimórfica|Interface `PoliticaCoparticipacao` (sem, percentual, fixo por tipo).|
|Autorização prévia (processo)|Fluxo de estado|Entidade `PedidoAutorizacao` com estados|O comportamento varia pouco por tipo de plano; fluxo diferente é guiado pelo `Procedimento.requerAutorizacaoPrevia`.|
|DUT (Diretrizes de Utilização)|Regras clínicas específicas|Tabelas/regra de decisão pontual; manter fora no recorte enxuto|São regras por procedimento com forte dependência clínica; melhor fingir que são um `boolean` de “critérios atendidos?”.|
|Urgência/emergência|Atributo do contexto de atendimento|Dado + condicionador de regras de carência e cobertura|Pode ser um `enum` no `ContextoAtendimento`; influencia lógica de carência e segmentação.|
|Atendimento (execução do procedimento)|Entidade de domínio transacional|Associação entre Beneficiário, Prestador, Procedimento, Autorização|Guarda o resultado da decisão (autorizado/negado, valor de coparticipação) e referência ao pedido de autorização, se houver.|
|Histórico de utilização (para tetos de coparticipação)|Estado agregado ao beneficiário/plano|Fora do escopo enxuto|Se um dia você modelar tetos, vira storage associado ao beneficiário por período.|

### Onde usar herança/polimorfismo vs associação

Boas candidatas a herança/estratégia:

* `Segmentacao`: encapsular as diferenças entre ambulatorial, hospitalar com/sem obstetrícia e referência em métodos que respondem “cobre ou não cobre este procedimento neste contexto?”.
* `PoliticaCoparticipacao`: encapsular as diferentes fórmulas de cálculo (sem copart., percentual, valor fixo por tipo).
* (Opcional) `PoliticaCarencia`: permitir que diferentes planos ou linhas de produtos apliquem regras distintas de carência com a mesma interface.

Melhor como associação/atributo:

* `ModalidadeContratacao`: afeta poucos pontos do fluxo de autorização; modelar como subclasses (PlanoIndividual, PlanoColetivoEmpresarial etc.) tende a gerar código duplicado.
* `AreaAbrangencia`, `Acomodacao`, `AdministradoraDeBeneficios`: atributos descritivos, usados só em checks simples ou fora da operação central.
* `RedeCredenciada`: associação Plano–Prestador; o comportamento de Prestador não varia o bastante para pedir herança.

Boas “regras de decisão” separadas:

* `RegraCobertura` (usa Rol + Segmentacao).
* `RegraCarencia` (usa datas + tipo de procedimento + contexto urgência/emergência).
* `RegraAutorizacaoPrevia` (usa flag do procedimento + eventual informação clínica).
* `RegraCoparticipacao` (já vista).

Essas regras podem ser:

* Métodos em serviços de domínio (ex.: `ServicoAutorizacao`) que recebem `Pl ano`, `Beneficiario`, `Procedimento`, `Contexto`.
* Ou objetos de política injetados em `Plano`, se você quiser um estilo mais DDD.



