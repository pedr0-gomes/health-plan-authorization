package br.ufca.autorizacao.repositorio;

import org.junit.jupiter.api.Test;

import br.ufca.autorizacao.dominio.CoparticipacaoPercentual;
import br.ufca.autorizacao.dominio.HospitalarComObstetricia;
import br.ufca.autorizacao.dominio.Plano;
import br.ufca.autorizacao.excecao.PlanoNaoEncontradoException;

import static org.junit.jupiter.api.Assertions.*;

class RepositorioPlanoTest {

    @Test
    void buscaPlanoExistente() {
        Plano plano = new Plano("Pedro", new HospitalarComObstetricia("HCO", "Cobre tudo"), new CoparticipacaoPercentual(0.2), 180, 180);
        RepositorioPlano repositorio = new RepositorioPlano();
        repositorio.adicionar(plano);
        assertEquals(plano, repositorio.buscar("Pedro"));
    }

    @Test
    void buscaPlanoInexistente() {
        RepositorioPlano repositorio = new RepositorioPlano();
        assertThrows(PlanoNaoEncontradoException.class,() -> repositorio.buscar("Maria"));
    }
}
