package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Dao {
	Connection conn;
	PreparedStatement stmt;
	ResultSet rs;

	private final String URL = "jdbc:mysql://localhost:3306/cafe";
	private final String USER = "root";
	private final String PASS = "";

	public void open() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(URL, USER, PASS);
	}

	public void close() throws SQLException {
		conn.close();
	}

	public static void main(String[] args) {
		try {
			Dao d = new Dao();
			d.open();
			d.close();
			System.out.println("Conectei...");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Não conectei....");
		}
	}

}

