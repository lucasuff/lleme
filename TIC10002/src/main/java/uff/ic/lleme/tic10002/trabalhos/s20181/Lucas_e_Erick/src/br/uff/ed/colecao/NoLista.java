package br.uff.ed.colecao;

import java.io.Serializable;

@SuppressWarnings("rawtypes")
public class NoLista<T extends Comparable> extends No<T> implements Serializable {

    private static final long serialVersionUID = -8818893711843410556L;
    No<T> proxNo;
    private int chave;

    public NoLista() {
    }

    public NoLista(T conteudo, int chave) {
        this.conteudo = conteudo;
        this.chave = chave;
    }

    public No<T> getProxNo() {
        return proxNo;
    }

    public void setProxNo(No<T> proxNo) {
        this.proxNo = proxNo;
    }

    public int getChave() {
        return chave;
    }

    public void setChave(int chave) {
        this.chave = chave;
    }

}
