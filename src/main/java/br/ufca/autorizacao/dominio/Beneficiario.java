package br.ufca.autorizacao.dominio;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import br.ufca.autorizacao.excecao.DadosInvalidosException;


public class Beneficiario {
    private final String nome;
    private final  LocalDate dataAdesao;
    private final Plano plano;

    public Beneficiario(String nome,LocalDate dataAdesao,Plano plano) {
        this.nome = nome;
        if (dataAdesao == null) {
            throw new DadosInvalidosException("Data inválida");
        }
        this.dataAdesao = dataAdesao;
        this.plano = plano;
    }

    public Plano getPlano() {
        return this.plano;
    }

    public int diasDecorridos(LocalDate dataEvento) {
        return (int) ChronoUnit.DAYS.between(this.dataAdesao, dataEvento);
    }

    public String getNome() {
        return this.nome;
    }
}
