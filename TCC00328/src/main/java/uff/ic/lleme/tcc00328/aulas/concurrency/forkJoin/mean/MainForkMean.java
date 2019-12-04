package uff.ic.lleme.tcc00328.aulas.concurrency.executor.forkJoin.mean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class MainForkMean {

    public static void main(String[] args) throws InterruptedException {
        long duracao1, duracao2;
        List<Aluno> alunos = new ArrayList<>();

        { // inicialização da lista de alunos
            for (int i = 0; i < 50000000; i++) {
                Aluno aluno = new Aluno(i);
                aluno.n1 = Math.random() * 11; // Math.random() --> [0,1)
                aluno.n2 = Math.random() * 11;
                alunos.add(aluno);
            }
            System.out.println("lista de alunos inicializada.");
        }

        { // Processamento com thread única
            Date inicio = new Date();
            for (int i = 0; i < alunos.size(); i++) {
                Aluno aluno = alunos.get(i);
                aluno.situacao = (aluno.n1 + aluno.n2) / 2 >= 6 ? "aprovado" : "reprovado";
            }
            Date fim = new Date();

            duracao1 = fim.getTime() - inicio.getTime();
            System.out.printf("média calculada por thread única: %d milisegundos%n", duracao1);
        }

        { // Processamento concorrente
            ForkMean fb = new ForkMean(alunos);
            ForkJoinPool pool = new ForkJoinPool();

            Date inicio = new Date();
            pool.invoke(fb);
            Date fim = new Date();

            duracao2 = fim.getTime() - inicio.getTime();
            System.out.printf("média calculada com Fork/Join: %d milisegundos%n", duracao2);
        }

        System.out.printf("diferença: %d milisegundos%n", duracao1 - duracao2);
    }
}
