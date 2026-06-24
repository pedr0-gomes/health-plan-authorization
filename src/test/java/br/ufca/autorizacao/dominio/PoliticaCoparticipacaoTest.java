package br.ufca.autorizacao.dominio;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class PoliticaCoparticipacaoTest {
    @Test
    void SemCoparticipacao() {
        Plano plano = new Plano("Pedro", new HospitalarComObstetricia("HCO", "Cobre tudo"), new SemCoparticipacao(), 180, 180);
        Procedimento p = new Procedimento(null, null, null, false, 0);
        double resultado = plano.calcularCopart(p);

        assertEquals(0.0,resultado);
    }

    @Test
    void CoparticipacaoPercentual() {
        Plano plano = new Plano("Pedro", new HospitalarComObstetricia("HCO", "Cobre tudo"), new CoparticipacaoPercentual(0.2), 180, 180);
        Procedimento p = new Procedimento(null, null, null, false, 100);
        double resultado = plano.calcularCopart(p);

        assertEquals(20.0,resultado,0.001);
    }

    @Test
    void CoparticipacaoFixaPorTipo() {
        Map <TipoProcedimento,Double> tabela = new HashMap<>();
        tabela.put(TipoProcedimento.CONSULTA,30.0);
        Plano plano = new Plano("Pedro", new HospitalarComObstetricia("HCO", "Cobre tudo"), new CoparticipacaoFixaPorTipo(tabela), 180, 180);
        Procedimento p = new Procedimento(null, null, TipoProcedimento.CONSULTA, false, 0);
        double resultado = plano.calcularCopart(p);

        assertEquals(30.0,resultado);
    }
    
}
