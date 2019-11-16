package uff.ic.lleme.tcc00328.aulas.concurrency.forkJoin.matriz;

import java.util.concurrent.locks.ReentrantLock;

public class Resultado {

    private Object locks[][] = null;
    private double[][] matriz = null;

    public Resultado(int linhas, int colunas) {
        locks = new ReentrantLock[linhas][colunas];
        matriz = new double[linhas][colunas];

        for (int i = 0; i < locks.length; i++)
            for (int j = 0; j < locks.length; j++)
                locks[i][j] = new Object();
    }

    public void addCelula(int i, int j, double valor) {
        synchronized (locks[i][j]) {
            matriz[i][j] += valor;
        }
    }

    public void setCelula(int i, int j, double valor) {
        synchronized (locks[i][j]) {
            matriz[i][j] = valor;
        }
    }

    public double getCelula(int i, int j) {
        return matriz[i][j];
    }

    public double[][] getMatriz() {
        return clonar(matriz);
    }

    private static double[][] clonar(double[][] src) {
        int length = src.length;
        double[][] target = new double[length][src[0].length];
        for (int i = 0; i < length; i++)
            System.arraycopy(src[i], 0, target[i], 0, src[i].length);
        return target;
    }
}
