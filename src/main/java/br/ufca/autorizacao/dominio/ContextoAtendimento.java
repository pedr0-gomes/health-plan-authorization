package br.ufca.autorizacao.dominio;

import java.time.LocalDate;

public class ContextoAtendimento {
    private final LocalDate dataEvento;
    private final boolean urgencia;
    private final boolean autorizacaoPreviaConcedida;

    public ContextoAtendimento(LocalDate dataEvento,boolean urgencia,boolean autorizacaoPreviaConcedida) {
        this.dataEvento = dataEvento;
        this.urgencia = urgencia;
        this.autorizacaoPreviaConcedida = autorizacaoPreviaConcedida;
    }

    public ContextoAtendimento(LocalDate dataEvento,boolean urgencia) {
        this(dataEvento,urgencia,false);
    }

    public ContextoAtendimento(LocalDate dataEvento) {
        this(dataEvento, false,false);
    }

    public LocalDate getDataEvento() {
        return this.dataEvento;
    }

    public boolean isUrgencia() {
        return urgencia;
    }

    public boolean isAutorizacaoPreviaConcedida() {
        return this.autorizacaoPreviaConcedida;
    }
}
