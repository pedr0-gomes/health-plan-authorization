package br.ufca.autorizacao.excecao;

public class BeneficiarioNaoEncontradoException extends RuntimeException {
    public BeneficiarioNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
