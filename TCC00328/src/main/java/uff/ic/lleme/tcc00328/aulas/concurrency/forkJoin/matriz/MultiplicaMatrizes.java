package uff.ic.lleme.tcc00328.aulas.concurrency.forkJoin.matriz;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class MultiplicaMatrizes extends RecursiveAction {

    private final double[][] a, b;
    private int i1, j1, i2, j2;
    public static Cell[][] resultado;

    public MultiplicaMatrizes(double[][] a, double[][] b, int i1, int j1, int i2, int j2) {
        this.a = a;
        this.b = b;
        this.i1 = i1;
        this.j1 = j1;
        this.i2 = i2;
        this.j2 = j2;
        if (resultado == null)
            resultado = new Cell[a.length][b[0].length];
    }

    protected void multiplica() {
        for (int i = i1; i < i2; i++)
            for (int j = j1; j < j2; j++)
                for (int k = 0; k < a[0].length; k++)
                    resultado[i][j].setValue(resultado[i][j].getValue() + a[i][k] * b[k][j]);
        System.out.format("(%d,%d)-(%d,%d)\n", i1, j1, i2, j2);
    }

    @Override
    protected void compute() {
        if ((i2 - i1) * (j2 - j1) < 10000)
            multiplica();
        else {
            List<MultiplicaMatrizes> jobs = new ArrayList<>();
            jobs.add(new MultiplicaMatrizes(a, b, i1, j1, (i1 + i2) / 2, (j1 + j2) / 2));
            jobs.add(new MultiplicaMatrizes(a, b, i1, (j1 + j2) / 2, (i1 + i2) / 2, j2));
            jobs.add(new MultiplicaMatrizes(a, b, (i1 + i2) / 2, j1, i2, (j1 + j2) / 2));
            jobs.add(new MultiplicaMatrizes(a, b, (i1 + i2) / 2, (j1 + j2) / 2, i2, j2));
            invokeAll(jobs);
        }
    }
}
