package elements;

import java.util.ArrayList;

public class Corrente extends Conta {
	private boolean cartaoCredito;
	private ArrayList<Despesa> debitoAutomatico = new ArrayList<Despesa>();
	
	public Corrente(String nome) {
		super(nome);
		cartaoCredito = false;
		setTipo(1);
	}
	
	public boolean getCartaoCredito() {
		return cartaoCredito;
	}
	
	public void setCartaoCredito() {
		cartaoCredito = !cartaoCredito;
	}
	//todo: Escrever metodos para o array list
	
	public void iban(float valor) {
		//Tranferencia de valores em moeda estrangeira
	}
	
	public void addListDespesa(Despesa despesa) {
		debitoAutomatico.add(despesa);
	}
	
	public void rmListDespesa(String nome) {
		for (int i = 0; i >= debitoAutomatico.size(); i++) {
			Despesa temp = debitoAutomatico.get(i);
			if(temp.getDescricao() == nome) {
				debitoAutomatico.remove(i);
			}
		}
	}


	
	

}
