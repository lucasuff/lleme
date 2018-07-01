package uff.ic.lleme.tic10002.trabalhos.s20181.Raiane.model;

import uff.ic.lleme.tic10002.trabalhos.s20181.Raiane.model.Cliente;
import uff.ic.lleme.tic10002.trabalhos.s20181.Raiane.model.Assunto;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import uff.ic.lleme.tic10002.trabalhos.s20181.Raiane.AssuntoComparator;
import uff.ic.lleme.tic10002.trabalhos.s20181.Raiane.EstatisticaTipoAssunto;
import uff.ic.lleme.tic10002.trabalhos.s20181.Raiane.EstatisticaTipoAssuntoComparator;
import uff.ic.lleme.tic10002.trabalhos.s20181.Raiane.ListaLigada;

public class Atendimento {

    private final int id;
    public Cliente cliente;
    public ListaLigada<Assunto> assuntos;
    public Date horaChegada;
    public Date horaIncioAtendimento;

    public static double PRIORIDADE_CLIENTE_IDADE = 65.0;
    public static double PRIORIDADE_CLIENTE_ESPERA = 15.0;
    public static double PRIORIDADE_CLIENTE_URGENCIA = 10.0;

    public Atendimento(int id) {
        this.id = id;
        this.assuntos = new ListaLigada<>();
    }

    public void importaCliente(Cliente c, ListaLigada<Assunto> a) {
        this.cliente = c;
        this.horaChegada = new Date();
        this.assuntos = a;
    }

    /**
     * Recebe os dados dos clientes e suas demandas e os coloca em espera de
     * atendimento
     *
     * @param tiposDeAssuntos
     */
    public void recepcionar(TipoAssunto tiposDeAssuntos[]) {
        String cpf;
        String nome;
        int idade;

        System.out.println("*************************************************************");
        System.out.println("**************** ENTRADA DE DADOS DO CLIENTE ****************");
        System.out.println("*************************************************************");

        Scanner entrada = new Scanner(System.in);
        System.out.println("Informe o nome do cliente: ");
        nome = entrada.nextLine();

        entrada = new Scanner(System.in);
        System.out.println("Informe o CPF do cliente: ");
        cpf = entrada.next();

        entrada = new Scanner(System.in);
        System.out.println("Informe a idade do cliente: ");
        idade = entrada.nextInt();

        this.cliente = new Cliente(this.id, nome, cpf, idade);
        this.horaChegada = new Date();

        this.adicionarAssuntos(tiposDeAssuntos);
    }

    /**
     * Fornece os dados referentes às demandas do próximo cliente a ser atendido
     * priorizando os clientes conforme sua idade, tempo de espera na fila e
     * grau de urgência dos assuntos
     *
     * @param horaIncioAtendimento
     */
    public void atender(Date horaIncioAtendimento) {
        this.horaIncioAtendimento = horaIncioAtendimento;
        System.out.println("*************************************************************");
        System.out.println("******************* INICIANDO ATENDIMENTO *******************");
        System.out.println("*************************************************************");
        System.out.println("Cliente: " + this.cliente.nome);
        System.out.printf("Prioridade: %.2f \n", this.calculaPrioridade(this.horaIncioAtendimento));
        Assunto.imprimeAssuntos(this.assuntos);
    }

    /**
     * Registra o fim dos atendimentos e as providências que foram tomadas para
     * atender as demandas dos clientes
     */
    public void encerrarAtendimento() {
        System.out.println("*************************************************************");
        System.out.println("******************* ENCERRANDO ATENDIMENTO ******************");
        System.out.println("*************************************************************");
        System.out.println("Cliente: " + this.cliente.nome);
        Random duracao = new Random();

        Assunto assunto;
        for (int i = 0; i < this.assuntos.tamanho(); i++) {
            assunto = this.assuntos.obtem(i);
            assunto.providencia = "Providência tomada no assunto de urgência " + assunto.tipoAssunto.getUrgencia();
            assunto.duracaoAtendimento = duracao.nextDouble() * 30;
            System.out.println(assunto.toString());
        }
    }

    /**
     * Exibe o tempo médio, por tipo de assunto, dos atendimento encerrados
     *
     * @param atendimentosRealizados
     */
    public static void gerarEstatistica(ListaLigada<Atendimento> atendimentosRealizados) {
        ListaLigada<EstatisticaTipoAssunto> listaPorTipoAssuntos = new ListaLigada<>();
        EstatisticaTipoAssunto objEstatisticaTipoAssunto;

        for (int i = 0; i < atendimentosRealizados.tamanho(); i++) {
            Assunto assunto;
            Atendimento atendimentoRealizado = atendimentosRealizados.obtem(i);
            for (int j = 0; j < atendimentoRealizado.assuntos.tamanho(); j++) {
                assunto = atendimentoRealizado.assuntos.obtem(j);
                objEstatisticaTipoAssunto = EstatisticaTipoAssunto.buscaElemento(assunto.tipoAssunto.tipo, listaPorTipoAssuntos);
                if (objEstatisticaTipoAssunto != null)
                    objEstatisticaTipoAssunto.atualizaTempo(assunto.duracaoAtendimento);
                else {
                    objEstatisticaTipoAssunto = new EstatisticaTipoAssunto(assunto.tipoAssunto.tipo, assunto.duracaoAtendimento);
                    //listaPorTipoAssuntos.adiciona(objEstatisticaTipoAssunto);
                    listaPorTipoAssuntos.adicionaComPrioridade(objEstatisticaTipoAssunto, new EstatisticaTipoAssuntoComparator());
                }
            }
        }

        System.out.println("*************************************************************");
        System.out.println("****************** ESTATÍSTICAS DO SISTEMA ******************");
        System.out.println("*************************************************************");

        if (listaPorTipoAssuntos.estaVazia())
            System.out.println("Não existe valores para serem exibidos!");
        else {
            System.out.println("Tempo médio e total de atendimentos para cada tipo de assunto");
            EstatisticaTipoAssunto listaPorTipoAssunto;
            for (int i = 0; i < listaPorTipoAssuntos.tamanho(); i++) {
                listaPorTipoAssunto = listaPorTipoAssuntos.obtem(i);
                double tempoMedio = listaPorTipoAssunto.tempoTotal / (double) listaPorTipoAssunto.totalAtendimentos;
                System.out.printf(
                        "Tipo: %s  |  Tempo médio: %.2f  |  Total de atendimentos: %s \n",
                        listaPorTipoAssunto.idTipo,
                        tempoMedio,
                        listaPorTipoAssunto.totalAtendimentos
                );
            }
        }

        System.out.println("*************************************************************");
    }

    @Override
    public String toString() {
        return this.cliente.toString()
                + " - Hora chegada: " + this.horaChegada
                + " - Início atendimento: " + this.horaIncioAtendimento;
    }

    public double diferencaEmMinutos(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        double minutos = (diff / 1000) / 60;
        long segundosRestantes = (diff / 1000) % 60;
        double result = minutos + (segundosRestantes / 60d);
        return result;
    }

    public void adicionarAssuntos(TipoAssunto tiposDeAssuntos[]) {
        System.out.println("*************************************************************");
        System.out.println("Selecione o tipo de assunto do cliente");
        for (int i = 0; i < tiposDeAssuntos.length; i++)
            System.out.println(
                    "["
                    + Integer.toString(i)
                    + "] "
                    + tiposDeAssuntos[i].titulo
            );

        String adicionarNovoAssunto = "S";
        int idTipoAssunto;
        do
            try {
                Scanner entrada = new Scanner(System.in);
                System.out.printf("\nCodigo do assunto: ");
                idTipoAssunto = entrada.nextInt();
                if (idTipoAssunto < 0 || idTipoAssunto >= tiposDeAssuntos.length) {
                    System.out.println("Opção inválida!");
                    continue;
                } else {
                    TipoAssunto tipoDeAssunto = tiposDeAssuntos[idTipoAssunto];
                    String descricao;

                    System.out.printf("Descreva o assunto: ");
                    entrada = new Scanner(System.in);
                    descricao = entrada.nextLine();

                    Assunto assunto = new Assunto(tipoDeAssunto, descricao, null, null);
                    this.assuntos.adicionaComPrioridade(assunto, new AssuntoComparator());
                }
                System.out.printf("Deseja adicionar um novo assunto [S/N]? R.: ");
                entrada = new Scanner(System.in);
                adicionarNovoAssunto = entrada.next();

            } catch (InputMismatchException e) {
                System.out.println("Opção inválida!");
            }
        while (adicionarNovoAssunto.toUpperCase().equals("S"));

    }

    public Double calculaPrioridade(Date d2) {
        double idade = this.cliente.idade / PRIORIDADE_CLIENTE_IDADE;
        double espera = (this.diferencaEmMinutos(this.horaChegada, d2)) / PRIORIDADE_CLIENTE_ESPERA;
        double mediaDosAssuntos = (Assunto.calculaMediaAssuntos(this.assuntos)) / PRIORIDADE_CLIENTE_URGENCIA;

        return (idade + espera + mediaDosAssuntos) / 3.0;
    }

    public static int buscaClienteMaiorPrioridade(ListaLigada<Atendimento> filaDeEspera, Date horaIncioAtendimento) {
        double maiorPrioridade = -1;
        int idProximoAtendimento = -1;
        for (int i = 0; i < filaDeEspera.tamanho(); i++) {
            double prioridade = filaDeEspera.obtem(i).calculaPrioridade(horaIncioAtendimento);
            if (maiorPrioridade < prioridade) {
                maiorPrioridade = prioridade;
                idProximoAtendimento = i;
            }
        }
        return idProximoAtendimento;
    }

}
