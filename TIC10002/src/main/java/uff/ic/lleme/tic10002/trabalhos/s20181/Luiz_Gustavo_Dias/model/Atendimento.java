package uff.ic.lleme.tic10002.trabalhos.s20181.Luiz_Gustavo_Dias.model;

public class Atendimento {

    public Cliente cliente;
    public ListaAssuntos assuntos;
    public int horaChegada;
    public int horaAtendimento;
    public int duracaoAtendimento;
    public float urgencia;
    public Atendimento proximo;

    public Atendimento(Cliente cliente, ListaAssuntos assuntos, int horaChegada, int horaAtendimento, Atendimento proximo) {
        this.cliente = cliente;
        this.assuntos = assuntos;
        this.horaChegada = horaChegada;
        this.horaAtendimento = horaAtendimento;
        this.duracaoAtendimento = 0;
        this.urgencia = 0;
        this.proximo = proximo;
    }
}
