package elements;

public class Usuario {
	private String nome;
	private String cpf;
	private String telefone;
	private String endereco;
	private int idUsuario;
	private int tipo;
	private int idConta;
	private Conta conta;
	private static int idContador = 0;
	
	Usuario(){
		idUsuario = incrementaIdUsuario();
		conta = null;
	}

	private static int incrementaIdUsuario() {
		return idContador++;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public int getIdConta() {
		return idConta;
	}
	
	public Conta getConta() {
		return conta;
	}
	
	public void setConta(Conta conta) {
		this.conta = conta;
	}
	
	public void criarConta() {
		conta = new Conta(nome);
		idConta = conta.getIdConta();
		
	}
	
	
}
