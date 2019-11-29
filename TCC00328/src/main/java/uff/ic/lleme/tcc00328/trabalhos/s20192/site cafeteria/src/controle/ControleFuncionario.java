package controle;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import persistencia.FuncionarioDao;
import entidade.Funcionario;

@WebServlet({ "/ControleFuncionario", "/cadastrar.html", "/buscar.html",
		"/excluir.html", "/editar.html", "/editar2.html", "/logar.html", "/deslogar.html"})
public class ControleFuncionario extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ControleFuncionario() {
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
			if (url.equalsIgnoreCase("/cadastrar.html")) {
				cadastrar(request,response);
			} else if (url.equalsIgnoreCase("/buscar.html")) {
				buscar(request,response);
			} else  if (url.equalsIgnoreCase("/excluir.html")) {
				excluir(request,response);
			}else  if (url.equalsIgnoreCase("/editar.html")) {
				editar(request,response);
			}else  if (url.equalsIgnoreCase("/editar2.html")) {
				editar2(request,response);
			}else  if (url.equalsIgnoreCase("/logar.html")) {
				logar(request,response);
			}else if (url.equalsIgnoreCase("/deslogar.html")){
				deslogar(request,response);
			}else{
				response.sendRedirect("/");
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
		
			
	}
	
	private void deslogar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
			HttpSession session = request.getSession();
			session.removeAttribute("identificacaofunc");
			session.removeAttribute("funcionarioAutenticado");
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
				
				Funcionario f = new Funcionario();
				f.setNome(nome);
				f.setEmail(email);
				f.setSenha(senha);
				
				// Enviar para o banco de dados
				new FuncionarioDao().cadastrar(f);
		
				request.setAttribute("msg","<div class='alert alert-success'>Funcionario Cadastrado!</div>");

			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("msg", "<div class='alert alert-danger'>Funcionario não Cadastrado!</div>");
			}finally{
				 request.getRequestDispatcher("cadastrar.jsp").forward(request, response);
			}
	}
	protected void buscar(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			String nome = request.getParameter("nome");
			request.setAttribute("nome", nome);
			
			FuncionarioDao fd = new FuncionarioDao();
			List<Funcionario> lista = fd.buscar(nome+"%");
			if (nome == "") {
				request.setAttribute("msg", "<div class='alert alert-info'>Por favor digite um nome</div>");
				lista = null;
			}else if(lista.size() == 0){
			request.setAttribute("msg", "<div class='alert alert-info'>Nenhum funcionario encontrado</div>");
		}
			request.setAttribute("lista", lista);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "<div class='alert alert-danger'>Erro na busca</div>");
		}finally{
			request.getRequestDispatcher("buscar.jsp").forward(request, response);
		}
	}
	
	protected void excluir(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			Integer id = new Integer(request.getParameter("id"));
			FuncionarioDao fd = new FuncionarioDao();
			Funcionario f = fd.buscarPorId(id);
			if (f == null) {
				request.setAttribute("msg", "<div class='alert alert-warning'>Funcionário não existe</div>");
			}else if (fd.excluir(id)) {
				if (fd.excluir2(id)) {
					request.setAttribute("msg", "<div class='alert alert-info'>Funcionário excluido</div>");
				}
			}else {
				request.setAttribute("msg", "<div class='alert alert-danger'>Funcionário não foi excluido</div>");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			request.getRequestDispatcher("buscar.jsp").forward(request, response);
		}
	}

	protected void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			Integer id = new Integer(request.getParameter("id"));
			FuncionarioDao fd = new FuncionarioDao();
			Funcionario f = fd.buscarPorId2(id);
			
			if(f == null){
				request.setAttribute("msg", "<div class='alert alert-warning'>Funcionario nao existente</div>");
				request.getRequestDispatcher("buscar.jsp").forward(request, response);
			}else{
				request.setAttribute("f", f);
				request.getRequestDispatcher("cadastrar.jsp").forward(request, response);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			request.getRequestDispatcher("buscar.jsp").forward(request, response);
		}
	}
	
	protected void editar2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			String nome = request.getParameter("nome");
			String email = request.getParameter("email");
			String senha = request.getParameter("senha");
			String id = request.getParameter("id");
			
			Funcionario f = new Funcionario();
			f.setNome(nome);
			f.setEmail(email);
			f.setSenha(senha);
			
			f.setId_funcionario(new Integer(id));
				
			FuncionarioDao fd = new FuncionarioDao();
			if(fd.editar(f)){
				request.setAttribute("msg", "<div class='alert alert-success'>Funcionario editado com sucesso</div>");
			}else{
				request.setAttribute("msg", "<div class='alert alert-danger'>Funcionario nao editado</div>");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			request.getRequestDispatcher("cadastrar.jsp").forward(request, response);
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
			
			FuncionarioDao fd = new FuncionarioDao();
			Funcionario func = fd.logar(email, senha);
			if(func != null){
				session.setAttribute("funcionarioAutenticado", func.getNome());
				session.setAttribute("identificacaofunc", func.getId_funcionario());
				request.setAttribute("funcionario", func.getNome());
				response.sendRedirect("blank.jsp");
			}else{
				request.setAttribute("msg", "<div class='alert alert-danger'>Email ou senha incorretos</div>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "<div class='alert alert-danger'>Erro ao logar</div>");
		}finally{
			request.getRequestDispatcher("blank.jsp").forward(request, response);
		}
	}
}

