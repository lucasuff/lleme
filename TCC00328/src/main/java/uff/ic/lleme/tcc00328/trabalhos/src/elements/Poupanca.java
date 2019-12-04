package elements;

public class Poupanca extends Conta{
	private float rendimento = 0.5f;
	
	public Poupanca(String nome) {
		super(nome);
		setTipo(2);
	}

	public void rendimentoConta() {
		setSaldo(getSaldo()*(1+getRendimento()));	
	}
	
	public float getRendimento() {
		return rendimento;
	}

	public void setRendimento(float rendimento) {
		this.rendimento = rendimento;
	}
	

}
