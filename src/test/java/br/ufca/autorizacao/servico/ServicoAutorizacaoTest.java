package br.ufca.autorizacao.servico;

import org.junit.jupiter.api.Test;

import br.ufca.autorizacao.dominio.Beneficiario;
import br.ufca.autorizacao.dominio.ContextoAtendimento;
import br.ufca.autorizacao.dominio.Ambulatorial;
import br.ufca.autorizacao.dominio.Decisao;
import br.ufca.autorizacao.dominio.HospitalarComObstetricia;
import br.ufca.autorizacao.dominio.Plano;
import br.ufca.autorizacao.dominio.Procedimento;
import br.ufca.autorizacao.dominio.ResultadoAutorizacao;
import br.ufca.autorizacao.dominio.TipoProcedimento;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

class ServicoAutorizacaoTest {

    @Test
    void ServicoCarenciaAutorizado() {
        Plano plano = new Plano("Unimed", new HospitalarComObstetricia("HCO", "Cobre tudo"), 300, 180);
        Beneficiario beneficiario = new Beneficiario("Pedro",LocalDate.of(2024,1,1),plano);
        Procedimento procedimento = new Procedimento("626-623","Algumacoisatomia I",TipoProcedimento.CIRURGIA, false);
        ContextoAtendimento contextoAtendimento = new ContextoAtendimento(LocalDate.of(2024,8,1), false);

        ServicoAutorizacao servico = new ServicoAutorizacao();
        ResultadoAutorizacao resultado = servico.autorizar(beneficiario, procedimento, contextoAtendimento);

        assertEquals(Decisao.AUTORIZADO,resultado.getDecisao());
    }

    @Test
    void ServicoCarenciaNegado() {
        Plano plano = new Plano("Unimed", new HospitalarComObstetricia("HCO", "Cobre tudo"), 300, 180);
        Beneficiario beneficiario = new Beneficiario("Maria",LocalDate.of(2024,1,1),plano);
        Procedimento procedimento = new Procedimento("622","Algumacoisatomia II",TipoProcedimento.CIRURGIA, false);
        ContextoAtendimento contextoAtendimento = new ContextoAtendimento(LocalDate.of(2024,2,1), false);

        ServicoAutorizacao servico = new ServicoAutorizacao();
        ResultadoAutorizacao resultado = servico.autorizar(beneficiario, procedimento, contextoAtendimento);

        assertEquals(Decisao.NEGADO_CARENCIA,resultado.getDecisao());
    }

    @Test
    void ServicoCoberturaNegado() {
        // Ambulatorial cobre só CONSULTA/EXAME; CIRURGIA não é coberta.
        // Carência cumprida de propósito (adesão 2024-01-01 → evento 2024-08-01, > 180 dias):
        // se a cobertura não cortasse, sairia AUTORIZADO. Como sai NEGADO_COBERTURA, foi a cobertura.
        Plano plano = new Plano("Unimed", new Ambulatorial("Amb", "Ambulatorial"), 300, 180);
        Beneficiario beneficiario = new Beneficiario("Joao",LocalDate.of(2024,1,1),plano);
        Procedimento procedimento = new Procedimento("999","Cirurgia qualquer",TipoProcedimento.CIRURGIA, false);
        ContextoAtendimento contextoAtendimento = new ContextoAtendimento(LocalDate.of(2024,8,1), false);

        ServicoAutorizacao servico = new ServicoAutorizacao();
        ResultadoAutorizacao resultado = servico.autorizar(beneficiario, procedimento, contextoAtendimento);

        assertEquals(Decisao.NEGADO_COBERTURA,resultado.getDecisao());
    }

    @Test
    void ServicoAutorizacaoNegada() {
        Plano plano = new Plano("Unimed", new Ambulatorial("Amb", "Ambulatorial"), 300, 180);
        Beneficiario beneficiario = new Beneficiario("Joao",LocalDate.of(2024,1,1),plano);
        Procedimento procedimento = new Procedimento("999","Cirurgia qualquer",TipoProcedimento.CONSULTA, true);
        ContextoAtendimento contextoAtendimento = new ContextoAtendimento(LocalDate.of(2024,8,1), false,false);

        ServicoAutorizacao servico = new ServicoAutorizacao();
        ResultadoAutorizacao resultado = servico.autorizar(beneficiario, procedimento, contextoAtendimento);

        assertEquals(Decisao.NEGADO_AUTORIZACAO_PREVIA,resultado.getDecisao());
    }

    @Test
    void ServicoAutorizacaoConcedido() {
        Plano plano = new Plano("Unimed", new Ambulatorial("Amb", "Ambulatorial"), 300, 180);
        Beneficiario beneficiario = new Beneficiario("Joao",LocalDate.of(2024,1,1),plano);
        Procedimento procedimento = new Procedimento("999","Cirurgia qualquer",TipoProcedimento.CONSULTA, true);
        ContextoAtendimento contextoAtendimento = new ContextoAtendimento(LocalDate.of(2024,8,1), false,true);

        ServicoAutorizacao servico = new ServicoAutorizacao();
        ResultadoAutorizacao resultado = servico.autorizar(beneficiario, procedimento, contextoAtendimento);

        assertEquals(Decisao.AUTORIZADO,resultado.getDecisao());
    }

}
