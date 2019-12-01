package persistencia;

import java.util.ArrayList;
import java.util.List;

import entidade.Produto;

public class ProdutoDao extends Dao{
	
	public boolean cadastrar(Produto p)throws Exception{
		boolean success = false;
		try {
			open();
			stmt = conn.prepareStatement("insert into produto values(null,?,?,?,?,?,?)");
			stmt.setString(1, p.getNome());
			stmt.setString(2, p.getDescricao());
			stmt.setString(3, p.getCategoria());
			stmt.setDouble(4, p.getValor());
			stmt.setString(5, p.getImagem());
			stmt.setInt(6, p.getEstoque());
			
			stmt.execute();
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			close();
		}
		return success;
	}
	
	public List<Produto>buscar(String nome)throws Exception{		
		List<Produto> lista = new ArrayList<Produto>();
		try {
			open();
			stmt = conn.prepareStatement("select * from produto where nome like ?");
			stmt.setString(1, nome);
			rs = stmt.executeQuery();
			while(rs.next()){
				Produto p = new Produto(
						rs.getInt("id_produto"),
						rs.getString("nome"),
						rs.getString("descricao"),
						rs.getString("categoria"),
						rs.getDouble("valor"),
						rs.getString("imagem"),
						rs.getInt("estoque"));
				
				lista.add(p);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			close();
		}
		return lista;
	}
	
	public Produto buscarPorId(int id)throws Exception{
		Produto p = null;
	
			try {
				open();
				stmt = conn.prepareStatement("select * from produto where id_produto = ?");
				stmt.setInt(1, id);
				
				rs = stmt.executeQuery();
				
				if(rs.next()){
					p = new Produto(rs.getInt("id_produto"),
							rs.getString("nome"),
							rs.getString("descricao"),
							rs.getString("categoria"),
							rs.getDouble("valor"),
							rs.getString("imagem"),
							rs.getInt("estoque"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				close();
			}
			return p;
	}
	
	public boolean excluir(int id) throws Exception{
		boolean success = false;
		try {
			open();
			stmt = conn.prepareStatement("delete from produto where id_produto = ?");
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
	
	public boolean editar(Produto p)throws Exception{
		boolean success = false;
		try{
			open();
			stmt = conn.prepareStatement("update produto set nome = ?,  descricao = ?, categoria = ?, valor = ?, imagem = ?, estoque = ? where id_produto = ?");
			stmt.setString(1, p.getNome());
			stmt.setString(2, p.getDescricao());
			stmt.setString(3, p.getCategoria());
			stmt.setDouble(4, p.getValor());
			stmt.setString(5, p.getImagem());
			stmt.setInt(6, p.getEstoque());
			
			
			stmt.setInt(7, p.getId_produto());

			stmt.execute();
			success = true;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return success;
	} 
	
	
	public List<Produto> listarTodos() throws Exception{
		List<Produto> lista = new ArrayList<Produto>();
		try{
	        open();
	        stmt = conn.prepareStatement("select * from produto");
	        rs = stmt.executeQuery();
	        while(rs.next()){
	            Produto p = new Produto();
	            p.setId_produto(rs.getInt("id_produto"));
	            p.setNome(rs.getString("nome"));
	            p.setDescricao(rs.getString("descricao"));
	            p.setCategoria(rs.getString("categoria"));
	            p.setValor(rs.getDouble("valor"));
	            p.setImagem(rs.getString("imagem"));
	            p.setEstoque(rs.getInt("estoque"));
   
	            if(p.getEstoque() > 0) {
					lista.add(p);
				}            
	        }
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
		close();
		}
		return lista;
	}
	
	
	public List<Produto> detalhe(int id)throws Exception{		
		List<Produto> lista = new ArrayList<Produto>();
		try {
			open();
			stmt = conn.prepareStatement("select * from produto where id_produto = ?");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			while(rs.next()){
				Produto p = new Produto(
						rs.getInt("id_produto"),
						rs.getString("nome"),
						rs.getString("descricao"),
						rs.getString("categoria"),
						rs.getDouble("valor"),
						rs.getString("imagem"),
						rs.getInt("estoque"));

				if(p.getEstoque() > 0) {
					lista.add(p);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			close();
		}
		return lista;
	}

	public List<Produto>combos()throws Exception{		
		List<Produto> lista = new ArrayList<Produto>();
		try {
			open();
			stmt = conn.prepareStatement("select * from produto where categoria = 'combo'");
			rs = stmt.executeQuery();
			while(rs.next()){
				Produto p = new Produto(
						rs.getInt("id_produto"),
						rs.getString("nome"),
						rs.getString("descricao"),
						rs.getString("categoria"),
						rs.getDouble("valor"),
						rs.getString("imagem"),
						rs.getInt("estoque"));

				if(p.getEstoque() > 0) {
					lista.add(p);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			close();
		}
		return lista;
	}
	
	public List<Produto>cafeechas()throws Exception{		
		List<Produto> lista = new ArrayList<Produto>();
		try {
			open();
			stmt = conn.prepareStatement("select * from produto where categoria = 'cafe'");
			rs = stmt.executeQuery();
			while(rs.next()){
				Produto p = new Produto(
						rs.getInt("id_produto"),
						rs.getString("nome"),
						rs.getString("descricao"),
						rs.getString("categoria"),
						rs.getDouble("valor"),
						rs.getString("imagem"),
						rs.getInt("estoque"));

				if(p.getEstoque() > 0) {
					lista.add(p);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			close();
		}
		return lista;
	}
	
	public List<Produto>paes()throws Exception{		
		List<Produto> lista = new ArrayList<Produto>();
		try {
			open();
			stmt = conn.prepareStatement("select * from produto where categoria = 'paes'");
			rs = stmt.executeQuery();
			while(rs.next()){
				Produto p = new Produto(
						rs.getInt("id_produto"),
						rs.getString("nome"),
						rs.getString("descricao"),
						rs.getString("categoria"),
						rs.getDouble("valor"),
						rs.getString("imagem"),
						rs.getInt("estoque"));

				if(p.getEstoque() > 0) {
					lista.add(p);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			close();
		}
		return lista;
	}
	
	
	public List<Produto>bebidas()throws Exception{		
		List<Produto> lista = new ArrayList<Produto>();
		try {
			open();
			stmt = conn.prepareStatement("select * from produto where categoria = 'bebidas'");
			rs = stmt.executeQuery();
			while(rs.next()){
				Produto p = new Produto(
						rs.getInt("id_produto"),
						rs.getString("nome"),
						rs.getString("descricao"),
						rs.getString("categoria"),
						rs.getDouble("valor"),
						rs.getString("imagem"),
						rs.getInt("estoque"));

				if(p.getEstoque() > 0) {
					lista.add(p);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			close();
		}
		return lista;
	}
}


