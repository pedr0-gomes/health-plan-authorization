package br.ufca.autorizacao.dominio;

public class HospitalarSemObstetricia extends Segmentacao {
    
    public HospitalarSemObstetricia(String nome,String descricao) {
        super(nome, descricao);
    }

    @Override
    public boolean cobre(Procedimento p) {
        return p.getTipo() != TipoProcedimento.PARTO;
    }

}