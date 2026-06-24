package br.ufca.autorizacao.dominio;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import br.ufca.autorizacao.excecao.DadosInvalidosException;

class BeneficiarioTest {

    @Test
    void dataAdesaoNulaExcecao() {
        assertThrows(DadosInvalidosException.class, () -> new Beneficiario("Pedro", null, new Plano("Pedro", new HospitalarComObstetricia("HCO", "Cobre tudo"), new SemCoparticipacao(), 300, 180)));
    }
}
