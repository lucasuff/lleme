package elements;

public class Financiamento extends Servico{
	
	private String motivo;

	public void projecao() {
		int i;
		float amortizacao, parcela;
		float total = 0;
		amortizacao = getValor() / getNumeroParcelas();
		for(i = 1; i <= getNumeroParcelas(); i++) {
			parcela = amortizacao + (getIndiceJuros() * (getValor() - ((i-1) * amortizacao)));
			System.out.printf("Parcela %d :R$ %.2f\n", i, parcela);
			total += parcela;
		}
		System.out.printf("Total: R$ %.2f\n",total);
	}
	public boolean analiseCredito() {
	    return Math.random() < 0.5;
	}
	
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
}