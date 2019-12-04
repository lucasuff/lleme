package elements;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Bd<Usuario> usuarioTable = new Bd<Usuario>();
		Bd<Servico> servicoTable = new Bd<Servico>();
		Bd<Conta> contaTable = new Bd<Conta>();
		int input;
		boolean sair = false;
		Usuario usuario;
		
		
		while(!sair) {
			System.out.println("Login:\n1 - Normal\n2 - Admin\n3 - Sair");
			input = sc.nextInt();
			switch (input) {
			case 1:
				while(!sair) {
					System.out.println("Login Usuario\nDigite a ID:");
					boolean valido = false;
					Usuario usuarioOnline = null;
					input = sc.nextInt();
					for(int i = 0; i < usuarioTable.table.size(); i++) {
						usuario = usuarioTable.table.get(i);
						if(usuario.getIdUsuario() == input) {
							usuarioOnline = usuario;
							valido = true;
							break;
						}
					}
					if(valido) {
						System.out.println("Acesso permitdo");
						while(!sair) {
							if(usuarioOnline.getConta() == null) {
								System.out.println("Menu\n Usuario: "+usuarioOnline.getNome()+"\n1 - Abrir conta\n2 - Sair");
								input = sc.nextInt();
								switch(input) {
								case 1:
									System.out.println("Abrir conta:\n1 - Corrente\n2 - Poupança\n3 - Salario\n4 - Voltar");
									sc.nextLine();
									input = sc.nextInt();
									switch(input) {
									case 1:
										Corrente contaCorrente = new Corrente(usuarioOnline.getNome());
										usuarioOnline.setConta(contaCorrente);
										contaTable.table.add(contaCorrente);
										System.out.println("Conta criada!\n");
										break;
									case 2:
										Poupanca contaPoupanca = new Poupanca(usuarioOnline.getNome());
										usuarioOnline.setConta(contaPoupanca);
										contaTable.table.add(contaPoupanca);
										System.out.println("Conta criada!\n");
										break;
									case 3:
										Salario contaSalario = new Salario(usuarioOnline.getNome());
										System.out.println("Digite o nome do empregador:");
										contaSalario.setNomeEmpregador(sc.next());
										usuarioOnline.setConta(contaSalario);
										contaTable.table.add(contaSalario);
										System.out.println("Conta criada!\n");
										break;
									case 4:
										System.out.println("Voltar");
									}	
									break;
								case 2:
									sair = true;

								}
							}
							else {
								System.out.println("Menu Usuario:\n1 - Saque\n2 - Deposito\n3 - DOC\n4 - Extrato\n5 - Serviços\n6 - Sair");
								input = sc.nextInt();
								switch (input) {
								case 1:
									System.out.println("Saque\nDigite o valor:");
									usuarioOnline.getConta().saque(sc.nextFloat());
									break;
								case 2:
									System.out.println("Deposito\nDigite o valor:");
									usuarioOnline.getConta().deposito((float)sc.nextFloat());
									break;
								case 3: 
									System.out.println("DOC\nDigite o valor:");
									float valorTemp = sc.nextFloat();
									System.out.println("Digite o destino:");
									usuarioOnline.getConta().transferencia(valorTemp, sc.next());
									break;
								case 4:
									System.out.println("Extrato\n");
									usuarioOnline.getConta().extrato(usuarioOnline.getConta().getHistorico());
									System.out.println("Saldo: "+usuarioOnline.getConta().getSaldo());
									break;
								case 5:
									while(!sair) {
										System.out.println("Serviços:\n1 - Add Serviço\n2 - Rm Serviço\n3 - Ver Serviços\n4 - Voltar");
										input = sc.nextInt();
										switch (input) {
										case 1:
											System.out.println("Add Serviço\n1 - Emprestimo\n2 - Financiamento\n3 - Seguro\n4 - Voltar");
											input = sc.nextInt();
											switch(input) {
											case 1:
												System.out.println("Emprestimo:");
												Emprestimo tempEmpre = new Emprestimo();
												tempEmpre.setNatureza("Emprestimo");
												System.out.println("Digite o valor:");
												tempEmpre.setValor((float)sc.nextFloat());
												System.out.println("Digite o numero de parcelas:");
												tempEmpre.setNumeroParcelas(sc.nextInt());
												tempEmpre.setIndiceJuros(0.2f);
												usuarioOnline.getConta().addServico(tempEmpre);
												System.out.println("Serviço adicionado\n");
												tempEmpre.projecao();
												break;
											case 2:
												System.out.println("Financiamento:");
												Financiamento tempFinan = new Financiamento();
												tempFinan.setNatureza("Financiamento");
												System.out.print("Digite o valor:");
												tempFinan.setValor((float)sc.nextFloat());
												System.out.print("Digite o numero de parcelas:");
												tempFinan.setNumeroParcelas(sc.nextInt());
												System.out.print("Digite o motivo:");
												tempFinan.setMotivo(sc.next());
												tempFinan.setIndiceJuros(0.2f);
												usuarioOnline.getConta().addServico(tempFinan);
												System.out.print("Serviço adicionado");
												tempFinan.projecao();
												break;
											case 3:
												System.out.println("Seguro:");
												Seguro tempSeg = new Seguro();
												tempSeg.setNatureza("Seguro");
												System.out.println("Digite o valor:");
												tempSeg.setValor((float)sc.nextFloat());
												System.out.println("Digite o numero de parcelas:");
												tempSeg.setNumeroParcelas(sc.nextInt());
												System.out.println("Digite o motivo");
												tempSeg.setNome(sc.next());
												tempSeg.setIndiceJuros(0.2f);
												usuarioOnline.getConta().addServico(tempSeg);
												System.out.println("Serviço adicionado");
												break;
											case 4:
												System.out.println("Voltar");
											}
											break;
										case 2:
											System.out.println("Remover Serviço");
											System.out.print("Digite o index do Serviço:");
											int index = sc.nextInt();
											usuarioOnline.getConta().removeServico(index);
											break;
										case 3:
											System.out.println("Ver Serviços:\n");
											usuarioOnline.getConta().verServicos();
											break;
										case 4:
											sair = true;
											System.out.println("Voltar");	
										}
									}
									sair = false;
									break;
								case 6:
									sair = true;
								}//end of switch
							}//end of else
						}//end of while
					}
					else {
						System.out.println("Acesso negado");
					}
				}
				sair = false;
				break;
				
			case 2:
				while(!sair) {
					System.out.println("MENU ADMIN:\n1 - Add Usuario\n2 - Rm Usuario\n3 - Ver Usuarios\n4 - Sair");
					sc.nextLine();
					input = sc.nextInt();
					switch (input) {
					case 1:
						Usuario temp = new Usuario();
						System.out.println("Add Usuario:\nNome:");
						temp.setNome(sc.next());
						System.out.println("CPF:");
						temp.setCpf(sc.next());
						System.out.println("Telefone:");
						temp.setTelefone(sc.next());
						System.out.println("Endereço:");
						temp.setEndereco(sc.next());
						temp.setTipo(0);
						usuarioTable.table.add(temp);
						break;
					case 2:
						System.out.println("Remove Usuario:\nDigite o Nome:");
						String nome = sc.next();
						for(int i = 0; i < usuarioTable.table.size(); i++) {
							usuario = usuarioTable.table.get(i);
							if(usuario.getNome() == nome) {
								usuarioTable.table.remove(i);
								System.out.printf("Usuario %s removido", nome);
							}
							else {
								System.out.println("Usuario não encontrado!");
							}
						}
						break;
					case 3:
						System.out.println("Ver Usuarios");
						for(int i = 0; i < usuarioTable.table.size(); i++) {
							usuario = usuarioTable.table.get(i);
							System.out.println("Usuario "+i);
							System.out.println("Nome: "+usuario.getNome());
							System.out.println("CPF: "+usuario.getCpf());
							System.out.println("Telefone: "+usuario.getTelefone());
							System.out.println("Endereço: "+usuario.getEndereco());
							System.out.println("ID: "+usuario.getIdUsuario());
						}
						break;
					case 4:
						sair = true;			
					}
				}
				sair = false;
				break;
				
			case 3:
				sair = true;
			}
			
		}
		System.exit(0);
		
		
	}
}

