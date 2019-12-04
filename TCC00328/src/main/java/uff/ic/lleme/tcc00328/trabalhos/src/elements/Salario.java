package elements;

import java.util.ArrayList;

public class Salario extends Conta{
	private float creditoLimite;
	private String nomeEmpregador;
	private ArrayList<Despesa> debitoAutomatico = new ArrayList<Despesa>();
	private float desconto;
	
	public Salario(String nome) {
		super(nome);
		setTipo(3);	
	}
	
	public float getCreditoLimite() {
		return creditoLimite;
	}

	public void setCreditoLimite(float creditoLimite) {
		this.creditoLimite = creditoLimite;
	}

	public String getNomeEmpregador() {
		return nomeEmpregador;
	}

	public void setNomeEmpregador(String nomeEmpregador) {
		this.nomeEmpregador = nomeEmpregador;
	}

	public float getDesconto() {
		return desconto;
	}

	public void setDesconto(float desconto) {
		this.desconto = desconto;
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
