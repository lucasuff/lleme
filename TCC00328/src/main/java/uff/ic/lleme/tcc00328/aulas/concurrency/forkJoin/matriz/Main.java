package uff.ic.lleme.tcc00328.aulas.concurrency.forkJoin.matriz;

import java.util.Date;
import org.junit.Assert;

public class Main {

    public static void main(String[] args) {
        double[][] A = Matrizes.rndMatriz(1000, 580);
        double[][] B = Matrizes.rndMatriz(580, 800);
        double[][] resultado1;
        double[][] resultado2;

        {
            Date inicio = new Date();
            resultado1 = Matrizes.multiplicarSingleThread(A, B);
            Date fim = new Date();
            long duracao = fim.getTime() - inicio.getTime();
            System.out.printf("multiplicacao usando thread única: %d milisegundos%n", duracao);
        }

        {
            Date inicio = new Date();
            resultado2 = Matrizes.multiplicarForkJoin(A, B);
            Date fim = new Date();
            long duracao = fim.getTime() - inicio.getTime();
            System.out.printf("multiplicacao usando Jork/Join: %d milisegundos%n", duracao);
        }

        for (int i = 0; i < resultado1.length; i++)
            Assert.assertArrayEquals(resultado1[i], resultado2[i], 0.0001);
    }

}
