package br.ufca.autorizacao.dominio;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


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

    public int diasDecorridos(LocalDate dataEvento) {
        return (int) ChronoUnit.DAYS.between(this.dataAdesao, dataEvento);
    }
}
