package uff.ic.lleme.tcc00288.aulas.concorrencia;

import java.sql.SQLException;
import uff.ic.lleme.tcc00288.aulas.concorrencia.util.Config;
import uff.ic.lleme.tcc00288.aulas.concorrencia.util.Transacao;

public class AtualizacaoPerdida {

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
                    x = x - N;
                    System.out.println(String.format("Transacao 1 faz x = %d - %d = %d", x + N, N, x));
                }

                processar(2000);

                long y = 0;
                {// Parte 2
                    escreverX(x);
                    y = lerY("");
                }

                processar(2000);

                {// Parte 3
                    int N = 3;
                    y = y + N;
                    System.out.println(String.format("Transacao 1 faz y = %d + %d = %d", y - N, N, y));
                    escreverY(y);
                }

                {// Parte 4
                    System.out.println("");
                    x = lerX("");
                    System.out.println("Transacao 1 encerra.");
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
                    x = lerX("");
                    int N = 8;
                    x = x + N;
                    System.out.println(String.format("Transacao 2 faz x = %d + %d = %d", x - N, N, x));
                }

                processar(2000);

                {// Parte 2
                    escreverX(x);
                    System.out.println("Transacao 2 encerra.");
                    this.conn.commit();
                }
            }
        };
        t.start();
        return t;
    }
}
