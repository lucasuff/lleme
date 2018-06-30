package uff.ic.lleme.tic10002.trabalhos.s20181.Nilson_e_Fabiano;

/**
 * Estrutura auxiliar para implementação da lista de Assuntos.
 *
 * @author Nilson e Fabiano
 */
class NoAssunto {

    protected Assunto a;
    protected NoAssunto prox;

    public NoAssunto(Assunto a) {
        this.a = a;
    }

    public Assunto getAssunto() {
        return this.a;
    }
}

/**
 * Lista de todos os assuntos a serem tratados num atendimento Implementada como
 * uma lista simplesmente encadeada
 *
 * @author Nilson e Fabiano
 */
public class Assuntos {

    NoAssunto primeiro;
    NoAssunto ultimo;
    NoAssunto iterador;

    public Assuntos() {
    }

    /**
     * Insere o assunto "a" no final da lista
     */
    public void insere(Assunto a) {
        NoAssunto no = new NoAssunto(a);
        if (primeiro == null)
            primeiro = no;
        else
            ultimo.prox = no;
        ultimo = no;
    }

    /**
     * Retorna a soma de todas as urgencias de todos os assuntos
     */
    public int calculaUrgencia() {
        NoAssunto p;
        int acc = 0;
        for (p = primeiro; p != null; p = p.prox)
            acc += p.a.getTipo().getUrgencia();
        return acc;
    }

    /**
     * Retorna o tempo necessario para atender todos os assuntos do atendimento
     */
    public int calculaDuracao() {
        NoAssunto p;
        int acc = 0;
        for (p = primeiro; p != null; p = p.prox)
            acc += p.a.getDuracao();
        return acc;
    }

    /**
     * Metodo que implementa uma parte do "iterador" interno sobre a lista de
     * assuntos. Ao ser chamado inicia o iterador e retorna o primeiro elemento
     * da lista.
     *
     */
    public Assunto getPrimeiro() {
        iterador = primeiro;
        if (iterador == null)
            return null;
        return primeiro.getAssunto();
    }

    /**
     * Metodo que implementa uma parte do "iterador" interno sobre a lista de
     * assuntos. Ao ser chamado retorna o próximo elemento da lista, assumindo
     * que "getPrimeiro" foi chamado em algum momento anterior.
     *
     */
    public Assunto getProximo() {
        if (iterador == null)
            return null;
        iterador = iterador.prox;
        if (iterador == null)
            return null;
        return iterador.getAssunto();
    }
}
