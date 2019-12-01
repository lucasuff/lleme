package trab_final;

import java.util.Scanner;

public class render {
	
	

	public void update(manager M1) {
		
		Scanner sc_1 = new Scanner(System.in);
		cliente user = null;
		int aux = -1;
		int aux2 = -1;
		int aux3 = -1;
		
		
		while (aux2 != 8) {
			aux = -1;
			while (user == null) {
			
				
				System.out.print("1.cadastrar\n2.Logar no sistema\n");
				
				aux = sc_1.nextInt();
			
				if (aux == 1) {
					M1.cadastra_cliente();
				}
				if (aux == 2) {
					user = M1.Login();
					
					
				}
				if (aux == 3) {
					aux = 0;
					aux2 = 8;
				}
			
			
			
			}
			
			aux2 = user.getCpf();
			
			if (user.getAdmin()) {
				while (aux2 != 8) {
					
					System.out.print("\n                                   MENU\n");
					System.out.print("                           DIGITE UM COMANDO\n");
					System.out.print("                  =======================================\n");
					System.out.print("                  |  1. Conceder privilegio              |\n");
					System.out.print("                  |  2. Cadastrar novo produto           |\n");
					System.out.print("                  |  3. remover produto do catalogo      |\n");
					System.out.print("                  |  4. remover usuario                  |\n");
					System.out.print("                  |  5. detalhar catalogo                |\n");
					System.out.print("                  |  6. nova compra                      |\n");
					System.out.print("                  |  7. novo cliente                     |\n");
					System.out.print("                  |  8. Sair                             |\n");
					System.out.print("                  =======================================\n");
					
					
					
					
					aux2 = sc_1.nextInt();
					
				
					if (aux2 == 1) {
						M1.setPrivilegios(user);
					}
					if (aux2 == 2) {
						M1.cadastra_produto(user);
					}
					if (aux2 == 3) {
						M1.remove_produto();
					}
					if (aux2 == 4) {
						M1.remove_usuario();
					}
					if (aux2 == 5) {
						M1.inventario();
					}
					if (aux2 == 6) {
						cliente c1 = M1.busca_cliente();
						if (c1 != null) {
							
						
						System.out.printf("Carrinho de %s\n", c1.getNome());
						
						aux3 = -1;
						while (aux3 != 0) {
							M1.add_carrinho(c1);
							System.out.println("novo produto? (s = 1 / n = 0)");
							aux3 = sc_1.nextInt();
							
							
						}
						System.out.printf("total a pagar: R$ %f\n", c1.getMeu_carrinho().getTotal());
						c1.getMeu_carrinho().produtos.clear();
						c1.getMeu_carrinho().setTotal(0.0);
						}
					}
					if (aux2 == 7) {
						M1.cadastra_cliente();
					}
					
					
				}
				
			}
			
			
		}
		
	
	}

}
