package br.ufca.autorizacao.dominio;

public abstract class Segmentacao {
    private final String nome;
    private final String descricao;

    protected  Segmentacao(String nome,String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }
    public abstract boolean cobre(Procedimento p);

    @Override
    public String toString() {
        return "Segmentação{nome='" + nome + "', descrição=" + descricao + "}";

    }
}