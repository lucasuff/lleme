package trab_final;

import java.util.ArrayList;

public class carrinho {
	private cliente dono;
	private double total;
	ArrayList<produto> produtos;
	
	public carrinho(cliente dono) {
		this.dono = dono;
		this.total = 0.0f;
		this.produtos = new ArrayList<>();
		
	}

	public void add_prod(produto p) {
		produtos.add(p);
		this.total += p.getPreço();
	}
	
	public cliente getDono() {
		return dono;
	}

	public void setDono(cliente dono) {
		this.dono = dono;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double d) {
		this.total = d;
	}

	public ArrayList<produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(ArrayList<produto> produtos) {
		this.produtos = produtos;
	}
	
	
}
