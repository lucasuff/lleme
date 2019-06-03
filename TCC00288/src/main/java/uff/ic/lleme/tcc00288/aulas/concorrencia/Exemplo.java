package uff.ic.lleme.tcc00288.aulas.concorrencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Savepoint;
import java.sql.Statement;

public class Exemplo {

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection("bd", "user", "pass")) {
            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            try (Statement st = conn.createStatement()) {// operações SQL>
                ResultSet rs = st.executeQuery("SQL");
            }

            try (Statement st = conn.createStatement()) {// operações SQL>
                st.executeUpdate("SQL");
            }

            try (Statement st = conn.createStatement()) {// operações SQL>
                PreparedStatement pstmt = conn.prepareStatement("SELECT func()");
                pstmt.execute(); // omit the result
            }

            conn.commit(); // fim da transação 1

            //
            // operações
            Savepoint savepoint = conn.setSavepoint();
            //
            conn.rollback(savepoint);
            // redo
            //
            conn.commit(); // fim da transação 2

            //
            // select func();
            //
            conn.rollback(); // fim anormal da transação 3

        } catch (Exception e) {
        }

    }
}
