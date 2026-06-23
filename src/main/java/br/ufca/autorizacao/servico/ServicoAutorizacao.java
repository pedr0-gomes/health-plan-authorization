package br.ufca.autorizacao.servico;

import br.ufca.autorizacao.dominio.Beneficiario;
import br.ufca.autorizacao.dominio.ContextoAtendimento;
import br.ufca.autorizacao.dominio.Decisao;
import br.ufca.autorizacao.dominio.Procedimento;
import br.ufca.autorizacao.dominio.ResultadoAutorizacao;
import br.ufca.autorizacao.dominio.TipoProcedimento;

public class ServicoAutorizacao {
    public ResultadoAutorizacao autorizar(Beneficiario beneficiario, Procedimento procedimento, ContextoAtendimento contextoAtendimento) {
        TipoProcedimento tipo = procedimento.getTipo();
        boolean urgencia = contextoAtendimento.isUrgencia();
        int dias = beneficiario.diasDecorridos(contextoAtendimento.getDataEvento()); 
        boolean resposta = beneficiario.getPlano().carenciaCumprida(tipo,dias,urgencia);
        if (resposta) {
            ResultadoAutorizacao resultado = new ResultadoAutorizacao(Decisao.AUTORIZADO,"Carência cumprida");
            return resultado;
        }
        else {
            ResultadoAutorizacao resultado = new ResultadoAutorizacao(Decisao.NEGADO_CARENCIA,"Carência não cumprida");
            return resultado;
        }
    }
}   
