/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.ic.lleme.tic10002.trabalhos.s20181.LuizAntonio_e_CarlosMuniz.model;

import java.util.Calendar;

/**
 *
 * @author Luiz Antonio
 */
public class Atendimento {

    private Cliente cliente;
    private Assunto[] assuntos;
    private Calendar horaChegada;
    private Calendar horaAtendimento;

    public Atendimento(Cliente cliente, Assunto[] assuntos, Calendar horaChegada) {
        this.cliente = cliente;
        this.assuntos = assuntos;
        this.horaChegada = horaChegada;
        this.horaAtendimento = null;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Assunto[] getAssuntos() {
        return this.assuntos;
    }

    public void setAssuntos(Assunto[] assuntos) {
        this.assuntos = assuntos;
    }

    public Calendar getHoraChegada() {
        return this.horaChegada;
    }

    public void setHoraChegada(Calendar horaChegada) {
        this.horaChegada = horaChegada;
    }

    public Calendar getHoraAtendimento() {
        return this.horaAtendimento;
    }

    public void setHoraAtendimento(Calendar horaAtendimento) {
        this.horaAtendimento = horaAtendimento;
    }

    @Override
    public String toString() {
        String result = "{Informações do Atendimento\n" + this.cliente.toString() + ",\nHora da Chegada: " + this.horaChegada.getTime() + ",\nHora do Atendimento: " + this.horaAtendimento.getTime() + ",\n";
        for (Assunto assunto : this.assuntos)
            result = result + assunto.toString();
        result = result + "}";
        return result;
    }
}
