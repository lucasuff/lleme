/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.ic.lleme.tic10002.trabalhos.s20181.LuizAntonio_e_CarlosMuniz;

import uff.ic.lleme.tic10002.trabalhos.s20181.LuizAntonio_e_CarlosMuniz.model.TipoAssunto;
import uff.ic.lleme.tic10002.trabalhos.s20181.LuizAntonio_e_CarlosMuniz.model.Assunto;
import uff.ic.lleme.tic10002.trabalhos.s20181.LuizAntonio_e_CarlosMuniz.model.Cliente;

/**
 *
 * @author Luiz Antonio
 */
public class TestServicoDeAtendimento {

    public static void main(String[] args) {
        ServicoDeAtendimento service = new ServicoDeAtendimento();

        Cliente john = new Cliente(1, "John", 40);
        Cliente paul = new Cliente(2, "Paul", 75);

        TipoAssunto guitar = new TipoAssunto("Guitarra", "Instrumento de 6 cordas", 8);
        TipoAssunto bass = new TipoAssunto("Contrabaixo", "Instrumento de 4 cordas", 6);
        TipoAssunto drum = new TipoAssunto("Bateria", "Instrumento de percursão", 7);

        Assunto assunto1 = new Assunto(bass, "Assunto 1");
        assunto1.setDuracaoAtendimento(12);

        Assunto assunto2 = new Assunto(bass, "Assunto 2");
        assunto2.setDuracaoAtendimento(21);

        Assunto assunto3 = new Assunto(bass, "Assunto 3");
        assunto3.setDuracaoAtendimento(34);

        Assunto assunto4 = new Assunto(bass, "Assunto 4");
        assunto4.setDuracaoAtendimento(5);

        Assunto assunto5 = new Assunto(bass, "Assunto 5");
        assunto5.setDuracaoAtendimento(57);

        Assunto assunto6 = new Assunto(bass, "Assunto 6");
        assunto6.setDuracaoAtendimento(48);

        Assunto assunto7 = new Assunto(guitar, "Assunto 7");
        assunto3.setDuracaoAtendimento(45);

        Assunto assunto8 = new Assunto(guitar, "Assunto 8");
        assunto4.setDuracaoAtendimento(20);

        Assunto[] assuntosJohn = new Assunto[2];
        assuntosJohn[0] = assunto7;
        assuntosJohn[1] = assunto8;

        Assunto[] assuntosPaul = new Assunto[6];
        assuntosPaul[0] = assunto1;
        assuntosPaul[1] = assunto2;
        assuntosPaul[2] = assunto3;
        assuntosPaul[3] = assunto4;
        assuntosPaul[4] = assunto5;
        assuntosPaul[5] = assunto6;

        System.out.println("Recepcionando John ...");
        service.recepcionar(john, assuntosJohn);
        System.out.println("Recepcionando Paul ...");
        service.recepcionar(paul, assuntosPaul);

        System.out.println("\nAtendendo o Paul ...");
        service.atender();
        System.out.println("Atendendo o John ...");
        service.atender();

        System.out.println("\nEncerrando o atendimento do John ...");
        service.encerrar(1);
        System.out.println("Encerrando o atendimento do Paul ...");
        service.encerrar(2);

        System.out.println("\nGerando estatística para Contrabaixo: " + service.gerarEstatistica(bass));
        System.out.println("Gerando estatística para Guitarra: " + service.gerarEstatistica(guitar));
        System.out.println("Gerando estatística para Bateria: " + service.gerarEstatistica(drum));

        System.out.println("\nFim dos testes");
    }

}
