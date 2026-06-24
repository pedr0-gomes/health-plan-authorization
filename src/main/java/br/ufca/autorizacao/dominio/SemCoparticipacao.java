package br.ufca.autorizacao.dominio;

public class SemCoparticipacao implements PoliticaCoparticipacao {
    public SemCoparticipacao() {

    }

    @Override
    public double calcular(Procedimento p) {
        return 0;
    }
}
