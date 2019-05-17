package uff.ic.lleme.tcc00288.aulas.concorrencia.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class Transacao extends Thread {

    protected int numero = 0;
    protected boolean controleTransacao = true;
    protected Connection conn = null;

    protected Transacao(int numero, boolean controleTransacao) {
        this.numero = numero;
        this.controleTransacao = controleTransacao;
    }

    @Override
    public void run() {
        try {
            Class.forName("org.postgresql.Driver");
            try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TCC00288", "postgres", "fluminense");) {

                this.conn = conn;
                setControleTransacao(controleTransacao);

                try {

                    tarefa();

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    try {
                        conn.rollback();
                    } catch (SQLException ex) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public abstract void tarefa() throws SQLException, InterruptedException;

    protected void setControleTransacao(boolean controleTransacao) throws SQLException {
        conn.setAutoCommit(!controleTransacao);
    }

    protected void rollback() throws SQLException {
        conn.rollback();
    }

    protected long lerX(String bloqueio) throws SQLException {
        try (Statement st = conn.createStatement();) {
            long x = 0;
            ResultSet rs1 = st.executeQuery(String.format("select valor from tabela where chave = 'x' %s;", bloqueio));
            if (rs1.next())
                x = rs1.getLong("valor");
            System.out.println(String.format("Transacao %d le x = %d", numero, x));
            return x;
        }
    }

    protected long lerY(String bloqueio) throws SQLException {
        try (Statement st = conn.createStatement();) {
            long y = 0;
            ResultSet rs2 = st.executeQuery(String.format("select valor from tabela where chave = 'y' %s;", bloqueio));
            if (rs2.next())
                y = rs2.getLong("valor");
            System.out.println(String.format("Transacao %d le y = %d", numero, y));
            return y;
        }
    }

    protected void desfazX(long x) throws SQLException {
        try (Statement st = conn.createStatement();) {
            st.executeUpdate(String.format("update tabela set valor=%d where chave = %s;", x, "'x'"));
            System.out.println(String.format("Transacao %d falha e desfaz a atualizacao de x = %d  ***", numero, x));
        }
    }

    protected long lerXNovamente() throws SQLException {
        try (Statement st = conn.createStatement();) {
            long novox = 0;
            ResultSet rs1 = st.executeQuery("select valor from tabela where chave = 'x';");
            if (rs1.next())
                novox = rs1.getLong("valor");
            return novox;
        }
    }

    protected void escreverX(long x) throws SQLException {
        try (Statement st = conn.createStatement();) {
            st.executeUpdate(String.format("update tabela set valor=%d where chave = %s;", x, "'x'"));
            System.out.println(String.format("Transacao %d salva x = %d", numero, x));
        }
    }

    protected void escreverY(long y) throws SQLException {
        try (Statement st = conn.createStatement();) {
            st.executeUpdate(String.format("update tabela set valor=%d where chave = %s;", y, "'y'"));
            System.out.println(String.format("Transacao %d salva y = %d", numero, y));
        }
    }

    protected void processar(long t) throws InterruptedException {
        System.out.println(String.format("Transacao %d em processamento...", numero));
        Thread.sleep(t);
    }
}
