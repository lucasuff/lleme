package br.uff.ed.colecao;

import java.io.Serializable;

@SuppressWarnings("rawtypes")
public class No<T extends Comparable> implements Serializable {

    private static final long serialVersionUID = -5612439628913773063L;
    protected T conteudo;

    public No() {

    }

    public No(T conteudo) {
        this.conteudo = conteudo;
    }

    public Object getConteudo() {
        return conteudo;
    }

    public void setConteudo(T conteudo) {
        this.conteudo = conteudo;
    }

}
