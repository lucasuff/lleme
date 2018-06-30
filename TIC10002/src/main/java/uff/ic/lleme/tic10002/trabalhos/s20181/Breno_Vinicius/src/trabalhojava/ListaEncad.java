/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author vinib
 */
package uff.ic.lleme.tic10002.trabalhos.s20181.Breno_Vinicius.src.trabalhojava;

import uff.ic.lleme.tic10002.trabalhos.s20181.Breno_Vinicius.src.trabalhojava.model.Cliente;

public class ListaEncad {

    private class No {

        public Cliente conteudo;
        public No proximo = null;
        public No anterior = null;

        private No() {

        }

        public No(Cliente objeto) {
            this.conteudo = objeto;
        }
    }

    private No primeiro = null;

    public Cliente buscar(String chave) {
        if (primeiro == null)
            return null;
        else if (primeiro.conteudo.nome == chave)
            return primeiro.conteudo;
        else
            return buscar(chave, primeiro);

    }

    private Cliente buscar(String chave, No atual) {

        if (atual.proximo != null)

            return null;

        else if (atual.proximo.conteudo.nome == chave)

            return atual.proximo.conteudo;

        else

            return buscar(chave, atual.proximo);

    }

    public Cliente obter(int pos) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void incluir(Cliente objeto) {
        if (primeiro == null)
            primeiro = new No(objeto);
        else
            incluir(objeto, primeiro);
    }

    private void incluir(Cliente objeto, No no) {
        if (no.proximo == null) {
            no.proximo = new No(objeto);
            no.proximo.anterior = no;
        } else
            incluir(objeto, no.proximo);
    }

    public void excluir(String chave) {
        if (primeiro == null)
            System.out.print("Nao há objetos");
        else if (primeiro.conteudo.nome == chave) {
            primeiro = primeiro.proximo;
            primeiro.proximo.anterior = null;
        } else
            excluir(chave, primeiro);
    }

    private void excluir(String chave, No atual) {

        if (atual.proximo.conteudo.nome == chave) {
            No aux = new No();
            aux = atual.proximo.anterior;
            aux.proximo = atual.proximo.proximo;
            atual.proximo.proximo.anterior = aux;
        } else
            excluir(chave, atual.proximo);
    }

}
