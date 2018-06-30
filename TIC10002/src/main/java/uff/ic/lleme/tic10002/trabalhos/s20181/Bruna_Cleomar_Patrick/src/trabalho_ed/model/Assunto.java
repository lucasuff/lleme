package uff.ic.lleme.tic10002.trabalhos.s20181.Bruna_Cleomar_Patrick.src.trabalho_ed.model;

import java.nio.ByteBuffer;

public class Assunto extends ObjetoBase {

    /* Classe que representa um Assunto tratado durante um Atendimento a um Cliente
     * Implementada conforme especifica��o do trabalho
	 * (Assunto {tipo, descri��o, provid�ncias, dura��oAtendimento})
     */
    private TipoAssunto tipo;
    private String descricao;
    private String providencias = null;
    private int duracaoAtendimento = 0;

    public Assunto(TipoAssunto tipo, String descricao) {
        this.tipo = tipo;
        this.descricao = descricao;
    }

    public TipoAssunto getTipoAssunto() {
        return this.tipo;
    }

    public String getChave() {
        return this.tipo.getChave();
    }

    public String getDescricao() {
        return this.descricao;
    }

    public String getProvidencias() {
        return providencias;
    }

    public Integer getHash() {
        String chave = this.tipo.getChave();
        ByteBuffer wrapped = ByteBuffer.wrap(chave.getBytes());
        return wrapped.getInt();
    }

    public void setProvidencias(String providencias) {
        this.providencias = providencias;
    }

    public int getDuracaoAtendimento() {
        return duracaoAtendimento;
    }

    public void setDuracaoAtendimento(int duracaoAtendimento) {
        this.duracaoAtendimento = duracaoAtendimento;
    }

}
