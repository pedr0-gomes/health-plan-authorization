package br.ufca.autorizacao.dominio;

import java.time.LocalDate;

public class ContextoAtendimento {
    private final LocalDate dataEvento;
    private final boolean urgencia;

    public ContextoAtendimento(LocalDate dataEvento,boolean urgencia) {
        this.dataEvento = dataEvento;
        this.urgencia = urgencia;
    }

    public LocalDate getDataEvento() {
        return this.dataEvento;
    }

    public boolean isUrgencia() {
        return urgencia;
    }
}
