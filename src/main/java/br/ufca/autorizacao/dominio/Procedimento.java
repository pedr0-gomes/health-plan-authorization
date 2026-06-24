package br.ufca.autorizacao.dominio;

import br.ufca.autorizacao.excecao.DadosInvalidosException;

public class Procedimento {
    private final String codigo;
    private final String descricao;
    private final TipoProcedimento tipo;
    private final boolean requerAutorizacaoPrevia;
    private final double valorBase;

    public Procedimento(String codigo,String descricao,TipoProcedimento tipo,boolean requerAutorizacaoPrevia,double valorBase) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.tipo = tipo;
        this.requerAutorizacaoPrevia = requerAutorizacaoPrevia;
        if (valorBase < 0) {
            throw new DadosInvalidosException("Valor Base inválido");
        }
        this.valorBase = valorBase;
    }

    public TipoProcedimento getTipo() {
        return this.tipo;
    }

    public boolean isRequerAutorizacaoPrevia() {
         return this.requerAutorizacaoPrevia;
    }

    public double getValorBase() {
        return this.valorBase;
    }

    public String getCodigo() {
        return this.codigo;
    }
}
