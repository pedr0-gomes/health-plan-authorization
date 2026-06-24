package br.ufca.autorizacao.servico;

import br.ufca.autorizacao.dominio.Beneficiario;
import br.ufca.autorizacao.dominio.ContextoAtendimento;
import br.ufca.autorizacao.dominio.Decisao;
import br.ufca.autorizacao.dominio.Plano;
import br.ufca.autorizacao.dominio.Procedimento;
import br.ufca.autorizacao.dominio.ResultadoAutorizacao;
import br.ufca.autorizacao.dominio.TipoProcedimento;

public class ServicoAutorizacao {
    public ResultadoAutorizacao autorizar(Beneficiario beneficiario, Procedimento procedimento, ContextoAtendimento contextoAtendimento) {
        TipoProcedimento tipo = procedimento.getTipo();
        boolean urgencia = contextoAtendimento.isUrgencia();
        if (!beneficiario.getPlano().cobre(procedimento)) {
            ResultadoAutorizacao resultado = new ResultadoAutorizacao(Decisao.NEGADO_COBERTURA,"Plano não cobre");
            return resultado;
        }
        int dias = beneficiario.diasDecorridos(contextoAtendimento.getDataEvento()); 
        boolean resposta = beneficiario.getPlano().carenciaCumprida(tipo,dias,urgencia);
        if (!resposta) {
            ResultadoAutorizacao resultado = new ResultadoAutorizacao(Decisao.NEGADO_CARENCIA,"Carência não cumprida");
            return resultado;
        }
        boolean requerAutorizacaoPrevia = procedimento.isRequerAutorizacaoPrevia();
        boolean autorizacaoPreviaConcedida = contextoAtendimento.isAutorizacaoPreviaConcedida();
        if (requerAutorizacaoPrevia && !autorizacaoPreviaConcedida) {
            ResultadoAutorizacao resultado = new ResultadoAutorizacao(Decisao.NEGADO_AUTORIZACAO_PREVIA,"Autorização prévia não concedida");
            return resultado;
        }
        ResultadoAutorizacao resultado = new ResultadoAutorizacao(Decisao.AUTORIZADO,"Autorizado",beneficiario.getPlano().calcularCopart(procedimento));
        return resultado;
    }
}   
