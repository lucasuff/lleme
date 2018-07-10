package uff.ic.lleme.tic10002.trabalhos.s20181.Nilson_e_Fabiano;

/**
 * Objetos que armazenam dados estatisticos para um determinado Tipo de Assunto
 *
 * @author Nilson e Fabiano
 */
class Estatistica {

    private int id;           // id do tipo de assunto
    private int duracao;   // duracao do atendimento
    private int quantidade;   // quantidade de atendimentos

    /**
     * Construtor
     *
     * @param id
     */
    public Estatistica(int id) {
        this.id = id;
    }

    /**
     * Registra para fins estatisticos que ocorreu um atendimento com duracao
     * dada por "duracao" em MINUTOS.
     *
     */
    public void contabilizaEvento(int duracao) {
        this.duracao += duracao;
        this.quantidade++;
    }

    /**
     * Retorna o valor do tempo medio de atendimento ou zero se não houve
     * atendimentos registrados
     */
    public double getMedia() {
        if (quantidade == 0)
            return 0;
        return (double) duracao / quantidade;
    }

    /**
     * Retorna a quantidade de atendimentos registrados
     *
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * Retorna a soma total da duracao de atendimentos nesta célula estatistica
     *
     */
    public int getDuracao() {
        return duracao;
    }

    /**
     * Retorna o Id ao qual essa célula estatistica se refere
     *
     */
    public int getId() {
        return id;
    }

}

public class Estatisticas {

    Estatistica hash[];
    int hashSize;

    /**
     * Construtor
     *
     * Inicializa o Hash tomando como base o numero de tipos de assunto
     *
     * @param numTiposAssunto Número de Tipos de Assunto cadastrados
     */
    public Estatisticas(int numTiposAssunto) {
        hashSize = numTiposAssunto * 2;
        hash = new Estatistica[hashSize];
    }

    /**
     * Computa o Hash para um id
     *
     * @param id
     * @return
     */
    private int computaHash(int id) {
        return id % hashSize;
    }

    /**
     * Insere a estatistica no Hash usando o valor de "getId" como chave
     *
     * @param e
     */
    private void insereHash(Estatistica e) {
        int p0 = computaHash(e.getId());
        int top = hashSize;
        for (int i = 0; i < 2; i++) {
            Estatistica h;
            int pos = p0;
            // tenta inserir na primeira posicao livre a partir de pos
            while (pos < top) {
                h = hash[pos];
                if (h == null) {
                    hash[pos] = e;
                    return;

                } else if (h.getId() == e.getId())
                    // ja estava inserido
                    return;
                pos++;
            }
            // nao existia?
            // entao tenta inserir a partir do inicio
            top = p0;
            p0 = 0;
        }
        // esta lotado
    }

    /**
     * Localiza a Estatistica a partir do id.
     *
     * @param id
     */
    private Estatistica localizaHash(int id) {
        int pos = computaHash(id);

        int top = hashSize;
        int p0 = pos;
        for (int i = 0; i < 2; i++) {
            Estatistica h;
            while (pos < top) {
                h = hash[pos];
                if (h.getId() == id)
                    return h;
                pos++;
            }
            // tenta novamente a partir do inicio
            top = p0;
            pos = 0;
        }
        return null;
    }

    /**
     * Cria uma nova célula para estatistica para um determinado id e a coloca
     * no hash usando o id como chave.
     *
     * @param id
     * @return
     */
    public void criaEstatistica(int id) {
        Estatistica e = new Estatistica(id);
        insereHash(e);
    }

    /**
     * Localiza uma estatistica com base num id
     *
     * @param id
     * @return
     */
    public Estatistica localizaEstatistica(int id) {
        return localizaHash(id);
    }
}
