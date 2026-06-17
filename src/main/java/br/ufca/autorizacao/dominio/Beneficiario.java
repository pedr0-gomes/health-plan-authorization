package br.ufca.autorizacao.dominio;

import java.time.LocalDate;

public class Beneficiario {
    private final String nome;
    private final  LocalDate dataAdesao;
    private final Plano plano;

    public Beneficiario(String nome,LocalDate dataAdesao,Plano plano) {
        this.nome = nome;
        this.dataAdesao = dataAdesao;
        this.plano = plano;
    }

    public Plano getPlano() {
        return this.plano;
    }
}
