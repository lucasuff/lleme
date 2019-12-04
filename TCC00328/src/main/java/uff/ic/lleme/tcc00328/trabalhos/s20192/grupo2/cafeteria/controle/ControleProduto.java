package controle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entidade.Produto;
import persistencia.ProdutoDao;


@WebServlet({ "/ControleProduto", "/cadastrarpro.html", "/buscarpro.html","/buscarpro2.html","/buscarpro3.html",
	"/excluirpro.html", "/editarpro.html", "/editarpro2.html", "/detalhes.html" })
public class ControleProduto extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public ControleProduto() {
        super();
    }
    
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		execute(request,response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		execute(request,response);
	}

	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			String url = request.getServletPath();
			if (url.equalsIgnoreCase("/cadastrarpro.html")) {
				cadastrarpro(request,response);
			} else if (url.equalsIgnoreCase("/buscarpro.html")) {
				buscarpro(request,response);
			} else if(url.equalsIgnoreCase("/buscarpro2.html")) {
				buscarpro2(request,response);
			}else if (url.equalsIgnoreCase("/excluirpro.html")) {
				excluirpro(request,response);
			}else  if (url.equalsIgnoreCase("/editarpro.html")) {
				editarpro(request,response);
			}else  if (url.equalsIgnoreCase("/editarpro2.html")) {
				editarpro2(request,response);
			}else  if (url.equalsIgnoreCase("/detalhes.html")) {
				detalhes(request,response);
			}else{
				response.sendRedirect("/");
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	

	protected void cadastrarpro(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
			try {
				String nome = request.getParameter("nome");
				String descricao = request.getParameter("descricao");
				String categoria = request.getParameter("categoria");
				String valor = request.getParameter("valor");
				String imagem = request.getParameter("imagem");
				String estoque = request.getParameter("estoque");
							
				Produto p = new Produto();
				
				valor = valor.replace(',', '.');
				
				p.setNome(nome);
				p.setDescricao(descricao);
				p.setCategoria(categoria);
				p.setValor(new Double(valor));
				p.setImagem(imagem);
				p.setEstoque((int) new Integer(estoque));
			
				new ProdutoDao().cadastrar(p);
				
				request.setAttribute("msg","<div class='alert alert-success'>Produto Cadastrado!</div>");
				
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("msg", "<div class='alert alert-danger'>Produto não Cadastrado!</div>");
			}finally{
				 request.getRequestDispatcher("cadastrarpro.jsp").forward(request, response);
			}
	}
	
	protected void buscarpro(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			String nome = request.getParameter("nome");
			request.setAttribute("nome", nome);
			
			ProdutoDao pd = new ProdutoDao();
			List<Produto> lista = pd.buscar(nome+"%");
			if (nome == "") {
				request.setAttribute("msg", "<div class='alert alert-info'>Por favor digite um nome</div>");
				lista = null;
			}else if(lista.size() == 0){
				request.setAttribute("msg", "<div class='alert alert-info'>Nenhum produto encontrado</div>");
			}
			request.setAttribute("lista", lista);
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "<div class='alert alert-danger'>Erro na busca</div>");
		}finally{
			request.getRequestDispatcher("buscarpro.jsp").forward(request, response);
		}
	}
	
	protected void buscarpro2(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			String nome = request.getParameter("nome");
			request.setAttribute("nome", nome);
			
			ProdutoDao pd = new ProdutoDao();
			List<Produto> lista = pd.buscar(nome+"%");
			if (nome == "") {
				request.setAttribute("msg", "<div class='alert alert-info'>Por favor digite um nome</div>");
				lista = null;
			}else if(lista.size() == 0){
				request.setAttribute("msg", "<div class='alert alert-info'>Nenhum produto encontrado</div>");
			}
			request.setAttribute("lista", lista);
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "<div class='alert alert-danger'>Erro na busca</div>");
		}finally{
			request.getRequestDispatcher("pesquisa.jsp").forward(request, response);
		}
	}
	protected void excluirpro(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			Integer id = new Integer(request.getParameter("id"));
			ProdutoDao pd = new ProdutoDao();
			Produto p = pd.buscarPorId(id);
			if (p == null) {
				request.setAttribute("msg", "<div class='alert alert-warning'>Produto não existe</div>");
			}else if (pd.excluir(id)) {
				request.setAttribute("msg", "<div class='alert alert-info'>Produto excluido</div>");
			}else {
				request.setAttribute("msg", "<div class='alert alert-danger'>Produto não foi excluido</div>");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			request.getRequestDispatcher("buscarpro.jsp").forward(request, response);
		}
	}

	protected void editarpro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			
			Integer id = new Integer(request.getParameter("id"));
			ProdutoDao pd = new ProdutoDao();
			Produto p = pd.buscarPorId(id);
			
			if(p == null){
				request.setAttribute("msg", "<div class='alert alert-warning'>Produto nao existente</div>");
				request.getRequestDispatcher("buscar.jsp").forward(request, response);
			}else{
				request.setAttribute("p", p);
				request.getRequestDispatcher("cadastrarpro.jsp").forward(request, response);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			request.getRequestDispatcher("buscarpro.jsp").forward(request, response);
		}
	}
	
	protected void editarpro2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			
			String nome = request.getParameter("nome");
			String descricao = request.getParameter("descricao");
			String categoria = request.getParameter("categoria");
			String valor = request.getParameter("valor");
			String imagem = request.getParameter("imagem");	
			String estoque = request.getParameter("estoque");	
			
			String id = request.getParameter("id");
			
			Produto p = new Produto();
			
			valor = valor.replace(',', '.');
			
			p.setNome(nome);
			p.setDescricao(descricao);
			p.setCategoria(categoria);
			p.setValor(new Double(valor));
			p.setImagem(imagem);		
			p.setEstoque((int) new Integer(estoque));
			
			p.setId_produto(new Integer(id));
	
			ProdutoDao pd = new ProdutoDao();
			
			if(pd.editar(p)){
				request.setAttribute("msg", "<div class='alert alert-success'>Produto editado com sucesso</div>");
			}else{
				request.setAttribute("msg", "<div class='alert alert-danger'>Produto nao editado</div>");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			request.getRequestDispatcher("cadastrarpro.jsp").forward(request, response);
		}
	}
	
	/*-----------------------------------------------------------------------------------*/
	
	protected void detalhes(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			List<Produto> lista = new ArrayList<Produto>();
		
			Integer id = new Integer(request.getParameter("id"));
			ProdutoDao pd = new ProdutoDao();
			Produto p = pd.buscarPorId(id);
			p.setId_produto(p.getId_produto());
	        p.setNome(p.getNome());     
	        p.setDescricao(p.getDescricao());
	        p.setCategoria(p.getCategoria());
	        p.setValor(p.getValor());
	        p.setImagem(p.getImagem());    
	        p.setEstoque(p.getEstoque());  
	        
	        lista.add(p);
        
		request.setAttribute("listagem", lista);
		request.getRequestDispatcher("detalhes.jsp").forward(request, response);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			request.getRequestDispatcher("buscarpro.jsp").forward(request, response);
		}
	}
}