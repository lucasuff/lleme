/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.ic.lleme.tic10002.trabalhos.s20181.Breno_Vinicius.src.trabalhojava.model;

import java.time.LocalTime;

/**
 *
 * @author Breno
 */
public class Cliente {

    public String nome;
    public String id;
    public String dia;
    public int idade;
    public String[] assuntos;
    public LocalTime chegada = LocalTime.parse("00:00:00");
    //int pos;
    public double prioridade;
    public LocalTime saida = LocalTime.parse("00:00:00");
    public int[] tempoAssuntos;
    public String[] providencia;
    public long idnum;

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
