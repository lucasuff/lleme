package uff.ic.lleme.tcc00288.aulas.concorrencia;

import java.sql.SQLException;
import uff.ic.lleme.tcc00288.aulas.concorrencia.util.Config;
import uff.ic.lleme.tcc00288.aulas.concorrencia.util.Transacao;

public class AtualizacaoTemporaria {

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

                long x = 0;
                int N = 5;
                {// Parte 1
                    x = ler("X");
                    System.out.println(String.format("Transação 1 faz x = %d - %d = %d", x, N, x - N));
                    x = x - N;
                    escrever("X", x);
                }

                processar(2000); // simulação carga de processamdento em outras atividades

                long y = 0;
                {// Parte 2
                    y = ler("Y");
                    System.out.println(String.format("Nenhuma transação deveria ter lido X = %1$d porque operação será desfeita.", x));
                    throw new SQLException("Erro no processamento");
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

                long x;
                int M = 8;
                {// Parte 1
                    x = ler("X");
                    if (!controleTransacao)
                        System.out.println("                      (ATUALIZAÇÃO TEMPORÁRIA)");
                    System.out.println(String.format("Transação 2 faz x = %d - %d = %d", x, M, x - M));
                    x = x - M;
                    escrever("X", x);
                }

                processar(2000); // simulação carga de processamdento em outras atividades
            }
        };
        t.start();
        return t;
    }
}
