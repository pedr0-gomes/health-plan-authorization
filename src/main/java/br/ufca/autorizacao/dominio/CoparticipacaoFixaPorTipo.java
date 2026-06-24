package br.ufca.autorizacao.dominio;

import java.util.Map;

public class CoparticipacaoFixaPorTipo implements PoliticaCoparticipacao {
    private final Map<TipoProcedimento,Double> tabela;

    public CoparticipacaoFixaPorTipo(Map<TipoProcedimento,Double> tabela) {
        this.tabela = tabela;
    }

    @Override
    public double calcular(Procedimento p) {
        return tabela.getOrDefault(p.getTipo(),0.0);
    }

}
