package uff.ic.lleme.tic10002.trabalhos.s20181.Romulo_e_Lucas.src.ed.trabalho20181;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Atendimento {

    Cliente cliente;
    HeapAssunto assuntos;
    Date horaChegada;
    Date horaAtendimento;

    public HeapAssunto getAssuntos() {
        return assuntos;
    }

    public void setAssuntos(HeapAssunto assuntos) {
        this.assuntos = assuntos;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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

    public Atendimento(Cliente cliente, HeapAssunto assuntos, Date horaChegada) {
        this.cliente = cliente;
        this.assuntos = assuntos;
        this.horaChegada = horaChegada;
    }

    public float calcularPrioridade() {
        int idade = this.cliente.getIdade();
        // diminuindo o agora com o a hora de chegada e convertendo para minutos
        long esperaEmMinutos = TimeUnit.MILLISECONDS.toMinutes(((new Date()).getTime() - this.getHoraChegada().getTime()));
        float mediaUrgencia = this.calcularMediaUrgenciaAssuntos(this.assuntos);
        return (((idade / 65) + ((float) esperaEmMinutos / 15) + (mediaUrgencia)) / 3);
    }

    private float calcularMediaUrgenciaAssuntos(HeapAssunto assuntos) {
        float somaUrgencias = 0;
        // somar todas as urgências dos assuntos na lista para tirar a média
        for (int i = 0; i < assuntos.getTamanho(); i++)
            somaUrgencias += assuntos.acessar(i).getTipoAssunto().getUrgencia();
        somaUrgencias = somaUrgencias / assuntos.getTamanho();
        return (somaUrgencias / 10);
    }
}
