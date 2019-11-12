package uff.ic.lleme.tcc00328.aulas.concurrency.executor.callable;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainCallable {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        boolean errors = false;
        int recebidos = 0, enviados = 0;
        ExecutorService executor = Executors.newFixedThreadPool(16);
        CompletionService<Aluno> gerenciador = new ExecutorCompletionService<>(executor);

        { // inicialização da lista de alunos
            for (int i = 0; i < 500; i++) {
                Aluno aluno = new Aluno(i);
                aluno.n1 = Math.random() * 11; // Math.random() --> [0,1)
                aluno.n2 = Math.random() * 11;

                gerenciador.submit(new ProcessaAluno(aluno));
                enviados++;
            }
        }

        {
            while (recebidos < enviados && !errors)
                try {
                    Aluno aluno = gerenciador.take().get();
                    System.out.println(aluno.nome + ": " + aluno.situacao);
                    recebidos++;
                } catch (Exception e) {
                    errors = true;
                }
        }
    }
}
