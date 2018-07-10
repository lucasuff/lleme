/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.ic.lleme.tic10002.trabalhos.s20181.Romulo_e_Lucas;

import uff.ic.lleme.tic10002.trabalhos.s20181.Romulo_e_Lucas.model.Atendimento;
import java.util.Date;

/**
 * Nesta tabela hash, a posição é a data, ou seja, cada posicao representa uma
 * data. O conteúdo de cada posição da tabela é uma lista de Atendimentos
 * encerrados naquele dia.
 */
public class HashAtendimento {

    int delta = 10;
    ListaEstaticaNaoOrdenadaDeAtendimento[] tabela = new ListaEstaticaNaoOrdenadaDeAtendimento[delta];

    public void armazenar(Atendimento a) {
        int posicao = calcularPosicao(a.getHoraAtendimento());
        if (posicao < tabela.length) {
            ListaEstaticaNaoOrdenadaDeAtendimento lista = new ListaEstaticaNaoOrdenadaDeAtendimento();
            // pegar lista se já existe
            if (tabela[posicao] != null)
                lista = tabela[posicao];
            lista.incluir(a);
            tabela[posicao] = lista;
        } else {
            // se a data for maior que a qtd de linhas na tabela, então
            // cria uma tabela com aquele tamanho de linhas
            expandir(posicao);
            armazenar(a);
        }
    }

    // passar data desejada
    public ListaEstaticaNaoOrdenadaDeAtendimento acessar(Date data) {
        int posicao = this.calcularPosicao(data);
        return tabela[posicao];
    }

    private int calcularPosicao(Date data) {
        // pegar a data de quando foi feito o atendimento
        int dataTemp = (data.getDate());
        // transformar data em posicao
        if (tabela.length == 0)
            return 0;
        return (dataTemp);
    }

    private void expandir(int tamanhoNecessario) {
        ListaEstaticaNaoOrdenadaDeAtendimento[] novaTabela = new ListaEstaticaNaoOrdenadaDeAtendimento[tamanhoNecessario + 1];
        for (int i = 0; i < this.tabela.length; i++)
            novaTabela[i] = this.tabela[i];
        this.tabela = novaTabela;
    }
}
