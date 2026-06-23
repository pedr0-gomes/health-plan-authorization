package br.ufca.autorizacao.dominio;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SegmentacaoTest {

    @Test
    void ambulatorialCobreConsulta() {
        Segmentacao segmentacao = new Ambulatorial("HMSVP", "Hospital Maternidade São Vicente de Paulo");
        Procedimento procedimento = new Procedimento("222", "Otorrino", TipoProcedimento.CONSULTA);
        assertTrue(segmentacao.cobre(procedimento));
    }

    @Test
    void ambulatorialNaoCobreCirurgia() {
        Segmentacao segmentacao = new Ambulatorial("Amb", "Ambulatorial");
        Procedimento procedimento = new Procedimento("333", "Apendicectomia", TipoProcedimento.CIRURGIA);
        assertFalse(segmentacao.cobre(procedimento));
    }

    @Test
    void hsoCobreCirurgia() {
        Segmentacao segmentacao = new HospitalarSemObstetricia("HSO", "Hospitalar sem obstetrícia");
        Procedimento procedimento = new Procedimento("333", "Apendicectomia", TipoProcedimento.CIRURGIA);
        assertTrue(segmentacao.cobre(procedimento));
    }

    @Test
    void hsoNaoCobreParto() {
        Segmentacao segmentacao = new HospitalarSemObstetricia("HSO", "Hospitalar sem obstetrícia");
        Procedimento procedimento = new Procedimento("444", "Parto normal", TipoProcedimento.PARTO);
        assertFalse(segmentacao.cobre(procedimento));
    }

    @Test
    void hcoCobreParto() {
        Segmentacao segmentacao = new HospitalarComObstetricia("HCO", "Hospitalar com obstetrícia");
        Procedimento procedimento = new Procedimento("444", "Parto normal", TipoProcedimento.PARTO);
        assertTrue(segmentacao.cobre(procedimento));
    }

    @Test
    void referenciaCobreParto() {
        Segmentacao segmentacao = new Referencia("Ref", "Plano referência");
        Procedimento procedimento = new Procedimento("444", "Parto normal", TipoProcedimento.PARTO);
        assertTrue(segmentacao.cobre(procedimento));
    }

}
