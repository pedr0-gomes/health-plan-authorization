package br.ufca.autorizacao.dominio;

public class Ambulatorial extends Segmentacao {
    
    public Ambulatorial(String nome,String descricao) {
        super(nome, descricao);
    }

    @Override
    public boolean cobre(Procedimento p) {
        return p.getTipo() == TipoProcedimento.CONSULTA || p.getTipo() == TipoProcedimento.EXAME;
    }

}