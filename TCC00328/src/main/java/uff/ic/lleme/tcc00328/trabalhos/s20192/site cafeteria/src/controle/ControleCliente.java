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

import entidade.Cliente;
import entidade.Compra;
import persistencia.ClienteDao;
import persistencia.CompraDao;

@WebServlet({ "/ControleCliente", "/cadastrarcli.html", "/buscarcli.html",
	"/excluircli.html", "/editarcli.html", "/editar2cli.html", "/logarcli.html","/deslogarcli.html","/buscarcomp.html"})
public class ControleCliente extends HttpServlet {
private static final long serialVersionUID = 1L;

public ControleCliente() {
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
		if (url.equalsIgnoreCase("/cadastrarcli.html")) {
			cadastrar(request,response);
		} else if (url.equalsIgnoreCase("/buscarcli.html")) {
			buscar(request,response);
		} else  if (url.equalsIgnoreCase("/excluircli.html")) {
			excluir(request,response);
		}else  if (url.equalsIgnoreCase("/editarcli.html")) {
			editar(request,response);
		}else  if (url.equalsIgnoreCase("/editar2cli.html")) {
			editar2(request,response);
		}else  if (url.equalsIgnoreCase("/logarcli.html")) {
			logar(request,response);
		}else if(url.equalsIgnoreCase("/deslogarcli.html")){
			deslogar(request,response);
		}else if(url.equalsIgnoreCase("/buscarcomp.html")){
			buscarcomp(request,response);
		}else{
			response.sendRedirect("/");
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
}
private void buscarcomp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	CompraDao cd = new CompraDao();
	List<Compra> lista= new ArrayList<Compra>();
	try {
		Integer id = new Integer(request.getParameter("id"));
		lista = cd.buscarPorIdCliente(id);
		request.setAttribute("lista",lista);
	} catch (Exception e) {
		e.printStackTrace();
	}finally {
		request.getRequestDispatcher("buscarcomp.jsp").forward(request, response);
	}
}

private void deslogar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
	try {
		HttpSession session = request.getSession();
		session.removeAttribute("identificacao");
		session.removeAttribute("usuarioAutenticado");
	} catch (Exception e) {
		e.printStackTrace();
		request.setAttribute("msg", "<div class='alert alert-danger'>Erro ao deslogar</div>");
	}finally{
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}
}

protected void cadastrar(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		try {
			String nome = request.getParameter("nome");
			String email = request.getParameter("email");
			String senha = request.getParameter("senha");
			
			Cliente c = new Cliente();
			c.setNome(nome);
			c.setEmail(email);
			c.setSenha(senha);
			
			// Enviar para o banco de dados
			new ClienteDao().cadastrar(c);
	
			request.setAttribute("msg","<div class='alert alert-success'>Cliente Cadastrado!</div>");

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "<div class='alert alert-danger'>Cliente não Cadastrado!</div>");
		}finally{
			 request.getRequestDispatcher("cadastrarcli.jsp").forward(request, response);
		}
}
protected void buscar(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
	try {
		String nome = request.getParameter("nome");
		request.setAttribute("nome", nome);
		
		ClienteDao cd = new ClienteDao();
		List<Cliente> lista = cd.buscar(nome+"%");
		if (nome == "") {
			request.setAttribute("msg", "<div class='alert alert-info'>Por favor digite um nome</div>");
			lista = null;
		}else if(lista.size() == 0){
		request.setAttribute("msg", "<div class='alert alert-info'>Nenhum Cliente encontrado</div>");
	}
		request.setAttribute("lista", lista);
		
		
	} catch (Exception e) {
		e.printStackTrace();
		request.setAttribute("msg", "<div class='alert alert-danger'>Erro na busca</div>");
	}finally{
		request.getRequestDispatcher("buscarcli.jsp").forward(request, response);
	}
}

protected void excluir(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
	try {
		Integer id = new Integer(request.getParameter("id"));
		ClienteDao cd = new ClienteDao();
		Cliente c = cd.buscarPorId(id);
		if (c == null) {
			request.setAttribute("msg", "<div class='alert alert-warning'>Cliente não existe</div>");
		}else if (cd.excluir(id)) {
			request.setAttribute("msg", "<div class='alert alert-info'>Cliente excluido</div>");
		}else {
			request.setAttribute("msg", "<div class='alert alert-danger'>Cliente não foi excluido</div>");
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	}finally {
		request.getRequestDispatcher("buscarcli.jsp").forward(request, response);
	}
}

protected void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	try{
		
		Integer id = new Integer(request.getParameter("id"));
		ClienteDao cd = new ClienteDao();
		Cliente c = cd.buscarPorId(id);
		if(c == null){
			request.setAttribute("msg", "<div class='alert alert-warning'>Cliente nao existente</div>");
			request.getRequestDispatcher("cadastrarcli.jsp").forward(request, response);
		}else{
			request.setAttribute("c", c);
			request.getRequestDispatcher("cadastrarcli.jsp").forward(request, response);
		}
		
	}catch(Exception e){
		e.printStackTrace();
		request.getRequestDispatcher("cadastrarcli.jsp").forward(request, response);
	}
}

protected void editar2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	try{
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		String senha = request.getParameter("senha");
		String id = request.getParameter("id");
		
		Cliente c = new Cliente();
		
		c.setNome(nome);
		c.setEmail(email);
		c.setSenha(senha);
		c.setId_cliente(new Integer(id));
			
		ClienteDao cd = new ClienteDao();
		if(cd.editar(c)){
			request.setAttribute("msg", "<div class='alert alert-success'>Cliente editado com sucesso</div>");
		}else{
			request.setAttribute("msg", "<div class='alert alert-danger'>Cliente nao editado</div>");
		}
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		request.getRequestDispatcher("cadastrarcli.jsp").forward(request, response);
	}
}


protected void logar(HttpServletRequest request, 
		HttpServletResponse response) throws ServletException, IOException {
	try {
		HttpSession session = request.getSession();
		String email = request.getParameter("email");
		request.setAttribute("email", email);
		String senha = request.getParameter("senha");
		request.setAttribute("senha", senha);
		
		ClienteDao cd = new ClienteDao();
		Cliente cliente = cd.logar(email, senha);
		if(cliente == null){
			request.setAttribute("msg", "<div class='alert alert-danger'>Email ou senha incorretos</div>");
		}else{
			session.setAttribute("usuarioAutenticado", cliente.getNome());
			session.setAttribute("identificacao", cliente.getId_cliente());
			request.setAttribute("usuario", cliente.getNome());
			request.setAttribute("msg", "<div class='alert alert-success'>Cliente logado</div>");
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
		
	} catch (Exception e) {
		e.printStackTrace();
		request.setAttribute("msg", "<div class='alert alert-danger'>Erro ao logar</div>");
	}finally{
		request.getRequestDispatcher("logarcli.jsp").forward(request, response);
	}
}
}