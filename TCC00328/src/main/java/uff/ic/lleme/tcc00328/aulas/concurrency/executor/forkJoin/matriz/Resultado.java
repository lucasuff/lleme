package uff.ic.lleme.tcc00328.aulas.concurrency.executor.forkJoin.matriz;

public class Resultado {

    private Object locks[][] = null;
    private double[][] matriz = null;

    private Resultado() {
    }

    public Resultado(int linhas, int colunas) {
        locks = new Object[linhas][colunas];
        matriz = new double[linhas][colunas];

        for (Object[] lock : locks)
            for (int j = 0; j < locks[0].length; j++)
                lock[j] = new Object();
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
        //return clonar(matriz);
        return matriz;
    }

    private static double[][] clonar(double[][] src) {
        int length = src.length;
        double[][] target = new double[length][src[0].length];
        for (int i = 0; i < length; i++)
            System.arraycopy(src[i], 0, target[i], 0, src[i].length);
        return target;
    }
}
