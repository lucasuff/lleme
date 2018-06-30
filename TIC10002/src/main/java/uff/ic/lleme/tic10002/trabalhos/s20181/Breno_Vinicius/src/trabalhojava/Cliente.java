/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhojava;

import java.time.LocalTime;

/**
 *
 * @author Breno
 */
public class Cliente {

    String nome;
    String id;
    String dia;
    int idade;
    String[] assuntos;
    LocalTime chegada = LocalTime.parse("00:00:00");
    //int pos;
    double prioridade;
    LocalTime saida = LocalTime.parse("00:00:00");
    int[] tempoAssuntos;
    String[] providencia;
    long idnum;

    public Cliente(String[] novocliente) {
        //Heap newclient = new Heap();
        //System.out.println(ident+"Novo Cliente:");
        //Cliente newclient = new Cliente();
        this.chegada = LocalTime.parse(novocliente[0]);
        this.id = novocliente[1];
        this.nome = novocliente[2].replaceAll(" ", "");
        this.idade = Integer.valueOf(novocliente[3].replaceAll("\\D+", ""));
        this.assuntos = novocliente[4].split(",");
        this.idnum = Long.parseLong(novocliente[1].replaceAll("[^0-9]+", ""));
        //this.prioridade = calcprior(newclient, horaAtual);
        //inspect(newclient);
    }

    public Cliente(Cliente cliAtendido, LocalTime finalAtendimento, String[] proced) {
        this.chegada = cliAtendido.chegada;
        this.id = cliAtendido.id;
        this.nome = cliAtendido.nome;
        this.idade = cliAtendido.idade;
        this.assuntos = cliAtendido.assuntos;
        this.prioridade = cliAtendido.prioridade;
        this.saida = finalAtendimento;
        this.providencia = proced;
        //System.out.println("idnum: " + Integer.parseInt(cliAtendido.id.replaceAll("[^0-9]", "")));
        //this.idnum = Long.parseLong(cliAtendido.id.replaceAll("[^0-9]+", ""));
    }
}
