package br.ufca.autorizacao.excecao;

public class ProcedimentoNaoEncontradoException extends RuntimeException {
    public ProcedimentoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
