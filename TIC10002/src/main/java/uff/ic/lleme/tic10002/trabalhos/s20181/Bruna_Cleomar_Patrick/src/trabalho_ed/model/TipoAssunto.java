package uff.ic.lleme.tic10002.trabalhos.s20181.Bruna_Cleomar_Patrick.src.trabalho_ed.model;

import uff.ic.lleme.tic10002.trabalhos.s20181.Bruna_Cleomar_Patrick.src.trabalho_ed.model.ObjetoBase;
import java.nio.ByteBuffer;

public class TipoAssunto extends ObjetoBase {

    private String tipo;
    private String titulo;
    private int urgencia;
    private int totalAtendimentos;
    private long totalMinutosAtendimento;

    public TipoAssunto(String tipo, String titulo, int urgencia) {
        this.tipo = tipo;
        this.titulo = titulo;
        this.urgencia = urgencia;
    }

    public Integer getHash() {
        String chave = this.tipo;
        ByteBuffer wrapped = ByteBuffer.wrap(chave.getBytes());
        return wrapped.getInt();
    }

    public String titulo() {
        return this.titulo;
    }

    public String getChave() {
        return this.tipo;
    }

    public TipoAssunto getConteudo() {
        return this;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public int getUrgencia() {
        return this.urgencia;
    }

    public long getTotalMinutosAtendimento() {
        return this.totalMinutosAtendimento;
    }

    public int getTotalAtendimentos() {
        return this.totalAtendimentos;
    }

    public void setTotalMinutosAtendimento(long minutos) {
        this.totalMinutosAtendimento = this.totalMinutosAtendimento + minutos;
    }

    public void setTotalAtendimentos() {
        this.totalAtendimentos++;
    }
}
