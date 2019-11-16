package uff.ic.lleme.tcc00328.aulas.concurrency.forkJoin.matriz;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.RecursiveTask;

public class Multiplicar extends RecursiveTask<double[][]> {

    private final double[][] a, b;
    private int i1, j1, i2, j2, i3, j3, i4, j4;
    private Resultado resultado;

    public Multiplicar(double[][] a, double[][] b) {
        Objects.requireNonNull(a);
        Objects.requireNonNull(b);

        this.a = a;
        this.b = b;
        this.i1 = 0;
        this.j1 = 0;
        this.i2 = a.length;
        this.j2 = a[0].length;
        this.i3 = 0;
        this.j3 = 0;
        this.i4 = b.length;
        this.j4 = b[0].length;
        this.resultado = new Resultado(a.length, b[0].length);
    }

    private Multiplicar(double[][] a, double[][] b, int i1, int j1, int i2, int j2, int i3, int j3, int i4, int j4, Resultado resultado) {
        Objects.requireNonNull(a);
        Objects.requireNonNull(b);

        this.a = a;
        this.b = b;
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
        for (int i = i1; i < i2; i++)
            for (int j = j3; j < j4; j++)
                for (int k = j1; k < j2; k++)
                    resultado.addCelula(i, j, a[i][k] * b[k][j]);
    }

    @Override
    protected double[][] compute() {
        if ((i2 - i1) * (j4 - j3) * (j2 - j1) < 10000)
            multiplica();
        else {
            List<Multiplicar> jobs = new ArrayList<>();
            //jobs.add(new Multiplicar(a, b, i1, j1, (i1 + i2) / 2, (j1 + j2) / 2, resultado));

            invokeAll(jobs);
        }
        return null;
    }

}
