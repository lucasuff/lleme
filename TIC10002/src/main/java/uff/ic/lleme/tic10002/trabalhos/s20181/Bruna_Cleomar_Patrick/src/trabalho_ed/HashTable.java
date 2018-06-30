package uff.ic.lleme.tic10002.trabalhos.s20181.Bruna_Cleomar_Patrick.src.trabalho_ed;

import uff.ic.lleme.tic10002.trabalhos.s20181.Bruna_Cleomar_Patrick.src.trabalho_ed.model.ObjetoBase;
import java.nio.ByteBuffer;

public class HashTable {

    ListaEstatica lista;

    public HashTable(int tamanho) {
        lista = new ListaEstatica(tamanho);
        for (int i = 0; i < tamanho; i++) {
            ListaEncadeada listaEnc = new ListaEncadeada();
            lista.Inserir(listaEnc);
        }
    }

    private int hash(String chave) {
        ByteBuffer wrapped = ByteBuffer.wrap(chave.getBytes());
        return wrapped.getInt() % (lista.tamanho);
    }

    public ObjetoBase Buscar(String chave) {
        ListaEncadeada listaEnc = (ListaEncadeada) lista.get(hash(chave));
        return listaEnc.Buscar(chave);
    }

    public void Inserir(String chave, ObjetoBase obj) {
        ListaEncadeada listaEnc = (ListaEncadeada) lista.get(hash(chave));
        listaEnc.Inserir(obj);
    }

}
