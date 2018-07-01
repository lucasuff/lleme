package uff.ic.lleme.tic10002.trabalhos.s20181.Luiz_Gustavo_Dias.model;

public class Assunto {

    public TipoAssunto tipoAssunto;
    public String descricao;
    public String providencia;
    public int duracaoAtendimentoAssunto;
    public Assunto proximoAssunto;
    public int tempoMedio;
    public int cont;

    public void setTipoAssunto(TipoAssunto tipoAssunto) {
        this.tipoAssunto = tipoAssunto;
    }

    public TipoAssunto getTipoAssunto() {
        return this.tipoAssunto;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setProvidencia(String providencia) {
        this.providencia = providencia;
    }

    public String getProvidencia() {
        return this.providencia;
    }

    public void setDuracaoAtendimentoAssunto(int duracaoAtendimentoAssunto) {
        this.duracaoAtendimentoAssunto = duracaoAtendimentoAssunto;
    }

    public int getDuracaoAtendimentoAssunto() {
        return this.duracaoAtendimentoAssunto;
    }

    public void setProximoAssunto(Assunto proximoAssunto) {
        this.proximoAssunto = proximoAssunto;
    }

    public Assunto getProximoAssunto() {
        return this.proximoAssunto;
    }

    public void setCont(int cont) {
        this.cont = cont;
    }

    public int getCont() {
        return this.cont;
    }

    public void setTempoMedio(int tempoMedio) {
        this.tempoMedio = tempoMedio;
    }

    public int getTempoMedio() {
        return this.tempoMedio;
    }

}
