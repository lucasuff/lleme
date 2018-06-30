package trabalho;

import java.util.Date;
import java.util.Scanner;

public class TestaAtendimento {

    public static int codigoAtendimento = 1000;
    public static int NUM_MAX_ATENDIMENTO_SIMULTANEOS = 2;

    public static void menu() {
        System.out.println("\n\n### Opções do sistema ###");
        System.out.println("1 - Recepcionar");
        System.out.println("2 - Atender");
        System.out.println("3 - Encerrar atendimento");
        System.out.println("4 - Gerar estatísticas");
        System.out.println("0 - Sair");
        System.out.println("Opcao:");
    }

    public static void main(String[] args) throws Exception {
        ListaLigada<Atendimento> listaDeEspera = new ListaLigada<>();
        ListaLigada<Atendimento> filaEmAtendimento = new ListaLigada<>();
        ListaLigada<Atendimento> atendimentosRealizados = new ListaLigada<>();

        TipoAssunto[] tiposDeAssuntos = new TipoAssunto[11];
        for (int i = 0; i <= 10; i++)
            tiposDeAssuntos[i] = new TipoAssunto(i, "Assunto " + (i + 1), i);
        // Importa dados do XML
        codigoAtendimento = CarregaDados.lerXML("dados.xml", codigoAtendimento, listaDeEspera, tiposDeAssuntos);

        int opcao;
        Scanner entrada = new Scanner(System.in);

        do {
            menu();
            opcao = entrada.nextInt();
            Atendimento objAtendimento;

            switch (opcao) {
                case 0:
                    System.out.println("Programa encerrado.");
                    break;
                case 1:
                    codigoAtendimento++;
                    objAtendimento = new Atendimento(codigoAtendimento);
                    objAtendimento.recepcionar(tiposDeAssuntos);
                    listaDeEspera.adiciona(objAtendimento);
                    break;

                case 2:
                    Date horaIncioAtendimento = new Date();
                    int idProximo = Atendimento.buscaClienteMaiorPrioridade(listaDeEspera, horaIncioAtendimento);
                    if (listaDeEspera.estaVazia())
                        System.out.println("Não existe nenhum cliente na fila de espera.");
                    else if (filaEmAtendimento.tamanho() == NUM_MAX_ATENDIMENTO_SIMULTANEOS)
                        System.out.println("Por favor, aguarde. Todos os nossos operadores estão ocupados.");
                    else {
                        objAtendimento = listaDeEspera.obtem(idProximo);
                        objAtendimento.atender(horaIncioAtendimento);
                        filaEmAtendimento.adicionaNoFinal(objAtendimento);
                        listaDeEspera.remove(idProximo);
                    }
                    break;

                case 3:
                    if (filaEmAtendimento.estaVazia())
                        System.out.println("Não existe nenhum atendimento em andamento.");
                    else {
                        objAtendimento = filaEmAtendimento.removePrimeiro();
                        objAtendimento.encerrarAtendimento();
                        atendimentosRealizados.adiciona(objAtendimento);
                    }
                    break;

                case 4:
                    Atendimento.gerarEstatistica(atendimentosRealizados);
                    break;

                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
}
