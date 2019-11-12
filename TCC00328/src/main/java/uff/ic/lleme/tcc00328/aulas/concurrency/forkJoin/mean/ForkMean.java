package uff.ic.lleme.tcc00328.aulas.concurrency.forkJoin.mean;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class ForkMean extends RecursiveAction {

    private int inicio = 0;
    private int quantidade = 0;
    private List<Aluno> alunos = new ArrayList<>();
    protected static int tamanhoJob = 100000;

    public ForkMean(List<Aluno> alunos, int inicio) {
        this.alunos = alunos;
        this.inicio = inicio;
        this.quantidade = alunos.size();
    }

    private ForkMean(List<Aluno> alunos, int inicio, int quantidade) {
        this.alunos = alunos;
        this.inicio = inicio;
        this.quantidade = quantidade;
    }

    protected void computeDirectly() {
        for (int i = inicio; i < inicio + quantidade; i++) {
            Aluno aluno = alunos.get(i);
            aluno.situacao = (aluno.n1 + aluno.n2) / 2 >= 6 ? "aprovado" : "reprovado";
        }
    }

    @Override
    protected void compute() {
        if (quantidade < tamanhoJob)
            computeDirectly();
        else {
            int meio = quantidade / 2;
            invokeAll(new ForkMean(alunos, inicio, meio),
                    new ForkMean(alunos, inicio + meio, quantidade - meio));
        }
    }
}
