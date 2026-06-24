package br.ufca.autorizacao.dominio;

public class CoparticipacaoPercentual implements PoliticaCoparticipacao {
    private final double percentual;
    public CoparticipacaoPercentual(double percentual) {
        this.percentual = percentual;
    }

    @Override
    public double calcular(Procedimento p) {
        return p.getValorBase()*this.percentual;
    }
}
