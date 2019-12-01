package trab_final;



public class cliente {
	private String nome;
	private int cpf;
	private String endereço;
	private String password;
	private boolean online;
	private boolean admin;
	private carrinho meu_carrinho;
	
	public cliente(String nome, int cpf,String endereço, String password) {
		this.nome = nome;
		this.cpf = cpf;
		this.endereço = endereço;
		this.password = password;
		if ((nome == "default") && (password == "12345")) {
			this.admin = true;
		} else {
			this.admin = false;
		}
		this.meu_carrinho = new carrinho(this);
		this.online = false;
	}
	
	public boolean getAdmin() {
		return admin;
	}
	
	public void removePrivilegios(cliente c) {
		if (c.getAdmin()) {
			if (this.admin) {
				this.admin = false;
			} else {
				System.out.printf("%s nao e mas um administrador", this.nome);
			}
		} else {
			System.out.println("acesso negado");
		}
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getCpf() {
		return cpf;
	}

	public void setCpf(int cpf) {
		this.cpf = cpf;
	}

	public String getEndereço() {
		return endereço;
	}

	public void setEndereço(String endereço) {
		this.endereço = endereço;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public carrinho getMeu_carrinho() {
		return meu_carrinho;
	}

	public void setMeu_carrinho(carrinho meu_carrinho) {
		this.meu_carrinho = meu_carrinho;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}
}