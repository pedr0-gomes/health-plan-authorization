package br.ufca.autorizacao.repositorio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;


import br.ufca.autorizacao.dominio.Procedimento;
import br.ufca.autorizacao.dominio.TipoProcedimento;
import br.ufca.autorizacao.excecao.ProcedimentoNaoEncontradoException;

public class RepositorioProcedimentoTest {
    @Test
    void buscaProcedimentoCodigoExistente() {
        Procedimento procedimento = new Procedimento("999","Cirurgia qualquer",TipoProcedimento.CIRURGIA, false, 100.0);
        RepositorioProcedimento repositorio = new RepositorioProcedimento();
        repositorio.adicionar(procedimento);
        assertEquals(procedimento, repositorio.buscar("999"));
    
    }

    @Test
    void buscaProcedimentoCodigoInexistente() {
        RepositorioProcedimento repositorio = new RepositorioProcedimento();
        assertThrows(ProcedimentoNaoEncontradoException.class,() -> repositorio.buscar("626"));
    
    }

    @Test
    void buscaProcedimentoTipoExistente() {
        Procedimento procedimento = new Procedimento("999","Cirurgia qualquer",TipoProcedimento.CIRURGIA, false, 100.0);
        RepositorioProcedimento repositorio = new RepositorioProcedimento();
        repositorio.adicionar(procedimento);
        assertEquals(List.of(procedimento), repositorio.buscar(TipoProcedimento.CIRURGIA));
        
    }

    @Test
    void buscaProcedimentoTipoInexistente() {
        RepositorioProcedimento repositorio = new RepositorioProcedimento();
        assertTrue(repositorio.buscar(TipoProcedimento.CIRURGIA).isEmpty());
        
    }
}
