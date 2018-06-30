/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.ic.lleme.tic10002.trabalhos.s20181.Juan_e_Silvio.src.model;

/**
 *
 * @author juan__000
 */
public class Contador {

    private float[] soma;
    private int[] quantidade;

    public Contador(int numContadores) {
        soma = new float[numContadores];
        quantidade = new int[numContadores];
    }

    public void adiciona(int tipo, float duracao) {
        soma[tipo - 1] += duracao;
        quantidade[tipo - 1]++;
    }

    public float getMedia(int tipo) {
        if (soma[tipo - 1] > 0)
            return soma[tipo - 1] / quantidade[tipo - 1];
        else
            return 0;
    }

}
