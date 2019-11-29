package controle;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entidade.Compra;
import entidade.Pedido;
import entidade.Produto;
import persistencia.CompraDao;
import persistencia.PedidoDao;
import persistencia.ProdutoDao;

/**
 * Servlet implementation class ControleCarrinho
 */
@WebServlet("/ControleCarrinho")
public class ControleCarrinho extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ControleCarrinho() {
		super();
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			List<Produto> produtos = new ProdutoDao().listarTodos();
			request.setAttribute("produtos", produtos);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		if (!response.isCommitted()) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("carrinho.jsp");
			dispatcher.forward(request, response);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Na variavel evento enviamos a ação a ser
		// realizada(adicionar,atualizar,limpar,comprar)
		String evento = request.getParameter("evento");
		if (evento != null) {
			request.getSession().setAttribute("msg", "");
			if (evento.equals("adicionar")) {
				if (request.getSession().getAttribute("carrinho") == null) {
					HashMap<Integer, Pedido> carrinho = new HashMap<Integer, Pedido>();
					request.getSession().setAttribute("carrinho", carrinho);
				}
				@SuppressWarnings("unchecked")
				HashMap<Integer, Pedido> carrinho = (HashMap<Integer, Pedido>) request.getSession()
						.getAttribute("carrinho");
				// A variavel produto pega o id do produto a ser utilizado
				Integer produto = Integer.parseInt(request.getParameter("produto"));
				Pedido pedido = carrinho.get(produto);
				if (pedido == null) {
					try {
						Pedido ped = new Pedido();
						Produto p = new ProdutoDao().buscarPorId(produto);
						if(p.getEstoque() > 0){
							ped.setProduto(p);
							ped.setQuantidade(1);
							ped.setValor(p.getValor());
						}else {
							request.setAttribute("msg", "<div class='alert alert-danger'>Não há quantia suficiente no estoque para esse produto!</div>");
						}
						pedido = ped;
					} catch (Exception e) {

					}
				} else {
					if(pedido.getProduto().getEstoque() > pedido.getQuantidade()) {
						pedido.setQuantidade(pedido.getQuantidade() + 1);
						pedido.setValor(pedido.getValor() + pedido.getProduto().getValor());					
					}else {
						request.setAttribute("msg", "<div class='alert alert-danger'>Não há quantia suficiente no estoque para esse produto!</div>");
					}
				}
				// Removemos parar atualizar a sessão do carrinho
				carrinho.remove(produto);
				carrinho.put(produto, pedido);
				request.getSession().setAttribute("carrinho", carrinho);

			} else if (evento.equals("mais")) {
				// Esse evento serve para adicionarmos mais 1 a quantidade do produto no
				// carrinho
				@SuppressWarnings("unchecked")
				HashMap<Integer, Pedido> carrinho = (HashMap<Integer, Pedido>) request.getSession()
						.getAttribute("carrinho");
				if (carrinho != null) {
					Integer produto = Integer.parseInt(request.getParameter("produto"));
					Pedido pedido = carrinho.get(produto);
					if (pedido != null) {
						Integer qtd = pedido.getQuantidade();
						if(pedido.getProduto().getEstoque() > pedido.getQuantidade()) {
							pedido.setQuantidade(qtd + 1);
						}else {
							request.setAttribute("msg", "<div class='alert alert-danger'>Não há quantia suficiente no estoque para esse produto!</div>");
						}
						pedido.setValor(pedido.getQuantidade() * pedido.getProduto().getValor());
						carrinho.remove(produto);
						if (pedido.getQuantidade() > 0) {
							carrinho.put(produto, pedido);
						} else {
							request.getSession().setAttribute("total", 0.0);
							request.getSession().setAttribute("quantidade", 0);
						}
						request.getSession().setAttribute("carrinho", carrinho);

					}
				}

			} else if (evento.equals("menos")) {
				// Esse evento serve para subtrair 1 a quantidade do produto no carrinho
				@SuppressWarnings("unchecked")
				HashMap<Integer, Pedido> carrinho = (HashMap<Integer, Pedido>) request.getSession()
						.getAttribute("carrinho");
				if (carrinho != null) {
					Integer produto = Integer.parseInt(request.getParameter("produto"));
					Pedido pedido = carrinho.get(produto);
					if (pedido != null) {
						Integer qtd = pedido.getQuantidade();
						pedido.setQuantidade(qtd - 1);
						pedido.setValor(pedido.getQuantidade() * pedido.getProduto().getValor());
						carrinho.remove(produto);
						if (pedido.getQuantidade() > 0) {
							carrinho.put(produto, pedido);
						} else {
							request.getSession().setAttribute("total", 0.0);
							request.getSession().setAttribute("quantidade", 0);
						}
						request.getSession().setAttribute("carrinho", carrinho);

					}
				}
			} else if (evento.equals("limpar")) {
				// Aqui excluimos um produto do carrinho
				@SuppressWarnings("unchecked")
				HashMap<Integer, Pedido> carrinho = (HashMap<Integer, Pedido>) request.getSession()
						.getAttribute("carrinho");
				if (carrinho != null) {
					Integer produto = Integer.parseInt(request.getParameter("produto"));
					carrinho.remove(produto);
					request.getSession().setAttribute("carrinho", carrinho);
					request.getSession().setAttribute("total", 0.0f);
					request.getSession().setAttribute("quantidade", 0);
				}
			}
		}

		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String evento = request.getParameter("evento");
		if (evento != null) {
			if (evento.equals("compra")) {
				// Nesse evento finalizamos a compra
				HttpSession session = request.getSession();
				String ids[] = request.getParameterValues("id");
				String qtd[] = request.getParameterValues("qtd");
				Integer identificacao = (Integer) session.getAttribute("identificacao");
				String usuarioAutenticado = (String) session.getAttribute("usuarioAutenticado");
				if(ids != null) {
					String total = (String) session.getAttribute("total");
					total = total.replace(',', '.');
					Double valor = Double.parseDouble(total);
					Date data = new Date(System.currentTimeMillis());
					SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");  
					if (usuarioAutenticado != null) {
						List<Pedido> dados = new ArrayList<Pedido>();
						try {
							for (int i = 0; i < ids.length; i++) {
								Produto p = new ProdutoDao().buscarPorId(Integer.parseInt(ids[i]));
								Pedido pedido = new Pedido();
								pedido.setQuantidade(Integer.parseInt(qtd[i]));
								pedido.setId_produto(Integer.parseInt(ids[i]));
								pedido.setValor(p.getValor() * Integer.parseInt(qtd[i]));
								dados.add(pedido);
								p.setEstoque(p.getEstoque() - pedido.getQuantidade());
								new ProdutoDao().editar(p);
							}
							for (Pedido p : dados) {
								CompraDao cd = new CompraDao();
								Compra compra = new Compra();
								compra.setData_compra(fmt.format(data));
								compra.setValor(valor);
								compra.setId_cliente(identificacao);
								compra.setId_pedido(new PedidoDao().cadastrar(p));
								cd.cadastrar(compra);
							}
							session.removeAttribute("carrinho");
							session.removeAttribute("total");
							session.removeAttribute("quantidade");
							request.setAttribute("msg", "<div class='alert alert-success'>Compra efetuada com sucesso!</div>");
						} catch (Exception e) {
							e.printStackTrace();
							System.out.println(e.getMessage());
						}
					} else {
						request.setAttribute("msg", "<div class='alert alert-danger'>Você precisa estar logado!</div>");
						request.getRequestDispatcher("logarcli.jsp").forward(request, response);
					}
				}else {
					request.setAttribute("msg", "<div class='alert alert-danger'>Não há produtos no carrinho!</div>");
					request.getRequestDispatcher("carrinho.jsp").forward(request, response);
				}
			}
		}
		
		processRequest(request, response);
	}

}
