package elements;

public class Servico{
	private float valor;
	private int numeroParcelas;
	private String natureza;
	private float indiceJuros;
	private int idServico;
	private static int idContador;
	
	public Servico(){
		incrementaIdContador();
		idServico = idContador;
	}
	
	private static void incrementaIdContador() {
		idContador++;
	}
	
	public int getIdServico() {
		return idServico;
	}
	
	public float getValor() {
		return valor;
	}
	
	public void setValor(float valor) {
		valor = valor;
	}
	
	public int getNumeroParcelas() {
		return numeroParcelas;
	}
	
	public void setNumeroParcelas(int numeroParcelas) {
		this.numeroParcelas = numeroParcelas;
	}
	
	public String getNatureza() {
		return natureza;
	}
	
	public void setNatureza(String natureza) {
		this.natureza = natureza;
	}
	
	public float getIndiceJuros() {
		return indiceJuros;
	}
	
	public void setIndiceJuros(float indiceJuros) {
		this.indiceJuros = indiceJuros;
	}
}
