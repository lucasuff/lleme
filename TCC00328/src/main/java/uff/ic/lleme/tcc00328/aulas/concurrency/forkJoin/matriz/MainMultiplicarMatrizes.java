package uff.ic.lleme.tcc00328.aulas.concurrency.forkJoin.matriz;

import java.util.Date;
import java.util.concurrent.ForkJoinPool;

public class MainMultiplicarMatrizes {

    public static void main(String[] args) {
        double[][] a = novaMatriz(1000, 580);
        double[][] b = novaMatriz(580, 1200);
        MultiplicaMatrizes fb = new MultiplicaMatrizes(a, b, 0, 0, a.length, b[0].length);
        ForkJoinPool pool = new ForkJoinPool();
        Date inicio = new Date();
        pool.invoke(fb);
        System.out.printf("%d milisegundos%n", (new Date().getTime() - inicio.getTime()));
    }

    private static double[][] novaMatriz(int linhas, int colunas) {
        double[][] matriz = new double[linhas][colunas];
        for (double[] linha : matriz)
            for (int j = 0; j < matriz[0].length; j++)
                linha[j] = n();
        return matriz;
    }

    private static double n() {
        return (Math.random() * 200) - 100;
    }
}
