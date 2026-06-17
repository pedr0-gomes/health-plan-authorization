package br.ufca.autorizacao.dominio;

public class ResultadoAutorizacao {
    private final Decisao decisao; 
    private final String motivo;

    ResultadoAutorizacao(Decisao decisao,String motivo) {
        this.decisao = decisao;
        this.motivo = motivo;
    }
}
