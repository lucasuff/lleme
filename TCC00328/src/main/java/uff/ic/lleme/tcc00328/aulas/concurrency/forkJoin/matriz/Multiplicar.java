package uff.ic.lleme.tcc00328.aulas.concurrency.forkJoin.matriz;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class Multiplicar extends RecursiveTask<double[][]> {

    private final double[][] a, b;
    private int i1, j1, i2, j2;
    private Resultado resultado;

    public Multiplicar(double[][] a, double[][] b) {
        this.a = a;
        this.b = b;
        this.resultado = new Resultado(a.length, b[0].length);
    }

    private Multiplicar(double[][] a, double[][] b, int i1, int j1, int i2, int j2, Resultado resultado) {
        this.a = a;
        this.b = b;
        this.i1 = i1;
        this.j1 = j1;
        this.i2 = i2;
        this.j2 = j2;
        this.resultado = resultado;
    }

    protected void multiplica() {
        for (int i = i1; i < i2; i++)
            for (int j = j1; j < j2; j++)
                for (int k = 0; k < a[0].length; k++)
                    resultado.adicionar(a[i][k] * b[k][j], i, j);
    }

    @Override
    protected double[][] compute() {
        if ((i2 - i1) * (j2 - j1) < 10000)
            multiplica();
        else {
            List<Multiplicar> jobs = new ArrayList<>();
            jobs.add(new Multiplicar(a, b, i1, j1, (i1 + i2) / 2, (j1 + j2) / 2, resultado));
            jobs.add(new Multiplicar(a, b, i1, (j1 + j2) / 2, (i1 + i2) / 2, j2, resultado));
            jobs.add(new Multiplicar(a, b, (i1 + i2) / 2, j1, i2, (j1 + j2) / 2, resultado));
            jobs.add(new Multiplicar(a, b, (i1 + i2) / 2, (j1 + j2) / 2, i2, j2, resultado));
            invokeAll(jobs);
        }
        return null;
    }

}
