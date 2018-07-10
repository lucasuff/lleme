package uff.ic.lleme.tic10002.trabalhos.s20181.Nilson_e_Fabiano;

import uff.ic.lleme.tic10002.trabalhos.s20181.Nilson_e_Fabiano.model.DateTime;
import uff.ic.lleme.tic10002.trabalhos.s20181.Nilson_e_Fabiano.model.Atendimento;
import uff.ic.lleme.tic10002.trabalhos.s20181.Nilson_e_Fabiano.model.Cliente;
import uff.ic.lleme.tic10002.trabalhos.s20181.Nilson_e_Fabiano.model.Assunto;
import java.util.Random;

/**
 * Classe que implementa os quatro métodos demandados pelo trabalho.
 *
 * Representa um serviço de atendimento a clientes genérico. Daí a razão do nome
 * "Servico".
 *
 * @author Nilson e Fabiano
 */
public class Servico {

    private final int minDuracao = 2; // Duracao minima de um atendimento
    private Random gerador;

    Pendentes pendentes = new Pendentes();
    Atendidos atendidos = new Atendidos();

    /**
     * Método recepcionar, conforme a especificação.
     *
     * @param cliente
     * @param assuntos
     */
    public void recepcionar(Cliente cliente, Assuntos assuntos) {
        Atendimento atendimento = new Atendimento(cliente, assuntos);
        pendentes.insere(atendimento);
    }

    /**
     * Permite a preparação para gerar uma sequencia de tempos de atendimento
     * dos assuntos. Usado principalmente para testes.
     */
    private void iniciaAtendimentoDosAssuntos() {
        gerador = new Random();
    }

    /**
     * Informa o tempo de atendimento para um assunto de um atendimento. No
     * momento gera valores aleatorios independentemente do tipo de assunto, mas
     * pode ser alterado para gerar valores de acordo com o tipo de assunto
     * Usado principalmente para testes.
     */
    private int getTempoAtendimentoDoAssunto(Assunto a) {
        return minDuracao + gerador.nextInt(100);
    }

    /**
     * Informa a providencia a ser tomada para um assunto de um atendimento. No
     * momento retorna a mesma string independentemente do tipo de assunto, mas
     * pode ser alterado para gerar valores de acordo com o tipo de assunto
     * Usado principalmente para testes.
     */
    private String getProvidenciaParaAssunto(Assunto a) {
        return "Alguma providência";
    }

    /**
     * Método atender, conforme a especificação.
     *
     * @return Atendimento
     */
    public Atendimento atender() {

        long horaAtendimento = DateTime.getTime();

        // localiza o proximo atendimento a ser realizado de acordo com a
        // prioridade, considerando a hora em que o atendimento está iniciando.
        Atendimento atendimento = pendentes.pegaPrioritario(horaAtendimento);

        // Nao tem ninguem para atender? Simplesmente sai
        if (atendimento == null)
            return null;

        // Registra a hora em que o atendimento foi iniciado
        atendimento.setHoraAtendimento(horaAtendimento);

        // Se prepara para iniciar o atendimento dos assuntos
        iniciaAtendimentoDosAssuntos();
        Assuntos as = atendimento.getAssuntos();

        System.out.println("Cliente: " + atendimento.getCliente().getNome());
        System.out.println("Assuntos: ");

        // Realiza o atendimento para cada assunto na lista de assuntos
        for (Assunto a = as.getPrimeiro(); a != null; a = as.getProximo()) {
            a.registrar(
                    getProvidenciaParaAssunto(a),
                    getTempoAtendimentoDoAssunto(a)
            );

            System.out.println("  " + a.getDescricao() + " - tipo: " + a.getTipo().getTitulo());
        }

        return atendimento;
    }

    /**
     * Método encerrar, conforme a especificação.
     *
     * @param atendimento
     */
    public void encerrar(Atendimento atendimento) {

        // Nao existe ninguem sendo atendido? retorna
        if (atendimento == null)
            return;

        // Coloca o atendimento corrente na lista de atendidos
        atendidos.insere(atendimento);
    }

    /**
     * Método gerarEstatistica, conforme a especificacao.
     */
    public void gerarEstatistica() {

        // cria uma "lista" que conterá celulas estatisticas chamadas de
        // Estatistica sendo uma para cada tipo de assunto
        Estatisticas es = new Estatisticas(TiposAssunto.count());

        // cria uma celula Estatistica para cada tipo de assunto
        for (TipoAssunto ta = TiposAssunto.pegaPrimeiro();
                ta != null;
                ta = TiposAssunto.pegaProximo())

            // cria a celula estatistica conforme o tipo de assunto
            es.criaEstatistica(ta.getTipo());

        // Computa as estatisticas para o dia de hoje
        long dia = DateTime.getTime(); // data base
        atendidos.computaEstatisticas(es, dia);

        // calcula as medias para os tipos de assunto
        int duracao_total = 0;
        int quantidade_total = 0;

        // para todos os tipos
        for (TipoAssunto ta = TiposAssunto.pegaPrimeiro();
                ta != null;
                ta = TiposAssunto.pegaProximo()) {

            // localiza a celula acumuladora de estatisticas para o tipo
            Estatistica e = es.localizaEstatistica(ta.getTipo());

            int duracao = e.getDuracao();
            int quantidade = e.getQuantidade();

            System.out.printf("%s[%d] Quantidade: %d  Duração: %ds  Tempo médio de atendimento: %.1fs:\n",
                    ta.getTitulo(), ta.getTipo(),
                    quantidade, duracao,
                    e.getMedia());

            // contabiliza estatisticas globais do dia
            duracao_total += duracao;
            quantidade_total += quantidade;
        }
        // calcula o tempo medio de atendimento para o dia
        double media = (quantidade_total == 0) ? 0.0
                : ((double) duracao_total / quantidade_total);
        System.out.printf("Quantidade Total: %d  Duração Total: %ds  Tempo médio de atendimento para o dia: %.1fs\n",
                quantidade_total, duracao_total, media);
    }

}
