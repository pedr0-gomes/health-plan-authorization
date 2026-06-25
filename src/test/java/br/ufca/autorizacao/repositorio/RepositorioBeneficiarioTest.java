package br.ufca.autorizacao.repositorio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import br.ufca.autorizacao.dominio.Beneficiario;
import br.ufca.autorizacao.dominio.CoparticipacaoPercentual;
import br.ufca.autorizacao.dominio.HospitalarComObstetricia;
import br.ufca.autorizacao.dominio.Plano;
import br.ufca.autorizacao.excecao.BeneficiarioNaoEncontradoException;

public class RepositorioBeneficiarioTest {
    @Test
    void buscaBeneficiarioExistente() {
        Plano plano = new Plano("Pedro", new HospitalarComObstetricia("HCO", "Cobre tudo"), new CoparticipacaoPercentual(0.2), 180, 180);
        Beneficiario beneficiario = new Beneficiario("Joao",LocalDate.of(2024,1,1),plano);
        RepositorioBeneficiario repositorio = new RepositorioBeneficiario();
        repositorio.adicionar(beneficiario);
        assertEquals(beneficiario, repositorio.buscar("Joao"));
    }

    @Test
    void buscaBeneficiarioInexistente() {
        RepositorioBeneficiario repositorio = new RepositorioBeneficiario();
        assertThrows(BeneficiarioNaoEncontradoException.class,() -> repositorio.buscar("Maria"));
    }
}
