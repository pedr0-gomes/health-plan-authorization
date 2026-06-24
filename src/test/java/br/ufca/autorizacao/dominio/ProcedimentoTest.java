package br.ufca.autorizacao.dominio;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import br.ufca.autorizacao.excecao.DadosInvalidosException;

class ProcedimentoTest {

    @Test
    void valorBaseNegativoExcecao() {
        assertThrows(DadosInvalidosException.class, () -> new Procedimento("724", null, null, false, -20));
    }
}
