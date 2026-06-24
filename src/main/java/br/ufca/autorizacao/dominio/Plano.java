package br.ufca.autorizacao.dominio;

import br.ufca.autorizacao.excecao.DadosInvalidosException;

public class Plano {
    private String nome;
    private final Segmentacao segmentacao;
    private final int carenciaParto;
    private final int carenciaDemais;
    private final PoliticaCoparticipacao politicaCoparticipacao;

    public Plano(String nome,Segmentacao segmentacao,PoliticaCoparticipacao politicaCoparticipacao,int carenciaParto,int carenciaDemais) {
        this.nome = nome;
        this.segmentacao = segmentacao;
        if (carenciaParto < 0) {
            throw new DadosInvalidosException("Carência de Parto Inválida");
        }
        if (carenciaDemais < 0) {
            throw new DadosInvalidosException("Carência Demais Inválida");
        }
        this.carenciaParto = carenciaParto;
        this.carenciaDemais = carenciaDemais;
        this.politicaCoparticipacao = politicaCoparticipacao;
    }

    public boolean carenciaCumprida(TipoProcedimento tipo,int dias,boolean urgencia) {
        if (urgencia)   return dias>=1;

        int prazo = switch(tipo) {
        case PARTO->carenciaParto;
        default->carenciaDemais;
        };

        return dias >= prazo;
    }

    public boolean cobre(Procedimento p) {
        return segmentacao.cobre(p);
    }

    public double calcularCopart(Procedimento p) {
        return this.politicaCoparticipacao.calcular(p);
    }

    public String getNome() {
        return this.nome;
    }
}
