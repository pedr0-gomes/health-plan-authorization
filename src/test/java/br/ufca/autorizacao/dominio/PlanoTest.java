package br.ufca.autorizacao.dominio;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlanoTest {

    @Test
    void consultaCom181DiasCumpreCarencia() {
        // 1. Arrange (SEU): cria o Plano com carenciaDemais = 180 (é o 4º argumento).
        Plano plano = new Plano("Pedro", new HospitalarComObstetricia("HCO", "Cobre tudo"), 180, 180);

        // 2. Act (SEU): chama carenciaCumprida com o tipo CONSULTA e 181 dias.
        boolean resultado = plano.carenciaCumprida(TipoProcedimento.CONSULTA,181,false);

        // 3. Assert (SEU): faz o teste falhar sozinho se resultado for false.
        assertTrue(resultado);
    }

    @Test
    void demaisCom179NaoDiasCumpreCarencia() {
        Plano plano = new Plano("Pedro", new HospitalarComObstetricia("HCO", "Cobre tudo"), 180, 180);

        boolean resultado = plano.carenciaCumprida(TipoProcedimento.CONSULTA,179,false);

        assertFalse(resultado);
    }

    @Test
    void demaisCom180DiasCumpreCarencia() {
        Plano plano = new Plano("Pedro", new HospitalarComObstetricia("HCO", "Cobre tudo"), 180, 180);

        boolean resultado = plano.carenciaCumprida(TipoProcedimento.CONSULTA,180,false);

        assertTrue(resultado);
    }

    @Test
    void partoCom301DiasCumpreCarencia() {
        Plano plano = new Plano("Pedro", new HospitalarComObstetricia("HCO", "Cobre tudo"), 300, 180);

        boolean resultado = plano.carenciaCumprida(TipoProcedimento.PARTO,301,false);

        assertTrue(resultado);
    }

    @Test
    void partoCom299DiasNaoCumpreCarencia() {
        Plano plano = new Plano("Pedro", new HospitalarComObstetricia("HCO", "Cobre tudo"), 300, 180);

        boolean resultado = plano.carenciaCumprida(TipoProcedimento.PARTO,299,false);

        assertFalse(resultado);
    }

    @Test
    void partoCom299ComUrgenciaCobrecarencia() {
        Plano plano = new Plano("Pedro", new HospitalarComObstetricia("HCO", "Cobre tudo"), 300, 180);

        boolean resultado = plano.carenciaCumprida(TipoProcedimento.PARTO,299,true);

        assertTrue(resultado);
    }

    @Test
    void partoCom299ComUrgenciaNaoCobrecarencia() {
        Plano plano = new Plano("Pedro", new HospitalarComObstetricia("HCO", "Cobre tudo"), 300, 180);

        boolean resultado = plano.carenciaCumprida(TipoProcedimento.PARTO,0,true);

        assertFalse(resultado);

    }


}
