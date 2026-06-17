package br.ufca.autorizacao.dominio;

public class Procedimento {
    private final String codigo;
    private final String descricao;
    private final TipoProcedimento tipo;

    Procedimento(String codigo,String descricao,TipoProcedimento tipo) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.tipo = tipo;
    }
}
