package uff.ic.lleme.tcc00288.aulas.concorrencia;

import java.sql.SQLException;
import uff.ic.lleme.tcc00288.aulas.concorrencia.util.Config;
import uff.ic.lleme.tcc00288.aulas.concorrencia.util.Transacao;

public class AtualizacaoTemporaria {

    public static void main(String[] args) throws InterruptedException {
        boolean controleTransação = false;
        Config.initBD();
        Transacao t1 = iniciarTransacaoT1(controleTransação);
        Transacao t2 = iniciarTransacaoT2(controleTransação);
        t1.join();
        t2.join();
    }

    private static Transacao iniciarTransacaoT1(boolean controleTransacao) {
        Transacao t = new Transacao(1, controleTransacao) {
            @Override
            public void tarefa() throws SQLException, InterruptedException {

                long x = 0;
                {// Parte 1
                    x = lerX("");
                    int N = 5;
                    System.out.println(String.format("Transacao 1 faz x = %d - %d = %d", x, N, x - N));
                    x = x - N;
                    escreverX(x);
                }

                processar(2000); // simulação carga de processamdento em outras atividades

                long y = 0;
                {// Parte 2
                    y = lerY("for update");
                    System.out.println("Transacao 1 encerra.");
                    rollback();
                }

            }
        };
        t.start();
        return t;
    }

    private static Transacao iniciarTransacaoT2(boolean controleTransacao) {
        Transacao t = new Transacao(2, controleTransacao) {
            @Override
            public void tarefa() throws SQLException, InterruptedException {

                processar(1000); // simulação carga de processamdento em outras atividades

                long x = 0;
                int M = 0;
                {// Parte 1
                    x = lerX("");
                    M = 8;
                    System.out.println(String.format("Transacao 2 faz x = %d - %d = %d", x, M, x - M));
                    x = x - M;
                    escreverX(x);
                }

                processar(2000); // simulação carga de processamdento em outras atividades

                long novoX = lerX("");
                if (novoX == x + M)
                    System.out.println(String.format("Transacao 2 continua lendo o valor x = %d <--------", novoX));
                else
                    System.out.println(String.format("Transacao 2 perde o valor x = %d que agora é x = %d <--------", x + M, novoX));
            }
        };
        t.start();
        return t;
    }
}
