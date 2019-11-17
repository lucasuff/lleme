package uff.ic.lleme.tcc00328.aulas.concurrency.executor.forkJoin.matriz;

import java.util.*;
import java.util.concurrent.*;

// tem que ter import java.util.*;
// tem que ter import java.util.concurrent.*;
public class Multiplicar extends RecursiveAction {

    private final double[][] A, B;
    private final int i1, j1, i2, j2, i3, j3, i4, j4;
    private final Resultado resultado;

    public Multiplicar(double[][] A, double[][] B) {
        Objects.requireNonNull(A);
        Objects.requireNonNull(B);

        this.A = A;
        this.B = B;
        this.i1 = 0;
        this.j1 = 0;
        this.i2 = A.length;
        this.j2 = A[0].length;
        this.i3 = 0;
        this.j3 = 0;
        this.i4 = B.length;
        this.j4 = B[0].length;
        this.resultado = new Resultado(A.length, B[0].length);
    }

    private Multiplicar(double[][] A, double[][] B, int i1, int j1, int i2, int j2, int i3, int j3, int i4, int j4, Resultado resultado) {
        Objects.requireNonNull(A);
        Objects.requireNonNull(B);

        this.A = A;
        this.B = B;
        this.i1 = i1;
        this.j1 = j1;
        this.i2 = i2;
        this.j2 = j2;
        this.i3 = i3;
        this.j3 = j3;
        this.i4 = i4;
        this.j4 = j4;
        this.resultado = resultado;
    }

    protected void multiplica() {
        double somatorio;
        for (int i = i1; i < i2; i++)
            for (int j = j3; j < j4; j++) {
                somatorio = 0;
                for (int k = j1; k < j2; k++)
                    somatorio += A[i][k] * B[k][j];
                resultado.addCelula(i, j, somatorio);
            }
    }

    @Override
    protected void compute() {
        int work = (i2 - i1) * (j4 - j3) * (j2 - j1);
        if (work < 1000000)
            multiplica();
        else {
            List<Multiplicar> jobs = new ArrayList<>();
            int im1 = (i1 + i2) / 2;
            int jm1 = (j1 + j2) / 2;
            int im3 = (i3 + i4) / 2;
            int jm3 = (j3 + j4) / 2;

            jobs.add(new Multiplicar(A, B, i1, j1, im1, jm1, i3, j3, im3, jm3, resultado));
            jobs.add(new Multiplicar(A, B, i1, j1, im1, jm1, i3, jm3, im3, j4, resultado));

            jobs.add(new Multiplicar(A, B, im1, j1, i2, jm1, i3, j3, im3, jm3, resultado));
            jobs.add(new Multiplicar(A, B, im1, j1, i2, jm1, i3, jm3, im3, j4, resultado));
            //
            //
            //
            jobs.add(new Multiplicar(A, B, i1, jm1, im1, j2, im3, j3, i4, jm3, resultado));
            jobs.add(new Multiplicar(A, B, i1, jm1, im1, j2, im3, jm3, i4, j4, resultado));

            jobs.add(new Multiplicar(A, B, im1, jm1, i2, j2, im3, j3, i4, jm3, resultado));
            jobs.add(new Multiplicar(A, B, im1, jm1, i2, j2, im3, jm3, i4, j4, resultado));

            invokeAll(jobs);
        }
    }

    public double[][] getResultado() {
        return resultado.getMatriz();
    }

}
