package uff.ic.lleme.tic10002.trabalhos.s20181.Nilson_e_Fabiano;

/**
 * Classe na qual os objetos representam cada assunto a ser tratado em um
 * atendimento de um cliente
 *
 * @author Nilson e Fabiano
 */
public class Assunto {

    private final TipoAssunto tipo;
    private final String descricao;
    private String providencia;
    private int duracao;

    /**
     * Contrutor
     *
     * @param tipo
     * @param descricao
     */
    public Assunto(TipoAssunto tipo, String descricao) {
        this.tipo = tipo;
        this.descricao = descricao;
    }

    /**
     * Registra qual a providencia será tomada para resolver o assunto bem como
     * a duracao do atendimento para esse assunto.
     *
     * @param providencia Providencia a ser tomada
     * @param duracao duracao do atendimento em minutos
     */
    public void registrar(String providencia, int duracao) {
        this.providencia = providencia;
        this.duracao = duracao;
    }

    /**
     * Retorna o tipo do Assunto
     */
    public TipoAssunto getTipo() {
        return tipo;
    }

    /**
     * Retorna a descricao do assunto
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Retorna o tempo necessário para resolver o assunto
     *
     */
    public int getDuracao() {
        return duracao;
    }
}
