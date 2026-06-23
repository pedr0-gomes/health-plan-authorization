package br.ufca.autorizacao.dominio;

public class HospitalarComObstetricia extends Segmentacao {
    
    public HospitalarComObstetricia(String nome,String descricao) {
        super(nome, descricao);
    }

    @Override
    public boolean cobre(Procedimento p) {
        return true;
    }

}