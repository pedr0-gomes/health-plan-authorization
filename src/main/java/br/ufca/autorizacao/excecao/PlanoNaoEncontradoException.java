package br.ufca.autorizacao.excecao;

public class PlanoNaoEncontradoException extends RuntimeException {
    public PlanoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
