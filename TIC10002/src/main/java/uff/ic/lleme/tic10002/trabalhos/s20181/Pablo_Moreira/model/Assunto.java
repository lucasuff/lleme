/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.ic.lleme.tic10002.trabalhos.s20181.Pablo_Moreira.model;

/**
 *
 * @author pablomoreira
 */
public class Assunto {

    public final static int MAX_PROVIDENCIAS = 10;
    private int tipo;
    private String descricao;
    private String[] providencias;
    private int duracao;

    public Assunto(int tipo, String descricao, String[] providencias, int duracao) {
        this.tipo = tipo;
        this.descricao = descricao;
        this.providencias = providencias;
        this.duracao = duracao;
    }

    public Assunto(int tipo, String descricao, int duracao) {
        this.tipo = tipo;
        this.descricao = descricao;
        this.duracao = duracao;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String[] getProvidencias() {
        return providencias;
    }

    public void setProvidencias(String[] providencias) {
        this.providencias = providencias;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

}
