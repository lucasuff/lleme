/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.ic.lleme.tic10002.trabalhos.s20181.LuizAntonio_e_Carlos.model;

/**
 *
 * @author Luiz Antonio
 */
public class Assunto {

    private TipoAssunto tipo;
    private String descricao;
    private String providencias;
    // duracao do assunto em minutos
    private int duracaoAtendimento;

    public Assunto(TipoAssunto tipo, String descricao) {
        this.tipo = tipo;
        this.descricao = descricao;
        this.providencias = null;
        this.duracaoAtendimento = 0;
    }

    public TipoAssunto getTipo() {
        return this.tipo;
    }

    public void setTipo(TipoAssunto tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getProvidencias() {
        return this.providencias;
    }

    public void setProvidencias(String providencias) {
        this.providencias = providencias;
    }

    public int getDuracaoAtendimento() {
        return this.duracaoAtendimento;
    }

    public void setDuracaoAtendimento(int duracaoAtendimento) {
        this.duracaoAtendimento = duracaoAtendimento;
    }

    @Override
    public String toString() {
        return "[Informações do Assunto: " + this.tipo + ", Descrição do Assunto: " + this.descricao + ", Providências Tomadas: " + this.providencias + ", Duração do Assunto: " + this.duracaoAtendimento + "]\n";
    }
}
