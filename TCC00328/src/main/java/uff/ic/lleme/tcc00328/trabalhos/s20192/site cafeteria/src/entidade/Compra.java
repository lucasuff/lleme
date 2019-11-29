package entidade;

import java.sql.Date;
import java.util.StringJoiner;
import java.util.regex.Pattern;

public class Compra {
	private Integer id_compra, id_cliente, id_pedido;
	//private Cliente cliente;
	//private Pedido pedido;
	private String data_compra;
	private Double valor;
	private Pedido pedido;

	public Compra() {
		// TODO Auto-generated constructor stub
	}

	public Compra(Integer id_compra, Integer id_cliente, Integer id_pedido, String data_compra, Double valor) {
		super();
		this.id_compra = id_compra;
		this.id_cliente = id_cliente;
		this.id_pedido = id_pedido;
		this.data_compra = data_compra;
		this.valor = valor;
	}

	public Integer getId_compra() {
		return id_compra;
	}

	public void setId_compra(Integer id_compra) {
		this.id_compra = id_compra;
	}

	public Integer getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(Integer id_cliente) {
		this.id_cliente = id_cliente;
	}

	public Integer getId_pedido() {
		return id_pedido;
	}

	public void setId_pedido(Integer id_pedido) {
		this.id_pedido = id_pedido;
	}

	public String getData_compra() {
		return data_compra;
	}

	public void setData_compra(String data_compra) {
		this.data_compra = data_compra;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}
	
	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	@Override
	public String toString() {
		return "Compra [id_compra=" + id_compra + ", id_cliente=" + id_cliente + ", id_pedido=" + id_pedido
				+ ", data_compra=" + data_compra + ", valor=" + valor + "]";
	}
}
