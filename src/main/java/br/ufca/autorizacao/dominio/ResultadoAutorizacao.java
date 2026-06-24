package br.ufca.autorizacao.dominio;

public class ResultadoAutorizacao {
    private final Decisao decisao; 
    private final String motivo;
    private final double valorCoparticipacao;

    public ResultadoAutorizacao(Decisao decisao,String motivo,double valorCoparticipacao) {
        this.decisao = decisao;
        this.motivo = motivo;
        this.valorCoparticipacao = valorCoparticipacao;
    }

    public ResultadoAutorizacao(Decisao decisao,String motivo) {
        this(decisao, motivo, 0);
    }

    public Decisao getDecisao() {
        return this.decisao;
    }

    public double getValorCoparticipacao() {
        return this.valorCoparticipacao;
    }
}
