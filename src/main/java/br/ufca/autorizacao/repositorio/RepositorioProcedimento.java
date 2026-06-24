package br.ufca.autorizacao.repositorio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.ufca.autorizacao.dominio.Procedimento;
import br.ufca.autorizacao.dominio.TipoProcedimento;
import br.ufca.autorizacao.excecao.ProcedimentoNaoEncontradoException;

public class RepositorioProcedimento {
    private final List<Procedimento> list = new ArrayList<>();

    public void adicionar(Procedimento procedimento) {
        this.list.add(procedimento);
    }

    public List<Procedimento> listar() {
        return Collections.unmodifiableList(this.list);
    }

    public Procedimento buscar(String codigo) {
        for (Procedimento procedimento : this.list) {
            if (codigo.equals(procedimento.getCodigo())) {
                return procedimento;
            }
        }
        throw new ProcedimentoNaoEncontradoException("Procedimento com código " + codigo + " não encontrado");
    }

    public List<Procedimento> buscar(TipoProcedimento tipo) {
        List<Procedimento> resultado = new ArrayList<>();
        for (Procedimento procedimento : this.list) {
            if (tipo.equals(procedimento.getTipo())) {
                resultado.add(procedimento);
            }
        }
        return resultado;
    }
}
