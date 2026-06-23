package br.ufca.autorizacao.dominio;

public class Referencia extends Segmentacao {
    
    public Referencia(String nome,String descricao) {
        super(nome, descricao);
    }

    @Override
    public boolean cobre(Procedimento p) {
        return true;
    }

}