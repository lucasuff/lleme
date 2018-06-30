package uff.ic.lleme.tic10002.trabalhos.s20181.Lucas_e_Erick.model;

import uff.ic.lleme.tic10002.trabalhos.s20181.Lucas_e_Erick.FilaDePrioridadesDinamica;
import uff.ic.lleme.tic10002.trabalhos.s20181.Lucas_e_Erick.TabelaHash;
import uff.ic.lleme.tic10002.trabalhos.s20181.Lucas_e_Erick.teste.InstanciasDeTeste;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

public class Main {

    private static FilaDePrioridadesDinamica<Cliente> filaClientes = new FilaDePrioridadesDinamica<Cliente>(50);
    private static TabelaHash<Estatistica> historicoEstatisticas = new TabelaHash<Estatistica>(110,
            "persistenciahash.hash");
    private static LocalDateTime relogioInicioExecucao;

    /*
	 * instancias para teste com clientes, assuntos, tiposassuntos e etc.
	 * private static InstanciasDeTeste3 teste = new InstanciasDeTeste3();
	 * private static 	 InstanciasDeTeste2 teste = new InstanciasDeTeste2();
     */

 /*
	 * instancias de estat�sticas para teste em 3 diferentes dias, as instancias de
	 * 2018 d�o colis�o.
	 * private static Estatistica estatisticaDoDia = new Estatistica(LocalDate.of(1994, Month.MAY, 22), teste.getTipoAssuntos().size());
	 * private static Estatistica estatisticaDoDia = new Estatistica(LocalDate.now(), teste.getTipoAssuntos().size());
	 * private static Estatistica estatisticaDoDia = new Estatistica(LocalDate.of(2018, Month.DECEMBER, 31), teste.getTipoAssuntos().size());
     */

 /*
* instancias prontas para teste no dia da apresenta��o
     */
    private static InstanciasDeTeste teste = new InstanciasDeTeste();
    private static Estatistica estatisticaDoDia = new Estatistica(LocalDate.of(2018, Month.JUNE, 18), teste.getTipoAssuntos().size());

    public static void main(String[] args) {
        /*
		 * inicializa as estatisticas com o vetor de todos os tipos de assuntos
		 * poss�veis para esse dom�nio. Cada dom�nio teria j� definido os tipos de
		 * assuntos que atende.
         */
        estatisticaDoDia.setTipoassunto(
                Arrays.copyOf(teste.getTipoAssuntos().toArray(), teste.getTipoAssuntos().size(), TipoAssunto[].class));

        /*
		 * recepciona os clientes adicionando na fila
         */
        for (int i = 0; i < teste.getClientes().size(); i++)
            recepcionar(teste.getClientes().get(i));

        /*
		 * faz o atendimento dos clientes
         */
        for (int i = 0; i < 7; i++)
            atender();

        /*
		 * Essa chamada adiciona na tabela hash uma estat�stica, no caso a do dia
         */
        historicoEstatisticas.add(estatisticaDoDia);

        /*
		 * Esse conjunto de chamadas de estat�stica serve para printar as estatisticas
		 * dos arquivos ja existentes. System.out.println("22//05//1994\n" +
		 * "----------\n"); gerarEstatisticas(LocalDate.of(1994, Month.MAY, 22));
		 * System.out.println("01//06//2018\n" + "----------\n");
		 * gerarEstatisticas(LocalDate.of(2018, Month.JUNE, 1));
		 * System.out.println("31//12//2018\n" + "----------\n");
		 * gerarEstatisticas(LocalDate.of(2018, Month.DECEMBER, 31));
         */

 /*
		 * Esse m�todo serializa a tabela hash
         */
        historicoEstatisticas.persistir();

        /*
		 * essa chamada printa a estat�stica do dia
         */
        gerarEstatisticas(estatisticaDoDia.getData());
    }

    // esse m�todo adiciona na fila de prioridades
    public static void recepcionar(Cliente cliente) {
        filaClientes.add(cliente);
    }

    // esse m�todo remove um cliente da fila, que garantidamente tem a maior
    // prioridade. Para cada cliente ele inicializa um atendimento, cada atendimento
    // tratar� de um assunto espec�fico. Existe um sleep para quest�o de teste de
    // forma que a diferen�a entre os testes n�o de tempo=0. Este m�todo tamb�m
    // chama o encerramento para cada assunto, podemos assim falar que um cliente s�
    // pode resolver o pr�ximo assunto quando terminar o assunto atual. Nesse M�todo
    // atualizamos ainda o objeto estat�stica que indica os valores para o dia
    // corrente.
    public static void atender() {
        try {
            relogioInicioExecucao = LocalDateTime.now();
            long diferenca;
            Cliente cliente = (Cliente) filaClientes.remove();
            Assunto[] assuntos = Arrays.copyOf(cliente.getListaAssuntos().toArray(),
                    cliente.getListaAssuntos().toArray().length, Assunto[].class);
            // Object[] o = cliente.getListaAssuntos().toArray();
            int size = assuntos.length;
            long tempototal = 0;
            for (int i = 0; i < size; i++) {
                Thread.sleep(2000);
                diferenca = Duration.between(relogioInicioExecucao, LocalDateTime.now()).getSeconds();
                System.out.println("diferen�a dentro do for " + diferenca);
                Atendimento atendimento = new Atendimento(cliente, assuntos[i],
                        cliente.getTempoChegada().plusSeconds(diferenca), null);
                encerrar(atendimento);
                estatisticaDoDia.setAparicao(atendimento.getAssunto().getTipoAssunto().getTipoid(), 1);
                estatisticaDoDia.setTotalEmSegundos(atendimento.getAssunto().getTipoAssunto().getTipoid(),
                        Duration.between(atendimento.getDataChegada(), atendimento.getDataAtendimento()).getSeconds());
                tempototal += Duration.between(atendimento.getDataChegada(), atendimento.getDataAtendimento())
                        .getSeconds();
                System.out.println("tempototal dentro do for " + tempototal);
                System.out.println("o cliente " + cliente.getNome() + " foi recepcionado as "
                        + cliente.getTempoChegada() + " porque a prioridade dele foi " + cliente.getPrioridade()
                        + ", seu assunto n" + i + " foi " + assuntos[i].getTipoAssunto().getTipo()
                        + " e o tempo de chegada do atendimento para ele foi " + atendimento.getDataChegada()
                        + " e o tempo final do atendimento para este assunto foi " + atendimento.getDataAtendimento());
            }
            cliente.setTempoAtendimento(cliente.getTempoChegada().plusSeconds(tempototal));
            System.out.println(
                    "tempo total de todos os atendimentos da classe cliente: " + cliente.getTempoAtendimento());
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void encerrar(Atendimento atendimento) {
        try {
            int sleeptimerandom = (int) ((double) (Math.random() * 3000) + 2000);
            Thread.sleep(sleeptimerandom);
            long diferenca = Duration.between(relogioInicioExecucao, LocalDateTime.now()).getSeconds();
            LocalDateTime tmp = atendimento.getDataChegada();
            atendimento.setDataAtendimento(tmp.plusSeconds(diferenca));
            atendimento.getAssunto().setDuracao(((int) Duration
                    .between(atendimento.getDataChegada(), atendimento.getDataAtendimento()).getSeconds()));
            atendimento.getAssunto().setDescricao("na d�vida, virose");
            atendimento.getAssunto().setProvidencia("inje��o na bunda resolve");
            // classe cliente ou por na table hash.
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void gerarEstatisticas(LocalDate data) {
        LocalDate hoje = LocalDate.now();

        if (data.isEqual(hoje)) {
            System.out.println("estatistica vindo da memoria");
            TipoAssunto[] ta = estatisticaDoDia.getTipoassunto();
            int[] aparicao = estatisticaDoDia.getAparicoes();
            long[] segs = estatisticaDoDia.getTotalemsegundos();
            for (int i = 0; i < ta.length; i++)
                if (aparicao[i] != 0)
                    System.out.println("Tipo assunto: " + ta[i].getTipo() + " e a m�dia de sua urg�ncia � de "
                            + ((double) (segs[i] / aparicao[i])) + " segundos.");
                else
                    System.out.println(
                            "Tipo assunto: " + ta[i].getTipo() + " e a m�dia de sua urg�ncia � de 0 segundos.");
        } else {
            System.out.println("estatistica vinda do arquivo");
            Estatistica estatisticaDoHistorico = historicoEstatisticas.get(new Estatistica().getChave(data));
            TipoAssunto[] ta = estatisticaDoHistorico.getTipoassunto();
            int[] aparicao = estatisticaDoHistorico.getAparicoes();
            long[] segs = estatisticaDoHistorico.getTotalemsegundos();
            for (int i = 0; i < ta.length; i++)
                if (aparicao[i] != 0)
                    System.out.println("Tipo assunto: " + ta[i].getTipo() + " e a m�dia de sua urg�ncia � de "
                            + ((double) (segs[i] / aparicao[i])) + " segundos.");
                else
                    System.out.println(
                            "Tipo assunto: " + ta[i].getTipo() + " e a m�dia de sua urg�ncia � de 0 segundos.");
        }

    }
}
