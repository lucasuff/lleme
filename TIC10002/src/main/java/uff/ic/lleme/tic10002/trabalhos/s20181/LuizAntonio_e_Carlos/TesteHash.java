/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.ic.lleme.tic10002.trabalhos.s20181.LuizAntonio_e_Carlos;

import uff.ic.lleme.tic10002.trabalhos.s20181.LuizAntonio_e_Carlos.model.TipoAssunto;
import uff.ic.lleme.tic10002.trabalhos.s20181.LuizAntonio_e_Carlos.model.Atendimento;
import uff.ic.lleme.tic10002.trabalhos.s20181.LuizAntonio_e_Carlos.model.Assunto;
import uff.ic.lleme.tic10002.trabalhos.s20181.LuizAntonio_e_Carlos.model.Cliente;
import java.util.Calendar;

/**
 *
 * @author Carlos Muniz
 */
public class TesteHash {

    public static void main(String[] args) {

        // cria clientes com id, nome, idade
        Cliente cliente1 = new Cliente(1, "John", 40);
        Cliente cliente2 = new Cliente(2, "Paul", 75);
        Cliente cliente3 = new Cliente(3, "George", 58);
        Cliente cliente4 = new Cliente(4, "Ringo", 77);

        // cria horarios de chegada
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

        // cria horarios de atendimento
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
        Assunto assunto1 = new Assunto(tipoA, "Assunto 1");// urgencia 7
        assunto1.setDuracaoAtendimento(12);

        Assunto assunto2 = new Assunto(tipoA, "Assunto 2");// urgencia 7
        assunto2.setDuracaoAtendimento(21);

        Assunto assunto3 = new Assunto(tipoB, "Assunto 3");// urgencia 7
        assunto3.setDuracaoAtendimento(34);

        Assunto assunto4 = new Assunto(tipoC, "Assunto 4");// urgencia 3
        assunto4.setDuracaoAtendimento(5);

        Assunto assunto5 = new Assunto(tipoD, "Assunto 5");// urgencia 10
        assunto5.setDuracaoAtendimento(57);

        Assunto assunto6 = new Assunto(tipoE, "Assunto 6");// urgencia 4
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
        ServicoDeAtendimento.recepcionar(cliente1, assuntos1);
        ServicoDeAtendimento.recepcionar(cliente2, assuntos2);
        ServicoDeAtendimento.recepcionar(cliente3, assuntos3);
        ServicoDeAtendimento.recepcionar(cliente4, assuntos4);

        // insere os clientes que estao sendo atendidos
        Atendimento atendimento1 = ServicoDeAtendimento.atender();
        Atendimento atendimento2 = ServicoDeAtendimento.atender();
        Atendimento atendimento3 = ServicoDeAtendimento.atender();
        Atendimento atendimento4 = ServicoDeAtendimento.atender();

        // exibe os clientes que estão sendo atendidos
        ServicoDeAtendimento.atendimentos.print();

        // insere os clientes que tiveram seu atendimento encerrado
        ServicoDeAtendimento.encerrar(1);
        ServicoDeAtendimento.encerrar(2);
        ServicoDeAtendimento.encerrar(3);
        ServicoDeAtendimento.encerrar(4);

        // exibe clientes que encerraram seu atendimento
        ServicoDeAtendimento.encerrados.print();

        // exibe clientes especificos da lista de clientes com atendimentos encerrados
        System.out.println("Teste: encerrados.buscar(1) " + ServicoDeAtendimento.encerrados.buscar(1));
        System.out.println("Teste: encerrados.buscar(2) " + ServicoDeAtendimento.encerrados.buscar(2));
        System.out.println("Teste: encerrados.buscar(3) " + ServicoDeAtendimento.encerrados.buscar(3));
        System.out.println("Teste: encerrados.buscar(4) " + ServicoDeAtendimento.encerrados.buscar(4));
        System.out.println("Teste: encerrados.buscar(5) " + ServicoDeAtendimento.encerrados.buscar(5));

        ServicoDeAtendimento.heap.print();

        // cria um horario de atendimento que e posterior a todos os horarios de chegada dos clientes
        Calendar horaAtendimento = Calendar.getInstance();
        horaAtendimento.set(Calendar.HOUR_OF_DAY, 20);
        horaAtendimento.set(Calendar.MINUTE, 27);
        horaAtendimento.set(Calendar.SECOND, 00);

        // gera estatisticas para cada tipo de atendimento, para uma determinada data
        System.out.println("Teste: gerarEstatistica(tipoA) " + ServicoDeAtendimento.gerarEstatistica(tipoA));// 16.5 = 66 / 4
        System.out.println("Teste: gerarEstatistica(tipoB) " + ServicoDeAtendimento.gerarEstatistica(tipoB));// 34 = 34 / 1
        System.out.println("Teste: gerarEstatistica(tipoC) " + ServicoDeAtendimento.gerarEstatistica(tipoC));// 5 = 15 / 3
        System.out.println("Teste: gerarEstatistica(tipoD) " + ServicoDeAtendimento.gerarEstatistica(tipoD));// 57 = 114 / 2
        System.out.println("Teste: gerarEstatistica(tipoE) " + ServicoDeAtendimento.gerarEstatistica(tipoE));// 48 = 96 / 2

        System.out.println("Fim da execucao");

    }

}
