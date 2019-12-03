package elements;

import java.util.ArrayList;

public class Conta{
	private int idConta;
	private static int idContador = 0;
	private int numero;
	private String titular;
	private float saldo;
	private ArrayList<Servico> servicos = new ArrayList<Servico>();
	private ArrayList<Historico> historico = new ArrayList<Historico>();
	private boolean cartao;
	private int tipo;
	
	public Conta(String nome){
		incrementarIdContador();
		titular = nome;
		idConta = idContador;
		numero = idConta;
		cartao = false;
	}
	
	public static void incrementarIdContador() {
		idContador++;
	}
	
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public String getTitular() {
		return titular;
	}
	public float getSaldo() {
		return saldo;
	}
	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}
	public boolean getCartao() {
		return cartao;
	}
	public void setCartao(boolean cartao) {
		this.cartao = cartao;
	}
	public int getIdConta() {
		return idConta;
	}
	
	public ArrayList<Historico> getHistorico(){
		return historico;
	}
	public ArrayList<Servico> getServicos(){
		return servicos;
	}
	
	
	public void addServico(Servico servico) {
		servicos.add(servico);
	}
	public void removeServico(int index) {
		System.out.println("Remover Serviço\nDigite a natureza:");
		boolean fond = false;
		for(int i = 0; i < servicos.size() ; i++) {
			 if(i == index) {
				 fond = true;
				 System.out.println("Fond!");
				 break;
			 }
		}
		if(fond) {
			System.out.println("Serviço "+servicos.get(index).getNatureza()+" remotivo.");
			servicos.remove(index);
		}
		else {
			System.out.println("Serviço não encontrado");
		}
	}
	public void verServicos() {
		System.out.println("Serviços:");
		for(int i = 0; i < servicos.size(); i++) {
			System.out.println("Serviço "+i);
			System.out.println("Naturea: "+servicos.get(i).getNatureza());
			System.out.println("Valor: "+servicos.get(i).getValor());
			System.out.println("Numero de Parcelas: "+servicos.get(i).getNumeroParcelas());
		}
	}
	
	public void extrato(ArrayList<Historico> historico) {
		System.out.println("Extrato:");
		for(int i = 0; i < historico.size(); i++) {
			Historico temp = historico.get(i);
			System.out.printf("%s\n", temp.getDescricao());
		}
	}
	public void saque(float valor) {
		if( valor <= saldo) {
			saldo -= valor;
			Historico temp = new Historico(idConta, valor, "Saque de: ");
			historico.add(temp);
		}
		else {
			System.out.println("Saldo insuficiente\n");
		}
		
	}
	public void deposito(float valor) {
		saldo += valor;
		Historico temp = new Historico(idConta, valor, "Deposito de: ");
		historico.add(temp);
	}
	public void transferencia(float valor, String destino) {
		if( valor <= saldo) {
			saldo -= valor;
			String texto = "Transferencia para: "+destino+", no valor de: ";
			Historico temp = new Historico(idConta, valor, texto);
			historico.add(temp);	
		}
		else {
			System.out.println("Saldo insuficiente\n");
		}
	}

}