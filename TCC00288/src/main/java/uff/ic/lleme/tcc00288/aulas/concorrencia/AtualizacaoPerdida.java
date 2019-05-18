package uff.ic.lleme.tcc00288.aulas.concorrencia;

import java.sql.SQLException;
import uff.ic.lleme.tcc00288.aulas.concorrencia.util.Config;
import uff.ic.lleme.tcc00288.aulas.concorrencia.util.Transacao;

public class AtualizacaoPerdida {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("*** Execução SEM controle de transação ***");
        System.out.println("");

        {
            boolean controleTransação = false;
            Config.initBD();
            Transacao t1 = iniciarTransacaoT1(controleTransação);
            Transacao t2 = iniciarTransacaoT2(controleTransação);
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
            Transacao t1 = iniciarTransacaoT1(controleTransação);
            Transacao t2 = iniciarTransacaoT2(controleTransação);
            t1.join();
            t2.join();
        }
    }

    private static Transacao iniciarTransacaoT1(boolean controleTransacao) {
        Transacao t = new Transacao(1, controleTransacao) {
            @Override
            public void tarefa() throws SQLException, InterruptedException {

                long x;
                int N = 3;
                {// Parte 1
                    x = ler("X");
                    N = 5;
                    System.out.println(String.format("Transacao 1 faz x = %d - %d = %d", x, N, x - N));
                    x = x - N;
                }

                processar(2000);

                long y;
                {// Parte 2
                    escrever("X", x);
                    y = ler("Y");
                }

                processar(2000);

                {// Parte 3
                    System.out.println(String.format("Transacao 1 faz y = %d + %d = %d", y, N, y + N));
                    y = y + N;
                    escrever("Y", y);
                }

                long newX;
                {// Parte 4
                    System.out.println("");
                    newX = ler("X");
                    if (newX != x)
                        System.out.println(String.format("Transacao 1 deveria ter lido x = %1$d, conforme foi gravado!!! (ATUALIZAÇÃO PERDIDA)", x));
                    else
                        System.out.println(String.format("Transacao 1 leu x = %1$d conforme foi gravado. (OK)", x));
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

                processar(1000);

                long x = 0;
                {// Parte 1
                    x = ler("X");
                    int N = 8;
                    System.out.println(String.format("Transacao 2 faz x = %d + %d = %d", x, N, x + N));
                    x = x + N;
                }

                processar(2000);

                {// Parte 2
                    escrever("X", x);
                }
            }
        };
        t.start();
        return t;
    }
}
