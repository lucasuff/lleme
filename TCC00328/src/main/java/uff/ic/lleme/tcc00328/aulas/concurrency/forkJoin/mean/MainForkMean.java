package uff.ic.lleme.tcc00328.aulas.concurrency.forkJoin.mean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class MainForkMean {

    public static void main(String[] args) throws InterruptedException {
        List<Aluno> alunos = new ArrayList<>();
        Aluno aluno;
        for (int i = 0; i < 50000000; i++) {
            aluno = new Aluno();
            aluno.n1 = Math.random() * 11; // Math.random() --> [0,1)
            aluno.n2 = Math.random() * 11;
            alunos.add(aluno);
        }
        System.out.println("lista de alunos inicializada.");
        //
        //
        Date inicio = new Date();
        for (int i = 0; i < alunos.size(); i++) {
            aluno = alunos.get(i);
            aluno.media = (aluno.n1 + aluno.n2) / 2;
        }
        long duracao1 = new Date().getTime() - inicio.getTime();
        System.out.printf("média calculada por thread única: %d milisegundos%n", duracao1);
        //
        //
        ForkMean fb = new ForkMean(alunos, 0);
        ForkJoinPool pool = new ForkJoinPool(16);
        inicio = new Date();
        pool.invoke(fb);
        long duracao2 = new Date().getTime() - inicio.getTime();
        System.out.printf("média calculada com Fork/Join: %d milisegundos%n", duracao2);

        System.out.printf("diferença: %d milisegundos%n", duracao1 - duracao2);
    }
}
