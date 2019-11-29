package persistencia;

import java.util.ArrayList;
import java.util.List;

import entidade.Cliente;

public class ClienteDao extends Dao{
	public boolean cadastrar(Cliente f)throws Exception{
		boolean success = false;
		try {
			open();
			stmt = conn.prepareStatement("insert into cliente values(null,?,?,?)",stmt.RETURN_GENERATED_KEYS);
			stmt.setString(1, f.getNome());
			stmt.setString(2, f.getEmail());
			stmt.setString(3, f.getSenha());
			
			stmt.execute();
			stmt.close();
			
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			close();
		}
		return success;
	}
	
	public List<Cliente>buscar(String nome)throws Exception{		
		List<Cliente> lista = new ArrayList<Cliente>();
		try {
			open(); 																																												
			stmt = conn.prepareStatement("select id_cliente,nome,email,senha from cliente where nome like ?");
			stmt.setString(1, nome);
			rs = stmt.executeQuery();
			while(rs.next()){
				Cliente f = new Cliente(
						rs.getInt("id_cliente"),
						rs.getString("nome"),
						rs.getString("email"),
						rs.getString("senha"));
			
				lista.add(f);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			close();
		}
		return lista;
	}
	
	public Cliente buscarPorId(int id)throws Exception{
			Cliente f = null;
			
			try {
				open();
				stmt = conn.prepareStatement("select * from cliente where id_cliente = ?");
				stmt.setInt(1, id);
				rs = stmt.executeQuery();
				if(rs.next()){
					f = new Cliente(
							rs.getInt("id_cliente"),
							rs.getString("nome"),
							rs.getString("email"),
							rs.getString("senha"));
				}	
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				close();
			}
			return f;
	}
	
	public boolean excluir(int id) throws Exception{
		boolean success = false;
		try {
			open();
			
			stmt = conn.prepareStatement("delete from cliente where id_Cliente = ?");
			stmt.setInt(1, id);
			
			stmt.execute();
			
			success = true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return success;
	}
	/*------------------------------------------------------------------------ */
	public Cliente logar(String email, String senha) throws Exception{
		Cliente cli = null;
		try {
			open();
			stmt = conn.prepareStatement("select * from cliente where email = ? and senha = ?");
			stmt.setString(1, email);
			stmt.setString(2, senha);
			rs = stmt.executeQuery();
			if(rs.next()){
				cli = new Cliente(
						rs.getInt("id_cliente"),
						rs.getString("nome"),
						rs.getString("email"),
						rs.getString("senha"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			close();
		}
		return cli;
	}
	
	public boolean editar(Cliente f)throws Exception{
		boolean success = false;
		try{
			open();
			stmt = conn.prepareStatement("update cliente set cliente.nome = ?, cliente.email = ?, cliente.senha = ? where cliente.id_cliente = ?");
			stmt.setString(1, f.getNome());
			stmt.setString(2, f.getEmail());
			stmt.setString(3, f.getSenha());
			stmt.execute();
			success = true;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return success;
	}
}
