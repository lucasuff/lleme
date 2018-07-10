package uff.ic.lleme.tic10002.trabalhos.s20181.Mateus_e_Raphael.model;

/**
 *
 * @author mateu
 */
public class Assunto {

    public TipoAssunto tipo;
    private String descricao;
    private String providencia;
    private int duracaoAtendimento;

    //método construtor com inicialização
    public Assunto(TipoAssunto tipo, String descricao) {
        this.tipo = tipo;
        this.descricao = descricao;
        this.providencia = null;
        this.duracaoAtendimento = -1;
    }

    public Assunto(TipoAssunto tipo, String descricao, String providencia, int duracaoAtendimento) {
        this.tipo = tipo;
        this.descricao = descricao;
        this.providencia = providencia;
        this.duracaoAtendimento = duracaoAtendimento;
    }

    public TipoAssunto getTipo() {
        return tipo;
    }

    public void setTipo(TipoAssunto tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getProvidencia() {
        return providencia;
    }

    public void setProvidencia(String providencia) {
        this.providencia = providencia;
    }

    public int getDuracaoAtendimento() {
        return duracaoAtendimento;
    }

    public void setDuracaoAtendimento(int duracaoAtendimento) {
        this.duracaoAtendimento = duracaoAtendimento;
    }

    public void finalizarAssunto(String providencia, int duracao) {
        this.providencia = providencia;
        this.duracaoAtendimento = duracao;
    }
}
