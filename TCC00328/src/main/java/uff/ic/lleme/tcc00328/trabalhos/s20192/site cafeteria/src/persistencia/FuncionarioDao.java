package persistencia;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import entidade.Funcionario;

public class FuncionarioDao extends Dao {
	
	
	public boolean cadastrar(Funcionario f)throws Exception{
		boolean success = false;
		try {
			open();
			stmt = conn.prepareStatement("insert into funcionario values(null,?,?,?)",stmt.RETURN_GENERATED_KEYS);
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
	
	public List<Funcionario>buscar(String nome)throws Exception{		
		List<Funcionario> lista = new ArrayList<Funcionario>();
		try {
			open(); 												
			stmt = conn.prepareStatement("select F.id_funcionario,F.nome,F.email,F.senha,from funcionario F where nome like ?");
			stmt.setString(1, nome);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				Funcionario f = new Funcionario(
						rs.getInt("id_funcionario"),
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
	
	public Funcionario buscarPorId(int id)throws Exception{
			Funcionario f = null;
			
			try {
				open();
				stmt = conn.prepareStatement("select * from funcionario where id_funcionario = ?");
				stmt.setInt(1, id);
				
				rs = stmt.executeQuery();
				
				if(rs.next()){
					f = new Funcionario(rs.getInt("id_funcionario"),
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
	
	public Funcionario buscarPorId2(int id)throws Exception{
		Funcionario f = null;
		
		try {
			open();
			stmt = conn.prepareStatement("select F.id_funcionario,F.nome,F.email,F.senha, from funcionario F where F.id_funcionario = ?");
			stmt.setInt(1, id);
			
			rs = stmt.executeQuery();
			
			if(rs.next()){
				f = new Funcionario(
						rs.getInt("id_funcionario"),
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
			stmt = conn.prepareStatement("delete from funcionario where id_funcionario = ?");
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
	
	public boolean excluir2(int id) throws Exception{
		boolean success = false;
		try {
			open();
			stmt = conn.prepareStatement("delete from funcionario_endereco where id_funcionario = ?");
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
	public Funcionario logar(String email, String senha) throws Exception{
		Funcionario func = null;
		try {
			open();
			stmt = conn.prepareStatement("select * from funcionario where email = ? and senha = ?");
			stmt.setString(1, email);
			stmt.setString(2, senha);
			rs = stmt.executeQuery();
			if(rs.next()){
				func = new Funcionario(
						rs.getInt("id_funcionario"),
						rs.getString("nome"),
						rs.getString("email"),
						rs.getString("senha"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			close();
		}
		return func;
	}
	
	public boolean editar(Funcionario f)throws Exception{
		boolean success = false;
		try{
			open();
			stmt = conn.prepareStatement("update funcionario set funcionario.nome = ?, funcionario.email = ?, funcionario.senha = ? where funcionario.id_funcionario = ?");
			stmt.setString(1, f.getNome());
			stmt.setString(2, f.getEmail());
			stmt.setString(3, f.getSenha());
			stmt.setInt(4, f.getId_funcionario());
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

