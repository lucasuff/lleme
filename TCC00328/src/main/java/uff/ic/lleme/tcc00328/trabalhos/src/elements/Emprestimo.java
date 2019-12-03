package elements;

public class Emprestimo extends Servico{
	
	public void projecao() {
		int i;
		float parcela;
		float total = 0;
		for(i = 1; i <= getNumeroParcelas(); i++) {
			parcela = (float)(getValor()*(Math.pow(getIndiceJuros(), i))); 
			System.out.printf("Parcela %d : R$ %.2f%n", i, parcela);
			System.out.println();
			total += parcela;
		}
		System.out.printf("Total: R$%.2f\n",total);
	}
	public boolean analiseCredito() {
	    return Math.random() < 0.5;
	}	
}