package uff.ic.lleme.tic10002.trabalhos.s20181.Lucas_e_Erick.model;

import java.time.LocalDateTime;

public class Atendimento {

    private Cliente cliente;
    private Assunto assunto;
    private LocalDateTime dataChegada;
    private LocalDateTime dataAtendimento;

    public Atendimento(Cliente cliente, Assunto assunto, LocalDateTime dataChegada, LocalDateTime dataAtendimento) {
        this.cliente = cliente;
        this.assunto = assunto;
        this.dataChegada = dataChegada;
        this.dataAtendimento = dataAtendimento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Assunto getAssunto() {
        return assunto;
    }

    public void setAssunto(Assunto assunto) {
        this.assunto = assunto;
    }

    public LocalDateTime getDataChegada() {
        return dataChegada;
    }

    public void setDataChegada(LocalDateTime dataChegada) {
        this.dataChegada = dataChegada;
    }

    public LocalDateTime getDataAtendimento() {
        return dataAtendimento;
    }

    public void setDataAtendimento(LocalDateTime dataAtendimento) {
        this.dataAtendimento = dataAtendimento;
    }

}
