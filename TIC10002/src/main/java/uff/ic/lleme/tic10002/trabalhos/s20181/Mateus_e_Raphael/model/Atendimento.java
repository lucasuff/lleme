package uff.ic.lleme.tic10002.trabalhos.s20181.Mateus_e_Raphael.model;

import uff.ic.lleme.tic10002.trabalhos.s20181.Mateus_e_Raphael.model.Cliente;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import uff.ic.lleme.tic10002.trabalhos.s20181.Mateus_e_Raphael.ListaAssuntos;

/**
 *
 * @author mateu
 */
public class Atendimento {

    private Cliente cliente;
    private ListaAssuntos assuntos;
    private float prioridadeCliente;
    private Instant horaChegada;
    private Instant horaAtendimento;

    public Atendimento(Cliente cliente, ListaAssuntos assuntos, Instant horaChegada) {
        this.cliente = cliente;
        this.assuntos = assuntos;
        this.horaChegada = horaChegada;
        this.calculaPrioridade(true);
    }

    public Atendimento(Cliente cliente, ListaAssuntos assuntos, int prioridadeCliente,
            Instant horaChegada, Instant horaAtendimento) {
        this.cliente = cliente;
        this.assuntos = assuntos;
        this.prioridadeCliente = prioridadeCliente;
        this.horaChegada = horaChegada;
        this.horaAtendimento = horaAtendimento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ListaAssuntos getAssuntos() {
        return assuntos;
    }

    public void setAssuntos(ListaAssuntos assuntos) {
        this.assuntos = assuntos;
    }

    public float getPrioridadeCliente() {
        return prioridadeCliente;
    }

    public void setPrioridadeCliente(int prioridadeCliente) {
        this.prioridadeCliente = prioridadeCliente;
    }

    public Instant getHoraChegada() {
        return horaChegada;
    }

    public void setHoraChegada(Instant horaChegada) {
        this.horaChegada = horaChegada;
    }

    public Instant getHoraAtendimento() {
        return horaAtendimento;
    }

    public void setHoraAtendimento(Instant horaAtendimento) {
        this.horaAtendimento = horaAtendimento;
    }

    public void calculaPrioridade(boolean recalcula) {
        long aux = ChronoUnit.MINUTES.between(this.horaChegada, Instant.now());
        if (recalcula == false)
            this.prioridadeCliente = (((float) (cliente.getIdade()) / 65) + ((float) (aux) / 15) + ((float) (this.assuntos.getMediaAssuntos()) / 10)) / 3;
        else
            this.prioridadeCliente = (((float) (cliente.getIdade()) / 65) + ((float) (aux) / 15) + ((float) (this.assuntos.calculaMediaAssuntos() / 10))) / 3;
    }

    public void calculaPrioridade(Instant agora) {
        long aux = ChronoUnit.MINUTES.between(this.horaChegada, agora);
        this.prioridadeCliente = (((float) (cliente.getIdade()) / 65) + ((float) (aux) / 15) + ((float) (this.assuntos.getMediaAssuntos()) / 10)) / 3;
    }
}
