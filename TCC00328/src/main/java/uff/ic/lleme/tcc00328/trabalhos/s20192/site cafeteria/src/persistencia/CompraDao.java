package persistencia;

import java.util.ArrayList;
import java.util.List;

import entidade.Compra;
import entidade.Pedido;

public class CompraDao extends Dao{
	public boolean cadastrar(Compra c) throws Exception {
		boolean success = false;
		try {
		open();
		stmt = conn.prepareStatement("insert into compra values(null,?,?,?,?)");
		stmt.setInt(1, c.getId_cliente());
		stmt.setString(2, c.getData_compra());
		stmt.setDouble(3, c.getValor());
		stmt.setInt(4, c.getId_pedido());
		stmt.execute();
		success = true;
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return success;
	}
	
	public List<Compra> buscarPorIdCliente(Integer id) throws Exception{
		List<Compra> lista  = new ArrayList<Compra>();
		try {
			open();
			stmt = conn.prepareStatement("select c.id_compra, c.id_cliente, c.data_compra, p.valor, p.id_pedido, p.id_produto, p.quantidade from compra c inner join pedido p on c.id_pedido = p.id_pedido where c.id_cliente = ?");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Compra c = new Compra();
				c.setId_compra(rs.getInt("id_compra"));
				c.setId_cliente(rs.getInt("id_cliente"));
				c.setData_compra(rs.getString("data_compra"));
				Pedido p = new Pedido(rs.getInt("id_pedido"),
									  rs.getInt("id_produto"),
									  rs.getInt("quantidade"),
									  rs.getDouble("valor"));
				c.setPedido(p);
				lista.add(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return lista;
	}
}
