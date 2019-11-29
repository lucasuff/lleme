package entidade;

import java.io.Serializable;

public class Pedido implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Integer id_pedido,id_produto,quantidade;
	private Double valor;
	private Produto produto;
	
	public Pedido() {
		// TODO Auto-generated constructor stub
	}

	public Pedido(Integer id_pedido, Integer id_produto, Integer quantidade, Double valor) {
		super();
		this.id_pedido = id_pedido;
		this.id_produto = id_produto;
		this.quantidade = quantidade;
		this.valor = valor;
	}

	public Integer getId_pedido() {
		return id_pedido;
	}

	public void setId_pedido(Integer id_pedido) {
		this.id_pedido = id_pedido;
	}

	public Integer getId_produto() {
		return id_produto;
	}

	public void setId_produto(Integer id_produto) {
		this.id_produto = id_produto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	@Override
	public String toString() {
		return "Pedido [id_pedido=" + id_pedido + ", id_produto=" + id_produto
				+ ", quantidade=" + quantidade + ", valor=" + valor + ", produto=" + produto + "]";
	}


}
