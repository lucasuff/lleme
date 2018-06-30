package uff.ic.lleme.tic10002.trabalhos.s20181.Lucas_e_Erick.model;

@SuppressWarnings("rawtypes")
public class Assunto implements Comparable {

    private TipoAssunto tipoAssunto;
    private String descricao;
    private String providencia;
    private int duracao;

    public Assunto(TipoAssunto tipoAssunto, String descricao, String providencia, int duracao) {
        this.tipoAssunto = tipoAssunto;
        this.descricao = descricao;
        this.providencia = providencia;
        this.duracao = duracao;
    }

    public TipoAssunto getTipoAssunto() {
        return tipoAssunto;
    }

    public void setTipoAssunto(TipoAssunto tipoAssunto) {
        this.tipoAssunto = tipoAssunto;
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

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    @Override
    public int compareTo(Object assunto) {
        return 0;
    }
}
