package br.uff.ed.main;

import br.uff.ed.colecao.Lista;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;

@SuppressWarnings("rawtypes")
public class Cliente implements Comparable, Priorizavel {

    private int cpf;
    private int idade;
    private String nome;
    private Lista<Assunto> listaAssuntos;
    private LocalDateTime tempoChegada;
    private LocalDateTime tempoAtendimento;
    private double prioridade;

    public Cliente(int cpf, int idade, String nome, Lista<Assunto> listaAssuntos, LocalDateTime tempoChegada) {
        this.cpf = cpf;
        this.idade = idade;
        this.nome = nome;
        this.listaAssuntos = listaAssuntos;
        this.tempoChegada = tempoChegada;
        this.tempoAtendimento = null;
        this.prioridade = this.priorizar(tempoChegada);
    }

    public LocalDateTime getTempoChegada() {
        return tempoChegada;
    }

    public void setTempoChegada(LocalDateTime tempoChegada) {
        this.tempoChegada = tempoChegada;
    }

    public double getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(double prioridade) {
        this.prioridade = prioridade;
    }

    public Lista<Assunto> getListaAssuntos() {
        return listaAssuntos;
    }

    public void setListaAssuntos(Lista<Assunto> listaAssuntos) {
        this.listaAssuntos = listaAssuntos;
    }

    public int getCpf() {
        return cpf;
    }

    public void setCpf(int cpf) {
        this.cpf = cpf;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDateTime getTempoAtendimento() {
        return tempoAtendimento;
    }

    public void setTempoAtendimento(LocalDateTime tempoAtendimento) {
        this.tempoAtendimento = tempoAtendimento;
    }

    @Override
    public int compareTo(Object cliente) {
        if (this.prioridade < ((Cliente) cliente).prioridade)
            return -1;
        else if (this.prioridade == ((Cliente) cliente).prioridade)
            return 0;
        else
            return 1;
    }

    @Override
    public double priorizar(LocalDateTime atual) {
        double idadenormalizada = idade / 65.0;
        double temponormalizado = (Duration.between(tempoChegada, atual).toMinutes()) / 15.0;
        double mediaassuntonormalizado = 0.0;
        Assunto[] assuntos = Arrays.copyOf(this.getListaAssuntos().toArray(), this.listaAssuntos.getTamanho(),
                Assunto[].class);
        for (int i = 0; i < assuntos.length; i++)
            mediaassuntonormalizado += (double) assuntos[i].getTipoAssunto().getUrgencia().getValor();
        mediaassuntonormalizado = (mediaassuntonormalizado / listaAssuntos.getTamanho()) / 10.0;
        return prioridade = (idadenormalizada + temponormalizado + mediaassuntonormalizado) / 3;
    }

    @Override
    public LocalDateTime getPriorizador() {
        return tempoChegada;
    }
}
