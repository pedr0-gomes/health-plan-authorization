package br.ufca.autorizacao.apresentacao;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import br.ufca.autorizacao.dominio.Ambulatorial;
import br.ufca.autorizacao.dominio.Beneficiario;
import br.ufca.autorizacao.dominio.ContextoAtendimento;
import br.ufca.autorizacao.dominio.CoparticipacaoFixaPorTipo;
import br.ufca.autorizacao.dominio.CoparticipacaoPercentual;
import br.ufca.autorizacao.dominio.HospitalarComObstetricia;
import br.ufca.autorizacao.dominio.Plano;
import br.ufca.autorizacao.dominio.Procedimento;
import br.ufca.autorizacao.dominio.Referencia;
import br.ufca.autorizacao.dominio.ResultadoAutorizacao;
import br.ufca.autorizacao.dominio.SemCoparticipacao;
import br.ufca.autorizacao.dominio.TipoProcedimento;
import br.ufca.autorizacao.excecao.BeneficiarioNaoEncontradoException;
import br.ufca.autorizacao.excecao.ProcedimentoNaoEncontradoException;
import br.ufca.autorizacao.repositorio.RepositorioBeneficiario;
import br.ufca.autorizacao.repositorio.RepositorioPlano;
import br.ufca.autorizacao.repositorio.RepositorioProcedimento;
import br.ufca.autorizacao.servico.ServicoAutorizacao;

public class Main {
    private final RepositorioPlano repositorioPlano = new RepositorioPlano();
    private final RepositorioBeneficiario repositorioBeneficiario = new RepositorioBeneficiario();
    private final RepositorioProcedimento repositorioProcedimento = new RepositorioProcedimento();
    private final ServicoAutorizacao servicoAutorizacao = new ServicoAutorizacao();

    public static void main(String[] args) {
        new Main().executar();
    }

    private void semear() {
        // Procedimentos (cobrindo todos os TipoProcedimento)
        repositorioProcedimento.adicionar(new Procedimento("626", "Consulta clínica geral", TipoProcedimento.CONSULTA, false, 150.0));
        repositorioProcedimento.adicionar(new Procedimento("627", "Consulta cardiológica", TipoProcedimento.CONSULTA, false, 250.0));
        repositorioProcedimento.adicionar(new Procedimento("710", "Hemograma completo", TipoProcedimento.EXAME, false, 80.0));
        repositorioProcedimento.adicionar(new Procedimento("711", "Ressonância magnética", TipoProcedimento.EXAME, true, 1200.0));
        repositorioProcedimento.adicionar(new Procedimento("820", "Cirurgia de apendicite", TipoProcedimento.CIRURGIA, true, 8000.0));
        repositorioProcedimento.adicionar(new Procedimento("821", "Cirurgia de catarata", TipoProcedimento.CIRURGIA, true, 5000.0));
        repositorioProcedimento.adicionar(new Procedimento("905", "Internação clínica", TipoProcedimento.INTERNACAO, false, 3000.0));
        repositorioProcedimento.adicionar(new Procedimento("906", "Internação em UTI", TipoProcedimento.INTERNACAO, true, 15000.0));
        repositorioProcedimento.adicionar(new Procedimento("330", "Parto normal", TipoProcedimento.PARTO, false, 4000.0));
        repositorioProcedimento.adicionar(new Procedimento("331", "Parto cesárea", TipoProcedimento.PARTO, true, 6000.0));

        // Planos (segmentação e política distintas em cada um)
        Plano planoAmbulatorial = new Plano("Unimed Ambulatorial", new Ambulatorial("Ambulatorial", "Consultas e exames"), new SemCoparticipacao(), 300, 180);

        Plano planoHospitalar = new Plano("Unimed Hospitalar Plus", new HospitalarComObstetricia("HCO", "Cobre tudo"), new CoparticipacaoPercentual(0.2), 300, 180);

        Map<TipoProcedimento, Double> tabelaCopart = new HashMap<>();
        tabelaCopart.put(TipoProcedimento.CONSULTA, 30.0);
        tabelaCopart.put(TipoProcedimento.EXAME, 20.0);
        tabelaCopart.put(TipoProcedimento.CIRURGIA, 200.0);
        Plano planoReferencia = new Plano("Unimed Referência", new Referencia("Referência", "Cobertura plena"), new CoparticipacaoFixaPorTipo(tabelaCopart), 300, 180);

        repositorioPlano.adicionar(planoAmbulatorial);
        repositorioPlano.adicionar(planoHospitalar);
        repositorioPlano.adicionar(planoReferencia);

        // Beneficiários (datas de adesão variadas, distribuídos entre os planos)
        repositorioBeneficiario.adicionar(new Beneficiario("Pedro", LocalDate.of(2026, 1, 1), planoHospitalar));
        repositorioBeneficiario.adicionar(new Beneficiario("Maria", LocalDate.of(2023, 5, 10), planoHospitalar));
        repositorioBeneficiario.adicionar(new Beneficiario("João", LocalDate.of(2024, 8, 15), planoAmbulatorial));
        repositorioBeneficiario.adicionar(new Beneficiario("Ana", LocalDate.of(2025, 11, 20), planoReferencia));
        repositorioBeneficiario.adicionar(new Beneficiario("Carlos", LocalDate.of(2022, 2, 28), planoReferencia));
    }

    public void executar() {
        semear();
        Scanner scanner = new Scanner((System.in));
        boolean rodando = true;
        while (rodando) {
            System.out.println();
            System.out.println("=== Autorização de Plano de Saúde ===");
            System.out.println("1 - Listar planos");
            System.out.println("2 - Listar beneficiários");
            System.out.println("3 - Listar procedimentos");
            System.out.println("4 - Solicitar autorização");
            System.out.println("5 - Sair");
            System.out.print("Escolha uma opção: ");
            int opcao;
            try {
                opcao = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida. Digite um número de 1 a 5.");
                continue;
            }
            switch(opcao) {
                case 1:
                    for (Plano p : repositorioPlano.listar()) {
                        System.out.println(p);
                    }
                    break;
                case 2:
                    for (Beneficiario b : repositorioBeneficiario.listar()) {
                        System.out.println(b);
                    }
                    break;
                case 3:
                    for (Procedimento p : repositorioProcedimento.listar()) {
                        System.out.println(p);
                    }
                    break;
                case 4: {
                    try {
                    System.out.println("Solicitar autorização");
                    System.out.print("Beneficiário: ");
                    String nome = scanner.nextLine();
                    Beneficiario beneficiario = repositorioBeneficiario.buscar(nome);
                    System.out.print("Código: ");
                    String codigo = scanner.nextLine();
                    Procedimento procedimento = repositorioProcedimento.buscar(codigo);
                    System.out.print("Urgência? (s/n): ");
                    String respostaUrgencia = scanner.nextLine();
                    boolean urgencia = respostaUrgencia.equalsIgnoreCase("s");
                    System.out.print("Autorização prévia concedida? (s/n): ");
                    String respostaAutorizacao = scanner.nextLine();
                    boolean autorizacaoPrevia = respostaAutorizacao.equalsIgnoreCase("s");
                    ContextoAtendimento contextoAtendimento = new ContextoAtendimento(LocalDate.now(), urgencia, autorizacaoPrevia);
                    ResultadoAutorizacao resultado = servicoAutorizacao.autorizar(beneficiario, procedimento, contextoAtendimento);
                    System.out.println(resultado);
                    } catch (BeneficiarioNaoEncontradoException | ProcedimentoNaoEncontradoException e) {
                        System.out.println("Não foi possível autorizar: " + e.getMessage());
                    }
                    break;
                }
                case 5:
                    rodando = false;
                    break;
                default:
                    System.out.println("Opção Inválida");
                    break;
            }
        }
    }
}
