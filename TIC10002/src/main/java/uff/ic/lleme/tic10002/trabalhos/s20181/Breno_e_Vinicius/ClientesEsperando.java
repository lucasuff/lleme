/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.ic.lleme.tic10002.trabalhos.s20181.Breno_e_Vinicius;

import uff.ic.lleme.tic10002.trabalhos.s20181.Breno_e_Vinicius.model.Assuntos;
import uff.ic.lleme.tic10002.trabalhos.s20181.Breno_e_Vinicius.model.Cliente;
import java.lang.reflect.*;
import java.time.*;
import java.util.*;
import java.util.stream.*;

/**
 *
 * @author Breno
 */
public class ClientesEsperando {

    public Assuntos ass;
    public String ident = "    ";
    int n = 0;
    int nmax = 300;
    public Cliente[] heap = new Cliente[nmax];

    LocalTime horaDIA;

    public ClientesEsperando(Assuntos ass) {
        this.ass = ass;
    }

//##############################################################################
    // HEAP
    public void recepcionar(String[] newclient, LocalTime horaAtual) {
        System.out.println(ident + "Novo Cliente:");
        //horaDIA = horaAtual;
        if (n < heap.length - 1) {
            heap[n] = new Cliente(newclient);
            heap[n].prioridade = calcprior(heap[n], horaAtual);
            subir(n);
            inspect(heap[n]);
            n++;
        } else
            System.out.print("Exceeded Dimension");
    }

    private void subir(int i) {
        int pai = (i - 1) / 2;
        if (pai >= 0) {
            heap[pai].prioridade = calcprior(heap[pai], horaDIA);
            if (heap[i].prioridade > heap[pai].prioridade) {
                trocar(i, pai);
                subir(pai);
            }
        }
    }

    private void descer(int i) {
        int filho = 2 * i + 1;
        if (filho < n - 1) {
            if (filho < (n - 2))// Tem filho da esquerda
                heap[filho].prioridade = calcprior(heap[filho], horaDIA);
            heap[filho + 1].prioridade = calcprior(heap[filho + 1], horaDIA);
            if (heap[filho].prioridade < heap[filho + 1].prioridade)
                filho = filho + 1;
            if (heap[i].prioridade < heap[filho].prioridade) {
                trocar(i, filho);
                descer(filho);
            }
        }
    }

    public void trocar(int i, int j) {
        Cliente aux = heap[i];
        heap[i] = heap[j];
        heap[j] = aux;
    }

    public Cliente remover() {
        Cliente atendido = heap[0];
        //System.out.println(n);
        heap[0] = heap[n - 1];
        heap[n - 1] = null;
        n--;
        descer(0);
        return atendido;
    }

    public void checkPrioridade() {
        for (int i = 0; i < n; i++)
            System.err.println("Prioridade: " + heap[i].nome + " " + heap[i].prioridade);
    }

    public double calcprior(Cliente cliente, LocalTime horaAtual) {
        double idadeM = cliente.idade / (65.0);
        double esperaM = (horaAtual.toSecondOfDay() - cliente.chegada.toSecondOfDay()) / (15 * 60.0);
        double assuntosM = getMeanAssuntos(cliente.assuntos);
        return (idadeM + esperaM + assuntosM) / 3;
    }

    public double getMeanAssuntos(String[] Assuntos) {
        int tamAss = Assuntos.length;
        int sumAss = 0;
        for (String temp : Assuntos)
            sumAss += ass.urgencia[Integer.parseInt(temp.replaceAll("\\D+", ""))];
        return sumAss / (Assuntos.length * 10.0);
    }

    public Cliente Atendimento(LocalTime horaAtual) {
        Cliente saiCliente = remover();
        horaDIA = horaAtual;
        int n1 = n + 1;
        System.out.println(ident + "A atual lista de espera contem " + n1 + " clientes");
        System.out.println(ident + "Proximo Cliente Atendido (horario " + horaDIA + "):");
        //int[] tempoAss = simAtendimento(saiCliente.assuntos);
        saiCliente.tempoAssuntos = simAtendimento(saiCliente.assuntos);
        saiCliente.providencia = simProvidencia(saiCliente.assuntos);
        horaDIA = horaDIA.plusMinutes(simAtendimentosoma(saiCliente.tempoAssuntos));
        saiCliente.saida = horaDIA;
        return saiCliente;
    }

    public int[] simAtendimento(String[] assuntos) {
        return IntStream.generate(() -> new Random().nextInt(7)).limit(assuntos.length).toArray();
    }

    public int simAtendimentosoma(int[] tempo_atendimento) {
        int i2 = 0;
        for (int i : tempo_atendimento)
            i2 += i;
        return i2;
    }

    public String[] simProvidencia(String[] assuntos) {
        String[] providencia = new String[assuntos.length];
        for (int i = 0; i < assuntos.length; i++)
            providencia[i] = ass.providencias[new Random().nextInt(ass.providencias.length)];
        return providencia;
    }

    //imprimir valores e instancias das classes
    static void inspect(Cliente obj) {
        Class<?> c = obj.getClass();
        Field[] fields = c.getDeclaredFields();
        for (Field field : fields)
            try {
                if (field.get(obj) instanceof String[]) {
                    String[] strArray = (String[]) field.get(obj);
                    System.out.println("           " + field.getName().toString() + ": " + Arrays.toString(strArray));
                } else if (field.getName().toString() == "this$0"
                        || field.getName().toString() == "tempoAssuntos"
                        || field.getName().toString() == "procedimentos"
                        || field.getName().toString() == "saida") {
                } else
                    System.out.println("           " + field.getName().toString() + ": " + field.get(obj));
            } catch (IllegalArgumentException | IllegalAccessException e1) {
            }
    }

}
