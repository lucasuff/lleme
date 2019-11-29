package persistencia;

import java.util.ArrayList;
import java.util.List;

import entidade.Pedido;

public class PedidoDao extends Dao {
	public Integer cadastrar(Pedido p) throws Exception {
		Integer id = null;
		try {
			open();
			stmt = conn.prepareStatement("insert into pedido values(null,?,?,?)", stmt.RETURN_GENERATED_KEYS);
			stmt.setDouble(1, p.getValor());
			stmt.setInt(2, p.getId_produto());
			stmt.setInt(3, p.getQuantidade());
			stmt.execute();
			
			rs = stmt.getGeneratedKeys();
			if(rs.next()) {
				id = rs.getInt(1);
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			close();
		}
		return id;
	}

}
