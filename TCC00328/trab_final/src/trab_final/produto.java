package trab_final;

public class produto {
	private String nome;
	private int cod;
	private float preço;
	
	public produto(String nome, int cod, float preço) {
		this.nome = nome;
		this.cod = cod;
		this.preço = preço;
	}
	
	public void info() {
		System.out.printf("=================================\n");
		System.out.printf("nome: %s\ncodigo: %d\npreço: %f\n", this.nome, this.cod, this.preço);
		System.out.printf("=================================\n");
		
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public float getPreço() {
		return preço;
	}

	public void setPreço(float preço) {
		this.preço = preço;
	}
}
