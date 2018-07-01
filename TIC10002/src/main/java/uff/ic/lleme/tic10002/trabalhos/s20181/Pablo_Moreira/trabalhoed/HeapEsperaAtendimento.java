/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.ic.lleme.tic10002.trabalhos.s20181.Pablo_Moreira.trabalhoed;

import java.util.*;

/**
 *
 * @author pablomoreira Implementação de MaxHeap
 */
public class HeapEsperaAtendimento {

    private ParDeEspera heapAtendimento[];
    private static final int HEAP_STD_SIZE = 100;
    private int heapSize;

    public HeapEsperaAtendimento() {
        heapAtendimento = new ParDeEspera[HEAP_STD_SIZE];
        heapSize = 0;
    }

    public void adicionar(Atendimento atendimento, float prioridade) {

        ParDeEspera novoPar = new ParDeEspera(atendimento, prioridade);
        if (heapSize == heapAtendimento.length)
            resizeHeap();

        heapAtendimento[heapSize] = novoPar;
        heapSize++;
        sobeFilho(heapSize - 1);

    }

    private void resizeHeap() {
        ParDeEspera[] largerArr = Arrays.copyOf(heapAtendimento, heapSize + HEAP_STD_SIZE);
        heapAtendimento = largerArr;

    }

    private void sobeFilho(int i) {

        if (i > 0) {
            int resto;
            if (i % 2 == 0)
                resto = 2;
            else
                resto = 1;

            if (heapAtendimento[i].getPrioridade() > heapAtendimento[(i - resto) / 2].getPrioridade()) {
                ParDeEspera aux = heapAtendimento[i];
                heapAtendimento[i] = heapAtendimento[(i - resto) / 2];
                heapAtendimento[(i - resto) / 2] = aux;

                sobeFilho((i - resto) / 2);
            }
        }
    }

    public Atendimento obterAtendimento() {

        ParDeEspera removido = heapAtendimento[0];

        heapAtendimento[0] = heapAtendimento[heapSize - 1];
        heapAtendimento[heapSize - 1] = null;
        heapSize--;

        reordenarHeap();

        return removido.getAtendimento();

    }

    private void reordenarHeap() {
        int indice = 0;

        while (indice < heapSize) {

            int maiorIndice = 0;
            int indiceEsq = indice * 2 + 1;
            int indiceDir = indice * 2 + 2;

            if (heapAtendimento[indiceDir] == null)
                maiorIndice = indiceEsq;
            else if (heapAtendimento[indiceEsq].getPrioridade() > heapAtendimento[indiceDir].getPrioridade())
                maiorIndice = indiceEsq;
            else
                maiorIndice = indiceDir;

            if (heapAtendimento[maiorIndice] == null)
                break;
            else if (heapAtendimento[maiorIndice].getPrioridade() > heapAtendimento[indice].getPrioridade()) {
                ParDeEspera aux = heapAtendimento[indice];
                heapAtendimento[indice] = heapAtendimento[maiorIndice];
                heapAtendimento[maiorIndice] = aux;
                indice = maiorIndice;
            } else
                break;

        }
    }

    public void alterarPrioridade(Atendimento atendimento, int novaPrioridade) {
        int chave = -1;
        for (int i = 0; i < heapAtendimento.length; i++)

            if (heapAtendimento[i].getAtendimento().getCliente().getCpf().equals(atendimento.getCliente().getCpf()) == true) {
                chave = i;
                break;
            }

        if (chave > 0) {
            heapAtendimento[chave].setPrioridade(novaPrioridade);
            sobeFilho(chave);
        }
    }

}
