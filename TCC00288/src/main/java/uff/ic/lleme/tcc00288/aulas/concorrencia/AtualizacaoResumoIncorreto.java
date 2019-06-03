package uff.ic.lleme.tcc00288.aulas.concorrencia;

import java.sql.SQLException;
import uff.ic.lleme.tcc00288.aulas.concorrencia.util.Config;

public class AtualizacaoResumoIncorreto {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("*** Execução SEM controle de transação ***");
        System.out.println("");

        {
            boolean controleTransação = false;
            Config.initBD();
            Transacao t2 = iniciarTransacaoT2(controleTransação);
            Transacao t1 = iniciarTransacaoT1(controleTransação);
            t1.join();
            t2.join();
        }

        System.out.println("");
        System.out.println("");
        System.out.println("*** Execução COM controle de transação ***");
        System.out.println("");

        {
            boolean controleTransação = true;
            Config.initBD();
            Transacao t2 = iniciarTransacaoT2(controleTransação);
            Transacao t1 = iniciarTransacaoT1(controleTransação);
            t1.join();
            t2.join();
        }
    }

    private static Transacao iniciarTransacaoT1(boolean controleTransacao) {
        Transacao t = new Transacao(1, controleTransacao) {
            @Override
            public void tarefa() throws SQLException, InterruptedException {

                processar(1000);

                long X = 0;
                int N = 5;
                {// Parte 1
                    X = ler("X");
                    System.out.println(String.format("Transacao 1 faz %1$s = %2$d - %3$d = %4$d", "X", X, N, X - N));
                    X = X - N;
                    escrever("X", X);
                }

                processar(2000);

                long Y = 0;
                {// Parte 2
                    Y = ler("Y");
                    System.out.println(String.format("Transacao 1 faz %1$s = %2$d - %3$d = %4$d", "Y", Y, N, X + N));
                    Y = Y + N;
                    escrever("Y", Y);
                    System.out.println(String.format("Transações devem ler %1$s = %3$d e %2$s = %4$d ou %1$s = %5$d e %2$s = %6$d. (RESUMO INCORRETO)", "X", "Y", X + N, Y - N, X, Y));
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

                long A;
                long soma;
                {// Parte 1
                    System.out.println(String.format("Transacao 2 faz %1$s = %2$d", "soma", 0));
                    soma = 0;
                    A = ler("A");
                    System.out.println(String.format("Transacao 2 faz %1$s = %2$d + %3$d = %4$d", "soma", soma, A, soma + A));
                    soma = soma + A;
                }

                processar(2000);

                long X;
                long Y;
                {// Parte 2
                    X = ler("X");
                    System.out.println(String.format("Transacao 2 faz %1$s = %2$d + %3$d = %4$d", "soma", soma, X, soma + X));
                    soma = soma + X;
                    Y = ler("Y");
                    System.out.println(String.format("Transacao 2 faz %1$s = %2$d + %3$d = %4$d", "soma", soma, Y, soma + Y));
                    soma = soma + Y;
                }
            }
        };
        t.start();
        return t;
    }
}
