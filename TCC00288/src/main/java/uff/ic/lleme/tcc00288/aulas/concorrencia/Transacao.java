package uff.ic.lleme.tcc00288.aulas.concorrencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class Transacao extends Thread {

    public final int numero;
    public final boolean controleTransacao;
    private Connection conn = null;

    protected Transacao(int numero, boolean controleTransacao) {
        this.numero = numero;
        this.controleTransacao = controleTransacao;
    }

    @Override
    public void run() {
        try {
            Class.forName("org.postgresql.Driver");
            try ( Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TCC00288", "postgres", "fluminense");) {

                this.conn = conn;
                setControleTransacao(controleTransacao);

                try {

                    tarefa();
                    commit();

                } catch (Exception e) {
                    System.out.println(String.format("%1$s na transação %2$d.", e.getMessage(), numero));
                    try {
                        rollback();
                    } catch (SQLException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public abstract void tarefa() throws SQLException, InterruptedException;

    public void desfazer() throws SQLException {
    }

    private void setControleTransacao(boolean controleTransacao) throws SQLException {
        conn.setAutoCommit(!controleTransacao);
        if (controleTransacao)
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        else
            conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
    }

    public void rollback() throws SQLException {
        if (!conn.getAutoCommit()) {
            conn.rollback();
            System.out.println(String.format("Transação %1$d detecta erro, dezfaz alterações e encerra.", numero));
        } else {
            desfazer();
            System.out.println(String.format("Transação %1$d termina.", numero));
        }
    }

    public void commit() throws SQLException {
        if (!conn.getAutoCommit()) {
            conn.commit();
            System.out.println(String.format("Transação %1$d encerra sem erros.", numero));
        } else
            System.out.println(String.format("Transação %1$d termina.", numero));
    }

    public long ler(String item) throws SQLException {
        try ( Statement st = conn.createStatement();) {
            long x = 0;
            ResultSet rs1 = st.executeQuery(String.format("select valor from tabela where chave = '%s';", item));
            if (rs1.next())
                x = rs1.getLong("valor");
            System.out.println(String.format("Transação %1$d lê %2$s = %3$d", numero, item, x));
            return x;
        }
    }

    public void escrever(String item, long valor) throws SQLException {
        try ( Statement st = conn.createStatement();) {
            st.executeUpdate(String.format("update tabela set valor=%2$d where chave = '%1$s';", item, valor));
            System.out.println(String.format("Transação %1$d grava %2$s = %3$d", numero, item, valor));
        }
    }

    public void desfazer(String item, long valorNovo, long valorAntigo) throws SQLException {
        try ( Statement st = conn.createStatement();) {
            st.executeUpdate(String.format("update tabela set valor=%2$d where chave = '%1$s';", item, valorAntigo));
            System.out.println(String.format("Transação %1$d desgrava %2$s = %3$d", numero, item, valorNovo));
        }
    }

    protected void processar(long t) throws InterruptedException {
        System.out.println(String.format("Transação %d em processamento...", numero));
        Thread.sleep(t);
    }
}
