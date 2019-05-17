package uff.ic.lleme.tcc00288.aulas.concorrencia;

import java.sql.SQLException;
import uff.ic.lleme.tcc00288.aulas.concorrencia.util.Config;
import uff.ic.lleme.tcc00288.aulas.concorrencia.util.Transacao;

public class AtualizacaoTemporaria {

    public static void main(String[] args) throws InterruptedException {
        boolean controleTransacao = true;
        Config.initBD();
        Transacao t1 = iniciarTransacaoT1(controleTransacao);
        Transacao t2 = iniciarTransacaoT2(controleTransacao);
        t1.join();
        t2.join();
    }

    private static Transacao iniciarTransacaoT1(boolean controleTransacao) {
        Transacao t = new Transacao(1, controleTransacao) {
            @Override
            public void tarefa() throws SQLException, InterruptedException {

                long x = 0;
                {// Parte 1
                    x = ler("X");
                    int N = 5;
                    System.out.println(String.format("Transação 1 faz x = %d - %d = %d", x, N, x - N));
                    x = x - N;
                    escrever("X", x);
                }

                processar(2000); // simulação carga de processamdento em outras atividades

                long y = 0;
                {// Parte 2
                    y = ler("Y");
                    throw new SQLException("Erro no processamento.");
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
                    x = ler("X");
                    M = 8;
                    System.out.println(String.format("Transação 2 faz x = %d - %d = %d", x, M, x - M));
                    x = x - M;
                    escrever("X", x);
                }

                processar(2000); // simulação carga de processamdento em outras atividades

                long novoX = ler("X");
                System.out.println(String.format("Transação 2 lê novamente x = %d que gravou anteriormente. <--------", novoX));
            }
        };
        t.start();
        return t;
    }
}
