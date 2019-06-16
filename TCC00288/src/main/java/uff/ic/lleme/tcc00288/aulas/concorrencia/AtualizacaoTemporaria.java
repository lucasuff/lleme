package uff.ic.lleme.tcc00288.aulas.concorrencia;

import java.sql.SQLException;
import uff.ic.lleme.tcc00288.aulas.concorrencia.util.Config;

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

            Transacao t3 = iniciarTransacaoT3(controleTransação);
            t3.join();
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

            Transacao t3 = iniciarTransacaoT3(controleTransação);
            t3.join();
        }
    }

    private static Transacao iniciarTransacaoT1(boolean controleTransacao) {
        Transacao t = new Transacao(1, controleTransacao) {
            @Override
            public void tarefa() throws SQLException, InterruptedException {

                long X = 0;
                int N = 5;
                {// Parte 1
                    X = ler("X");
                    System.out.println(String.format("Transação 1 faz x = %d - %d = %d", X, N, X - N));
                    X = X - N;
                    escrever("X", X);
                }

                processar(2000); // simulação carga de processamdento em outras atividades

                long Y = 0;
                {// Parte 2
                    Y = ler("Y");
                    System.out.println(String.format("Nenhuma transação além da %1$d deveria ter lido X = %2$d porque operação será desfeita. (ATUALIZAÇÃO TEMPORÁRIA)", numero, X));
                    throw new SQLException("Erro no processamento");
                }
            }

            @Override
            public void desfazer() throws SQLException {
                desfazer("X", 95, 100);
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

                long X;
                int M = 8;
                {// Parte 1
                    X = ler("X");
                    System.out.println(String.format("Transação 2 faz x = %d - %d = %d", X, M, X - M));
                    X = X - M;
                    escrever("X", X);
                }

                processar(2000); // simulação carga de processamdento em outras atividades
            }
        };
        t.start();
        return t;
    }

    private static Transacao iniciarTransacaoT3(boolean controleTransacao) {
        Transacao t = new Transacao(3, controleTransacao) {
            @Override
            public void tarefa() throws SQLException, InterruptedException {
                ler("X");
            }
        };
        t.start();
        return t;
    }
}
