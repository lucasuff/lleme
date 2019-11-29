package entidade;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class Funcionario {
	private int id_funcionario;
	private String nome;
	private String email;
	private String senha;
	
	public Funcionario() {
		
	}
	// endereco não entra no construtor
	public Funcionario(int id_funcionario, String nome, String email,
			String senha) {
		super();
		this.id_funcionario = id_funcionario;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
	}

	public int getId_funcionario() {
		return id_funcionario;
	}

	public void setId_funcionario(int id_funcionario) {
		this.id_funcionario = id_funcionario;
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
	
	public static String converterData2(Date data){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String date = sdf.format(data);
		
		return date;
	}
}
