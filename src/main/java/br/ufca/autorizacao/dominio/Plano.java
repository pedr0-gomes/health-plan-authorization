package br.ufca.autorizacao.dominio;

public class Plano {
    private String nome;
    private final int carenciaUrgencia;
    private final int carenciaParto;
    private final int carenciaDemais;

    public Plano(String nome,int carenciaUrgencia,int carenciaParto,int carenciaDemais) {
        this.nome = nome;
        this.carenciaUrgencia = carenciaUrgencia;
        this.carenciaParto = carenciaParto;
        this.carenciaDemais = carenciaDemais;
    }
}
