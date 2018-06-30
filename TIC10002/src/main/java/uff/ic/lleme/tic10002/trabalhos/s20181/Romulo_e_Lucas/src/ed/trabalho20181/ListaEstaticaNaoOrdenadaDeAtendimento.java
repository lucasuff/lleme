/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.ic.lleme.tic10002.trabalhos.s20181.Romulo_e_Lucas.src.ed.trabalho20181;

/**
 *
 * @author romul
 */
public class ListaEstaticaNaoOrdenadaDeAtendimento {

    private final int delta = 20;
    private int tamanho = 0;
    private Atendimento[] lista = new Atendimento[delta];

    // public Atendimento buscar(int chave) {}
    // public Atendimento obter(int pos) {}
    public void incluir(Atendimento objeto) {
        if (tamanho < lista.length - 1)
            this.lista[tamanho++] = objeto;
        else
            expandir();
    }

    public void excluir(int chave) {
        this.lista[chave] = this.lista[--tamanho];
        this.lista[tamanho] = null;
    }

    private Atendimento[] expandir() {
        Atendimento[] novaLista = new Atendimento[this.lista.length + delta];
        for (int i = 0; i < this.lista.length; i++)
            novaLista[i] = this.lista[i];
        return novaLista;
    }

    public Atendimento acessar(int pos) {
        return this.lista[pos];
    }

    public int getTamanho() {
        return tamanho;
    }
}
