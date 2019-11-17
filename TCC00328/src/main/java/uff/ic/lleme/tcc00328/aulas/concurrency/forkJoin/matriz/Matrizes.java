package uff.ic.lleme.tcc00328.aulas.concurrency.forkJoin.matriz;

import java.util.*;
import java.util.concurrent.*;

public class Matrizes {

    public static double[][] multiplicarSingleThread(double[][] A, double[][] B) {
        Objects.requireNonNull(A, "Matriz A inválida.");
        Objects.requireNonNull(B, "Matriz B inválida.");

        double[][] resultado = new double[A.length][B[0].length];
        for (int i = 0; i < A.length; i++)
            for (int j = 0; j < B.length; j++)
                for (int k = 0; k < A[0].length; k++)
                    resultado[i][j] += A[i][k] * B[k][j];
        return resultado;
    }

    public static double[][] multiplicarForkJoin(double[][] A, double[][] B) {
        Objects.requireNonNull(A, "Matriz A inválida.");
        Objects.requireNonNull(B, "Matriz B inválida.");

        double[][] resultado;
        Multiplicar fb = new Multiplicar(A, B);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(fb);
        resultado = fb.getResultado();

        return resultado;
    }

    public static double[][] rndMatriz(int linhas, int colunas) {
        double[][] matriz = new double[linhas][colunas];
        for (double[] linha : matriz)
            for (int j = 0; j < matriz[0].length; j++)
                linha[j] = (Math.random() * 200) - 100;
        return matriz;
    }

}
