package uff.ic.lleme.tic10002.trabalhos.s20181.Romulo_e_Lucas.src.ed.trabalho20181;

import java.util.Date;

public class Assunto {

    private TipoAssunto tipoAssunto;
    private String descricao;
    private String providencias;
    private long duracaoAtendimento;

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
        return providencias;
    }

    public void setProvidencia(String providencias) {
        this.providencias = providencias;
    }

    public long getDuracaoAtendimento() {
        return duracaoAtendimento;
    }

    public void calcularDuracaoAtendimento(Date horaAtendimento) {
        this.duracaoAtendimento = ((new Date()).getTime()) - horaAtendimento.getTime();
    }

    public Assunto(TipoAssunto tipoAssunto, String descricao) {
        this.tipoAssunto = tipoAssunto;
        this.descricao = descricao;
    }
}
