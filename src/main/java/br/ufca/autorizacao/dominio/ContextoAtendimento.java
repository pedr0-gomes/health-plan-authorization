package br.ufca.autorizacao.dominio;

import java.time.LocalDate;

public class ContextoAtendimento {
    private final LocalDate dataEvento;

    public ContextoAtendimento(LocalDate dataEvento) {
        this.dataEvento = dataEvento;
    }
}
