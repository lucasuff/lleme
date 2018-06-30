/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.ic.lleme.tic10002.trabalhos.s20181.PabloMoreira.trabalhoed;

import java.util.Date;

/**
 *
 * @author pablomoreira
 */
public class Atendimento {

    public final static int MAX_ASSUNTOS = 10;
    private Cliente cliente;
    private Assunto[] assunto;
    private Date horaChegada;
    private Date horaAtendimento;

    public Atendimento(Cliente cliente, Assunto[] assunto, Date horaChegada, Date horaAtendimento) {
        this.cliente = cliente;
        this.assunto = assunto;
        this.horaChegada = horaChegada;
        this.horaAtendimento = horaAtendimento;
    }

    public Atendimento(Cliente cliente, Assunto[] assunto) {
        this.cliente = cliente;
        this.assunto = assunto;
        this.horaChegada = horaChegada;
        this.horaAtendimento = horaAtendimento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Assunto[] getAssunto() {
        return assunto;
    }

    public void setAssunto(Assunto[] assunto) {
        this.assunto = assunto;
    }

    public Date getHoraChegada() {
        return horaChegada;
    }

    public void setHoraChegada(Date horaChegada) {
        this.horaChegada = horaChegada;
    }

    public Date getHoraAtendimento() {
        return horaAtendimento;
    }

    public void setHoraAtendimento(Date horaAtendimento) {
        this.horaAtendimento = horaAtendimento;
    }

}
