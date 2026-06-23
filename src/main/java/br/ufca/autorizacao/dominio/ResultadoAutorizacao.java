package br.ufca.autorizacao.dominio;

public class ResultadoAutorizacao {
    private final Decisao decisao; 
    private final String motivo;

    public ResultadoAutorizacao(Decisao decisao,String motivo) {
        this.decisao = decisao;
        this.motivo = motivo;
    }

    public Decisao getDecisao() {
        return this.decisao;
    }
}
