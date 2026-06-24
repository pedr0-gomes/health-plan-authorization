package br.ufca.autorizacao.repositorio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.ufca.autorizacao.dominio.Plano;
import br.ufca.autorizacao.excecao.PlanoNaoEncontradoException;

public class RepositorioPlano {
    private final List<Plano> list = new ArrayList<>();
    
    public void adicionar(Plano plano) {
        this.list.add(plano);
    }

    public Plano buscar(String nome) {
        for (Plano plano : this.list) {
            if (nome.equals(plano.getNome())) {
                return plano;
            }
        }
        throw new PlanoNaoEncontradoException("Plano nomeado por " + nome + " não encontrado");
    }

    public List<Plano> listar() {
        return Collections.unmodifiableList(this.list);
    }
}
