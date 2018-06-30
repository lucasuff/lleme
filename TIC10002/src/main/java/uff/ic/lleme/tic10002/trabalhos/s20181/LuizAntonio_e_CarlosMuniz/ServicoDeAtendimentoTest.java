/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.ic.lleme.tic10002.trabalhos.s20181.LuizAntonio_e_CarlosMuniz;

import java.util.Calendar;

/**
 *
 * @author Luiz Antonio
 */
public class ServicoDeAtendimentoTest {

    public static void main(String[] args) {

        // cria clientes com id, nome, idade
        Cliente cliente1 = new Cliente(1, "John", 40);
        Cliente cliente2 = new Cliente(2, "Paul", 75);
        Cliente cliente3 = new Cliente(3, "George", 58);
        Cliente cliente4 = new Cliente(4, "Ringo", 77);

        // cria horários de chegada
        Calendar chegada1 = Calendar.getInstance();
        chegada1.set(Calendar.HOUR_OF_DAY, 15);
        chegada1.set(Calendar.MINUTE, 00);
        chegada1.set(Calendar.SECOND, 00);

        Calendar chegada2 = Calendar.getInstance();
        chegada2.set(Calendar.HOUR_OF_DAY, 16);
        chegada2.set(Calendar.MINUTE, 15);
        chegada2.set(Calendar.SECOND, 00);

        Calendar chegada3 = Calendar.getInstance();
        chegada3.set(Calendar.HOUR_OF_DAY, 17);
        chegada3.set(Calendar.MINUTE, 30);
        chegada3.set(Calendar.SECOND, 00);

        Calendar chegada4 = Calendar.getInstance();
        chegada4.set(Calendar.HOUR_OF_DAY, 18);
        chegada4.set(Calendar.MINUTE, 45);
        chegada4.set(Calendar.SECOND, 00);

        // cria horários de atendimento
        Calendar atendido1 = Calendar.getInstance();
        atendido1.set(Calendar.HOUR_OF_DAY, 15);
        atendido1.set(Calendar.MINUTE, 37);
        atendido1.set(Calendar.SECOND, 00);

        Calendar atendido2 = Calendar.getInstance();
        atendido2.set(Calendar.HOUR_OF_DAY, 16);
        atendido2.set(Calendar.MINUTE, 54);
        atendido2.set(Calendar.SECOND, 00);

        Calendar atendido3 = Calendar.getInstance();
        atendido3.set(Calendar.HOUR_OF_DAY, 18);
        atendido3.set(Calendar.MINUTE, 30);
        atendido3.set(Calendar.SECOND, 00);

        Calendar atendido4 = Calendar.getInstance();
        atendido4.set(Calendar.HOUR_OF_DAY, 19);
        atendido4.set(Calendar.MINUTE, 10);
        atendido4.set(Calendar.SECOND, 00);

        // cria tipos de assunto
        TipoAssunto tipoA = new TipoAssunto("A", "Tipo A", 7);
        TipoAssunto tipoB = new TipoAssunto("B", "Tipo B", 7);
        TipoAssunto tipoC = new TipoAssunto("C", "Tipo C", 3);
        TipoAssunto tipoD = new TipoAssunto("D", "Tipo D", 10);
        TipoAssunto tipoE = new TipoAssunto("E", "Tipo E", 4);

        // cria assuntos
        Assunto assunto1 = new Assunto(tipoA, "Assunto 1");// urgência 7
        assunto1.setDuracaoAtendimento(12);

        Assunto assunto2 = new Assunto(tipoA, "Assunto 2");// urgência 7
        assunto2.setDuracaoAtendimento(21);

        Assunto assunto3 = new Assunto(tipoB, "Assunto 3");// urgência 7
        assunto3.setDuracaoAtendimento(34);

        Assunto assunto4 = new Assunto(tipoC, "Assunto 4");// urgência 3
        assunto4.setDuracaoAtendimento(5);

        Assunto assunto5 = new Assunto(tipoD, "Assunto 5");// urgência 10
        assunto5.setDuracaoAtendimento(57);

        Assunto assunto6 = new Assunto(tipoE, "Assunto 6");// urgência 4
        assunto6.setDuracaoAtendimento(48);

        // cria conjuntos de assuntos
        Assunto[] assuntos1 = new Assunto[4];
        assuntos1[0] = assunto1;
        assuntos1[1] = assunto2;
        assuntos1[2] = assunto4;
        assuntos1[3] = assunto5;

        Assunto[] assuntos2 = new Assunto[3];
        assuntos2[0] = assunto1;
        assuntos2[1] = assunto2;
        assuntos2[2] = assunto3;

        Assunto[] assuntos3 = new Assunto[3];
        assuntos3[0] = assunto4;
        assuntos3[1] = assunto5;
        assuntos3[2] = assunto6;

        Assunto[] assuntos4 = new Assunto[2];
        assuntos4[0] = assunto4;
        assuntos4[1] = assunto6;

        // cria atendimentos
        Atendimento atendimento1 = new Atendimento(cliente1, assuntos1, chegada1);
        atendimento1.setHoraAtendimento(atendido1);

        Atendimento atendimento2 = new Atendimento(cliente2, assuntos2, chegada2);
        atendimento2.setHoraAtendimento(atendido2);

        Atendimento atendimento3 = new Atendimento(cliente3, assuntos3, chegada3);
        atendimento3.setHoraAtendimento(atendido3);

        Atendimento atendimento4 = new Atendimento(cliente4, assuntos4, chegada4);
        atendimento4.setHoraAtendimento(atendido4);

        // cria um horario de atendimento que é posterior a todos os horários de chegada dos clientes
        Calendar horaAtendimento = Calendar.getInstance();
        horaAtendimento.set(Calendar.HOUR_OF_DAY, 20);
        horaAtendimento.set(Calendar.MINUTE, 27);
        horaAtendimento.set(Calendar.SECOND, 00);

        /*
        System.out.println(cliente1);
        System.out.println(cliente2);
        System.out.println(cliente3);
        System.out.println(cliente4);
         */

 /*
        System.out.println(tipo1);
        System.out.println(tipo2);
        System.out.println(tipo3);
        System.out.println(tipo4);
        System.out.println(tipo5);
         */

 /*
        System.out.println(assunto1);
        System.out.println(assunto2);
        System.out.println(assunto3);
        System.out.println(assunto4);
        System.out.println(assunto5);
        System.out.println(assunto6);
         */

 /*
        System.out.println(atendimento1);
        System.out.println(atendimento2);
        System.out.println(atendimento3);
        System.out.println(atendimento4);
         */

 /*
        System.out.println(horaAtendimento);
         */
        ListaDeAtendimentos atendimentos = new ListaDeAtendimentos();

        testaIncluirAtendimentos(atendimentos, atendimento1, atendimento3);

        testaRemoverAtendimentos(atendimentos);

        ListaDeAtendimentosEncerrados encerrados = new ListaDeAtendimentosEncerrados();

        testaIncluirAtendimentosEncerrados(encerrados, atendimento1, atendimento2, atendimento3, atendimento4);

        testaBuscarAtendimentosEncerrados(encerrados);

        testaCalculaTempoMedioDeAtendimento(encerrados, horaAtendimento, tipoA, tipoB, tipoC, tipoD, tipoE);

        Heap heap = new Heap();

        testaInsereHeap(heap, atendimento1, atendimento2, atendimento3, atendimento4);

        testaRemoveHeap(heap, horaAtendimento);

    }

    public static void testaIncluirAtendimentos(ListaDeAtendimentos atendimentos, Atendimento atendimento1, Atendimento atendimento3) {
        System.out.println("\nIncluindo atendimentos ...");
        // insere os clientes que estão sendo atendidos
        atendimentos.incluir(atendimento1);
        atendimentos.incluir(atendimento3);

        // exibe os clientes que estão sendo atendidos
        atendimentos.print();
    }

    public static void testaRemoverAtendimentos(ListaDeAtendimentos atendimentos) {
        System.out.println("\nRemovendo atendimentos para seu encerramento ...");
        // remove clientes da lista para indicar o encerramento de seu atendimento
        System.out.println(atendimentos.buscar(1));
        System.out.println(atendimentos.buscar(2));// null, pois não existe
        System.out.println(atendimentos.buscar(3));
        System.out.println(atendimentos.buscar(4));// lista vazia & null
        /*
        O método buscar(id) retorna null quando o id passado não é encontrado.
        Quando o id é encontrado, o método retorna o elemento e o remove da lista de atendimentos.
        Quando a lista não possui elementos, uma mensagem informa o usuário que a lista está vazia.
         */
    }

    public static void testaIncluirAtendimentosEncerrados(ListaDeAtendimentosEncerrados encerrados, Atendimento atendimento1, Atendimento atendimento2, Atendimento atendimento3, Atendimento atendimento4) {
        System.out.println("\nIncluindo atendimentos na lista de encerrados ...");
        // insere os clientes que tiveram seu atendimento encerrado
        encerrados.incluir(atendimento2);
        encerrados.incluir(atendimento4);
        encerrados.incluir(atendimento1);
        encerrados.incluir(atendimento3);

        // exibe clientes que encerraram seu atendimento
        encerrados.print();
    }

    public static void testaBuscarAtendimentosEncerrados(ListaDeAtendimentosEncerrados encerrados) {
        System.out.println("\nBuscando por atendimentos encerrados ...");
        // exibe clientes específicos da lista de clientes com atendimentos encerrados
        System.out.println(encerrados.buscar(1));
        System.out.println(encerrados.buscar(2));
        System.out.println(encerrados.buscar(3));
        System.out.println(encerrados.buscar(4));
        System.out.println(encerrados.buscar(5));// retorna null quando id não existe
    }

    public static void testaCalculaTempoMedioDeAtendimento(ListaDeAtendimentosEncerrados encerrados, Calendar horaAtendimento, TipoAssunto tipoA, TipoAssunto tipoB, TipoAssunto tipoC, TipoAssunto tipoD, TipoAssunto tipoE) {
        System.out.println("\nCalculando tempo médio de atendimento para os 5 tipos da lista de encerrados. Hora de atendimento utilizada: " + horaAtendimento.getTime());
        // gera estatísticas para cada tipo de atendimento, para uma determinada data
        System.out.println(encerrados.calculaTempoMedioDeAtendimento(tipoA, horaAtendimento));// 16.5 = 66 / 4
        System.out.println(encerrados.calculaTempoMedioDeAtendimento(tipoB, horaAtendimento));// 34 = 34 / 1
        System.out.println(encerrados.calculaTempoMedioDeAtendimento(tipoC, horaAtendimento));// 5 = 15 / 3
        System.out.println(encerrados.calculaTempoMedioDeAtendimento(tipoD, horaAtendimento));// 57 = 114 / 2
        System.out.println(encerrados.calculaTempoMedioDeAtendimento(tipoE, horaAtendimento));// 48 = 96 / 2
    }

    public static void testaInsereHeap(Heap heap, Atendimento atendimento1, Atendimento atendimento2, Atendimento atendimento3, Atendimento atendimento4) {
        System.out.println("\nInserindo atendimentos na lista de prioridades ...");
        // insere os clientes na fila de prioridades para aguardar o momento do atendimento
        heap.insert(atendimento1);
        heap.insert(atendimento2);
        heap.insert(atendimento3);
        heap.insert(atendimento4);

        heap.print();
    }

    public static void testaRemoveHeap(Heap heap, Calendar horaAtendimento) {
        System.out.println("\nRemovendo os atendimentos de maior prioridade da lista (um por um) ...");
        // remove os clientes do heap para serem atendidos
        Atendimento atendimento = heap.remove(horaAtendimento);
        System.out.println("Atendimento de maior prioridade do momento: " + atendimento.getCliente().toString());
        heap.print();

        atendimento = heap.remove(horaAtendimento);
        System.out.println("Atendimento de maior prioridade do momento: " + atendimento.getCliente().toString());
        heap.print();

        atendimento = heap.remove(horaAtendimento);
        System.out.println("Atendimento de maior prioridade do momento: " + atendimento.getCliente().toString());
        heap.print();

        atendimento = heap.remove(horaAtendimento);
        System.out.println("Atendimento de maior prioridade do momento: " + atendimento.getCliente().toString());
        heap.print();
    }
}
