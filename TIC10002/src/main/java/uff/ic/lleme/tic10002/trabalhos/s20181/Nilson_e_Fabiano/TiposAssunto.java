package uff.ic.lleme.tic10002.trabalhos.s20181.Nilson_e_Fabiano;

/**
 * Classe auxiliar para a estrutura de dados que armazena os Tipos de Assunto.
 *
 * @author Nilson e Fabiano
 */
class NoTipoAssunto {

    protected TipoAssunto ta;
    protected NoTipoAssunto prox;

    public NoTipoAssunto(TipoAssunto ta) {
        this.ta = ta;
    }

}

/**
 * Classe que armazena, estaticamente, os Tipos de Assunto a serem utilizados
 * por todo o Sistema.
 *
 * Implementada como uma lista simplemente encadeada, com inserção do novo
 * elemento (TipoAssunto) no final da lista, como se fosse uma fila.
 *
 * @author Nilson e Fabiano
 */
public class TiposAssunto {

    private static NoTipoAssunto primeiro;   // primeiro elemento da lista
    private static NoTipoAssunto ultimo;     // ultimo elemento da lista
    private static NoTipoAssunto corrente;   // utilizado pelo iterador
    private static int n;                    // quantidade de elementos

    /**
     * Insere o tipo de assunto "ta" na lista na última posicao.
     */
    public static TipoAssunto insere(TipoAssunto ta) {
        NoTipoAssunto no = new NoTipoAssunto(ta);
        if (ultimo == null)
            primeiro = no;
        else
            ultimo.prox = no;
        ultimo = no;
        n++;
        return ta;
    }

    /**
     * Metodo que implementa uma parte do "iterador" interno sobre a lista de
     * Tipos de Assunto. Ao ser chamado inicia o iterador e retorna o primeiro
     * elemento da lista.
     */
    public static TipoAssunto pegaPrimeiro() {
        corrente = primeiro;
        if (corrente == null)
            return null;
        return corrente.ta;
    }

    /**
     * Metodo que implementa uma parte do "iterador" interno sobre a lista de
     * Tipos de Aassunto. Ao ser chamado retorna o próximo elemento da lista,
     * assumindo que "getPrimeiro" foi chamado em algum momento anterior.
     */
    public static TipoAssunto pegaProximo() {
        if (corrente == null)
            return null;
        corrente = corrente.prox;
        if (corrente == null)
            return null;
        return corrente.ta;
    }

    /**
     * Retorna a quantidade total de Tipos de Assunto cadastrados
     */
    public static int count() {
        return n;
    }
}
