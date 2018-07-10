package uff.ic.lleme.tic10002.trabalhos.s20181.Juan_e_Silvio.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.naming.LimitExceededException;
import uff.ic.lleme.tic10002.trabalhos.s20181.Juan_e_Silvio.HashTable;
import uff.ic.lleme.tic10002.trabalhos.s20181.Juan_e_Silvio.Heap;

/*
O fluxo de processo do serviço de atendimento ao cliente é
1) recepcionar atendimento,
2) recuperar dados do atendimento mais prioritário,
3) registrar providências e duração de atendimento e, quando solicitado a qualquer tempo,
4) gerar estatística.
 */
public class SAC {

    private final Heap filaDePrioridade;
    private final HashTable atendimentosEncerrados;
    private Atendimento atual;
    private boolean debug;
    private final static int QTD_TIPOS = 10;

    public SAC(boolean debug) {
        this.debug = debug;
        this.filaDePrioridade = new Heap();
        this.atendimentosEncerrados = new HashTable();
        atual = null;
    }

    //A função recepcionar recebe os dados dos clientes e suas demandas e os coloca em espera de atendimento
    public void recepcionar(Cliente c, Assunto[] assuntos) {
        try {
            filaDePrioridade.inserir(new Atendimento(c, assuntos));
            if (debug)
                filaDePrioridade.printHeap();
        } catch (LimitExceededException e) {
            System.err.println("Fila cheia!");
        }
    }

    /*
    A função atender fornece os dados referentes às demandas do próximo cliente a ser atendido
    priorizando os clientes conforme sua idade, tempo de espera na fila e grau de urgência dos
    assuntos segundo a fórmula: Prioridade (cliente) = média {[idade/65 anos],[espera/15 minutos],[média{urgencia(assuntos)}/10]}
     */
    public Atendimento atender() {
        try {
            if (debug)
                filaDePrioridade.printHeap();
            atual = filaDePrioridade.getNext();
            atual.inicioAtendimento();
            if (debug)
                System.out.println("Início do atendimento: " + atual.getCliente().getNome());
            return atual;
        } catch (LimitExceededException ex) {
            System.err.println("Nenhum atendimento na fila!");
            return null;
        }
    }

    /*
        A função gerarEstatistica fornece, quando solicitado, o tempo médio de atendimento por tipo de assunto no dia corrente,
        permitindo calibrar o critério de prioridade: assuntos mais demorados, em média, podem ter
        sua prioridade aumentada.

        OBS: A função gerarEstatística() deve computar o tempo médio a partir nos dados
        de atendimento encerrados.
     */
    public void gerarEstatistica(String data) {
        if (debug)
            System.out.println(atendimentosEncerrados.toString());
        //Pega atendimentos do dia especificado
        Atendimento[] encerradosDoDia = atendimentosEncerrados.get(data);
        if (encerradosDoDia.length > 0) {
            //Conta duração de cada tipo assunto e imprime média.
            Contador cont = new Contador(QTD_TIPOS);
            for (Atendimento a : encerradosDoDia) {
                Assunto[] assuntosAtend = a.getAssuntos();
                for (int j = 0; j < assuntosAtend.length; j++) {
                    int idTipo = assuntosAtend[j].getTipoAssunto().getTipo();
                    cont.adiciona(idTipo, assuntosAtend[j].getDuracaoAtendimento());
                }
            }
            System.out.println("-----------------------------------------------------------------");
            System.out.println("Estatísticas (" + data.substring(8, 10) + "/" + data.substring(5, 7) + "/" + data.substring(0, 4) + "):");
            System.out.println("-----------------------------------------------------------------");
            for (int i = 1; i <= QTD_TIPOS; i++)
                System.out.println("Tipo " + i + ": " + cont.getMedia(i));
            System.out.println("-----------------------------------------------------------------");
        } else
            System.out.println("ERRO! - Nenhum atendimento foi realizado no dia informado");
    }

    /*
        A função encerrar registra o fim dos atendimentos e as providências que foram tomadas
        para atender as demandas dos clientes.
     */
    public void encerrar(Atendimento atendimento) throws ParseException {
        String chave = atendimento.getHoraAtendimento();
        atendimentosEncerrados.put(chave, atendimento);
        imprimeResultadoDoAtendimento(atendimento);
    }

    public boolean filaDeAtendimentoVazia() {
        return filaDePrioridade.getSize() == 0;
    }

    public HashTable getAtendimentosEncerrados() {
        return atendimentosEncerrados;
    }

    private String formataData(int dia, int mes, int ano) {
        //Pega ano,mes,dia do sistema e passa como key
        Calendar c = new GregorianCalendar();
        c.set(ano, mes - 1, dia); //-1 necessário pois janeiro é tratado como mês 0
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(c.getTimeZone());
        String s = dateFormat.format(c.getTime());
        return s;
    }

    private void imprimeResultadoDoAtendimento(Atendimento atendimento) {
        System.out.println("-----------------------------------------------------------------");
        System.out.println("Atendimento Encerrado - Cliente: " + atendimento.getCliente().getNome());
        System.out.println("-----------------------------------------------------------------");
        Assunto[] assuntosResolvidos = atendimento.getAssuntos();
        for (int i = 0; i < assuntosResolvidos.length; i++) {
            System.out.println("Assunto " + (i + 1) + ": | TIPO: " + assuntosResolvidos[i].getTipoAssunto().toString() + "\n\t"
                    + assuntosResolvidos[i].getDescricao());
            System.out.println("\tProvidência: " + assuntosResolvidos[i].getProvidencias()
                    + "| TEMPO: " + assuntosResolvidos[i].getDuracaoAtendimento());
        }
        System.out.println("-----------------------------------------------------------------");
    }
}
