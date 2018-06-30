package uff.ic.lleme.tic10002.trabalhos.s20181.Nilson_e_Fabiano;

/**
 * Armazena os dados de atendimento do cliente bem como algumas das informações
 * necessárias para que seja atendido com a prioridade correta.
 *
 * @author Nilson e Fabiano
 */
public class Atendimento {

    private Cliente cliente;         // Cliente do atendimento
    private Assuntos assuntos;       // Assuntos a serem tratados
    private long horaChegada;        // hora em que o cliente chegou ao local
    private long horaAtendimento;    // hora em que o atendimento iniciou
    private double fator_prioridade; // parte da prioridade invariavel no tempo

    /**
     * Construtor
     *
     * Cria o objeto atendimento ja computando a parte invariável com o tempo da
     * prioridade
     *
     */
    public Atendimento(Cliente cliente, Assuntos assuntos) {
        this.cliente = cliente;
        this.assuntos = assuntos;
        this.horaChegada = DateTime.getTime();
        computaFatorDePrioridade();
    }

    /**
     * Retorna a que dia um determinado instante de tempo pertence. O valor
     * retornado serve apenas para comparação entre "epoch"s.
     *
     * @param epoch número de segundos entre o dia de hoje e 1/1/10970
     */
    private long DayOf(long epoch) {
        return epoch / 86400;
    }

    /**
     * Calcula a parte do valor da prioridade que não varia com o tempo
     */
    public void computaFatorDePrioridade() {
        int fatorIdade = 65;
        double vIdade
                = (double) cliente.getIdade() / fatorIdade;

        int assuntoUrgencia = assuntos.calculaUrgencia();
        double vUrgencia
                = (double) assuntoUrgencia / 10;

        fator_prioridade = vIdade + vUrgencia;
    }

    /**
     * Calcula a prioridade deste atendimento com base na hora de referencia
     * passada no parametro "tm" em segundos
     *
     * @param tm segundos entre a hora atual e a meia-noite de 1/1/10970
     */
    public double prioridade(long tm) {
        int fatorEspera = 5 * 60; // minutos para segundos
        double vEspera
                = (double) (tm - horaChegada) / fatorEspera;

        return (fator_prioridade + vEspera) / 3;
    }

    /**
     * retorna a duracao total do atendimento a partir da soma da duracao do
     * atendimento de cada assunto
     */
    public int calculaDuracao() {
        return assuntos.calculaDuracao();
    }

    /**
     * Informa a hora em que o atendimento iniciou.
     *
     * @param horaAtendimento
     */
    public void setHoraAtendimento(long horaAtendimento) {
        this.horaAtendimento = horaAtendimento;
    }

    /**
     * Retorna a hora de chegada ao ponto de Atendimento
     */
    public long getHoraChegada() {
        return this.horaChegada;
    }

    /**
     * Compara a data do atendimento com a data em "date" e retorna -1, 0 ou 1
     * se, respectivamente, a data do atendimento for menor, igual ou maior que
     * "date.
     */
    public int comparaData(long date) {
        long d1 = DayOf(date);
        long d2 = DayOf(horaAtendimento);
        // System.out.printf("Data = %d, Data=%d\n",d1,d2);

        if (d1 < d2)
            return -1;
        if (d1 == d2)
            return 0;
        return 1;
    }

    /**
     * Retorna o Cliente do atendimento
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Retorna a estrutura que contém os assuntos deste atendimento
     */
    public Assuntos getAssuntos() {
        return this.assuntos;
    }
}
