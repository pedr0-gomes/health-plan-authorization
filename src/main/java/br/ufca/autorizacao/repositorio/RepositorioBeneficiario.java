package br.ufca.autorizacao.repositorio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.ufca.autorizacao.dominio.Beneficiario;
import br.ufca.autorizacao.excecao.BeneficiarioNaoEncontradoException;

public class RepositorioBeneficiario {
    private final List<Beneficiario> list = new ArrayList<>();
    
    public void adicionar(Beneficiario beneficiario) {
        this.list.add(beneficiario);
    }

    public List<Beneficiario> listar() {
        return Collections.unmodifiableList(this.list);
    }

    public Beneficiario buscar(String nome) {
        for (Beneficiario beneficiario : this.list) {
            if (nome.equals(beneficiario.getNome())) {
                return beneficiario;
            }
        }
        throw new BeneficiarioNaoEncontradoException("Beneficiário nomeado por " + nome + " não encontrado");
    }
}
