package trab_final;

import java.util.ArrayList;
import java.util.Scanner;

public class manager {
	ArrayList<cliente> cliente_db;
	ArrayList<produto> produto_db;
	
	public manager(ArrayList<cliente> cliente_db, ArrayList<produto> produto_db) {
		this.cliente_db = cliente_db;
		this.produto_db = produto_db;
		
	}
	
	
	public produto busca_produto() {
		Scanner sc_int = new Scanner(System.in);
		int aux;
		boolean find = false;
		System.out.println("Imforme o codigo: ");
		aux = sc_int.nextInt();
		produto p1 = null;
		
		
		for (produto p:this.produto_db) {
			if (aux == p.getCod()) {
				p1 = p;
				find = true;
			}
		}
		if (!find) {
			System.out.println("produto nao encontrado.");
		}
		return p1;
	}
	
	
	public cliente busca_cliente() {
		Scanner sc_string = new Scanner(System.in);
		cliente c1 = null;
		String name;
		boolean find = false;
		
		System.out.println("Informe o nome: ");
		name = sc_string.next();
		
		for (cliente c: this.cliente_db) {
			if (c.getNome().equals(name)) {
				c1 = c;
				find = true;
			}
		}
		
		if (!find) {
			System.out.println("Usuario nao encontrado.");
		}
		return c1;
		
	}
	
	
	public void setPrivilegios(cliente call) {
		cliente c1 = null;
		
		c1 = this.busca_cliente();
		if (c1 != null) {
		if (call.getAdmin()) {
			if (c1.getAdmin()) {
				System.out.println("Usuario ja possui privilegios.");
			} else {
				c1.setAdmin(true);
				System.out.printf("%s agora e administrador", c1.getNome());
				}
			} else {
				System.out.println("acesso negado");
			}
		}
	}
	
	
	
	public cliente Login() {
		Scanner sc_string = new Scanner(System.in);
		Scanner sc2_string = new Scanner(System.in);
		
		boolean on = false;
		cliente user = null;
		boolean pass_error = false;
		
		System.out.println("Nome: ");
		String name = sc_string.nextLine();
		System.out.println("Senha: ");
		String pass = sc2_string.nextLine();
		
		for (cliente c : this.cliente_db) {
			
			if (c.getNome().equals(name)) {
				if (c.getPassword().equals(pass)) {
					user = c;
					on = true;
					
				} else {
					System.out.print("Senha invalida\n");
					pass_error = true;
				}
			}
		}
		if (on == false) {
			if (pass_error == false) {
				System.out.print("Usuario nao encontrado\n");
			}
		
		}
		
		
		return user;
	}
	
	
	
	public void Logout(cliente c) {
		c.setOnline(false);
	}
	
	
	
	public void cadastra_cliente() {
	    Scanner sc1 = new Scanner(System.in);
	    Scanner sc2 = new Scanner(System.in);
		String nome;
	    int cpf;
	    String endereço;
	    String password;
	    cliente c1;
	    
	    

		System.out.print("Informe o seu nome:");
		nome = sc1.next();
		  
		System.out.print("Informe o cpf: ");
		cpf = sc2.nextInt();
		  
		System.out.print("Informe o endereço para entrega: ");
		endereço = sc1.next();
		  
		System.out.print("Informe uma senha: ");
		password = sc1.next();
		
		
		c1 = new cliente(nome, cpf, endereço, password);
		this.cliente_db.add(c1);
		
    }

	
	public void cadastra_produto(cliente call) {
		Scanner sc1 = new Scanner(System.in);
	    Scanner sc2 = new Scanner(System.in);
		
		String nome;
	    int cod;
	    float preço;
	    
	    if (call.getAdmin()) {
	    	produto p1;
		    
		    System.out.printf("Informe o nome do produto:\n");
		    nome = sc1.next();
		  
		    System.out.printf("\nInforme o codigo: ");
		    cod = sc2.nextInt();
		  
		    System.out.printf("Informe o preço: ");
		    preço = sc1.nextFloat();
		    
		    p1 = new produto(nome, cod, preço);
		    this.produto_db.add(p1);
	    } else {
	    	System.out.print("Acesso negado");
	    }
   }

		
	public void remove_produto() {
		produto p1 = this.busca_produto();
		if (p1 != null) {
			this.produto_db.remove(this.produto_db.indexOf(p1));
		}
	}
	
	
	public void remove_usuario() {
		cliente c1 = this.busca_cliente();
		if (c1 != null) {
			this.cliente_db.remove(this.cliente_db.indexOf(c1));
		}
	}
	
	
	public void inventario() {
		for (produto c:this.produto_db) {
			c.info();
		}
	}
	
	
	public void add_carrinho(cliente c) {
		produto p1 = null;
		p1 = this.busca_produto();
		if (p1 != null) {
			p1.info();
			c.getMeu_carrinho().add_prod(p1);
			System.out.printf("total: R$ %f", c.getMeu_carrinho().getTotal());
		}
			
	}
	
	
	
	
	
	
}
