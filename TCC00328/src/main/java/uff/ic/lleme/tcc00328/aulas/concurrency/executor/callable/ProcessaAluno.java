package uff.ic.lleme.tcc00328.aulas.concurrency.executor.callable;

import java.util.concurrent.Callable;

public class ProcessaAluno implements Callable<Aluno> {

    private Aluno aluno;

    public ProcessaAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    @Override
    public Aluno call() throws Exception {
        aluno.situacao = (aluno.n1 + aluno.n2) / 2 >= 6 ? "aprovado" : "reprovado";
        return aluno;
    }

}
