package elements;

public class Despesa {
	private String nome;
	private float valor;
	private int idDespesa;
	private static int idContador;
	
	Despesa(){
		incrementaIdContador();
		idDespesa = idContador;
	}
	
	private static void incrementaIdContador() {
		idContador++;
	}
	
	public int getIdDespesa() {
		return idDespesa;
	}
	
	public String getDescricao() {
		return nome;
	}
	public void setDescricao(String descricao) {
		this.nome = descricao;
	}
	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		this.valor = valor;
	}
}

