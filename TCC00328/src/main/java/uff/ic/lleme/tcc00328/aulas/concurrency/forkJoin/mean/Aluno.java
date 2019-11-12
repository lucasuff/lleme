package uff.ic.lleme.tcc00328.aulas.concurrency.forkJoin.mean;

public class Aluno {

    public String nome = null;
    public double n1 = 0;
    public double n2 = 0;
    public double media = 0;

    public synchronized void setN1(double n1) {
        this.n1 = n1;
        media = (n1 + n2) / 2;
    }

    public synchronized void setN2(double n2) {
        this.n2 = n2;
        media = (n1 + n2) / 2;
    }

    public synchronized double getMedia() {
        return media;
    }
}
