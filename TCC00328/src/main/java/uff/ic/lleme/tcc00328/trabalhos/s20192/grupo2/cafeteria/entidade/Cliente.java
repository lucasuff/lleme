package entidade;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class Cliente {
	private int id_cliente;
	private String nome;
	private String email;
	private String senha;
	
	public Cliente() {
		
	}
	
	public Cliente(int id_cliente, String nome, String email, String senha) {
		this.id_cliente = id_cliente;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
	}

	public int getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public static Date converterData(String data){
		String novaData[] = data.split("/");
		String dia = novaData[0];
		String mes = novaData[1];
		String ano = novaData[2];
		GregorianCalendar cal = new GregorianCalendar(new Integer(ano), 
				new Integer(mes) - 1, new Integer(dia));
		return cal.getTime();
	}
	
	public static String converterData2(Date data){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String date = sdf.format(data);
		
		return date;
	}
	
	
}
