/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.ic.lleme.tic10002.trabalhos.s20181.Romulo_e_Lucas.model;

import uff.ic.lleme.tic10002.trabalhos.s20181.Romulo_e_Lucas.model.Cliente;
import uff.ic.lleme.tic10002.trabalhos.s20181.Romulo_e_Lucas.model.Atendimento;
import uff.ic.lleme.tic10002.trabalhos.s20181.Romulo_e_Lucas.model.Assunto;
import java.util.Date;
import uff.ic.lleme.tic10002.trabalhos.s20181.Romulo_e_Lucas.HashAtendimento;
import uff.ic.lleme.tic10002.trabalhos.s20181.Romulo_e_Lucas.HeapAssunto;
import uff.ic.lleme.tic10002.trabalhos.s20181.Romulo_e_Lucas.HeapAtendimento;
import uff.ic.lleme.tic10002.trabalhos.s20181.Romulo_e_Lucas.ListaEstaticaNaoOrdenadaDeAtendimento;
import uff.ic.lleme.tic10002.trabalhos.s20181.Romulo_e_Lucas.ParametrosGlobais;

public class Servico {

    HeapAtendimento listaAtendimentosAbertos = new HeapAtendimento();
    HashAtendimento tabelaDeAtendimentosFechados = new HashAtendimento();

    public void recepcionar(Cliente c, HeapAssunto assuntos) {
        Atendimento a = new Atendimento(c, assuntos, new Date());
        listaAtendimentosAbertos.incluir(a, a.calcularPrioridade());
        System.out.print("\nCliente recepcionado. Heap: ");
        listaAtendimentosAbertos.printHeapAtendimento();
    }

    public Atendimento atender() throws InterruptedException {
        Atendimento momento = listaAtendimentosAbertos.remover();
        momento.setHoraAtendimento(new Date());
        System.out.println("\nCliente " + momento.getCliente().getNome() + " iniciou atendimento.");
        // atender todos os assuntos do cliente por maior urgência
        // e setar os assuntos atendimentos com suas providências e durações
        momento.setAssuntos(atenderAssuntos(momento));
        return momento;
    }

    public void encerrar(Atendimento a) {
        this.tabelaDeAtendimentosFechados.armazenar(a);
        System.out.print("\nCliente encerrou atendimento. Heap: ");
        listaAtendimentosAbertos.printHeapAtendimento();
    }

    public void gerarEstatistica() {
        ListaEstaticaNaoOrdenadaDeAtendimento listaDoDia = this.tabelaDeAtendimentosFechados.acessar(new Date());
        // variáveis de soma por tipoAssunto
        float somaTipo0 = 0;
        int qtdTipo0 = 0;
        float somaTipo1 = 0;
        int qtdTipo1 = 0;
        float somaTipo2 = 0;
        int qtdTipo2 = 0;
        float somaTipo3 = 0;
        int qtdTipo3 = 0;
        float somaTipo4 = 0;
        int qtdTipo4 = 0;
        float somaTipo5 = 0;
        int qtdTipo5 = 0;
        float somaTipo6 = 0;
        int qtdTipo6 = 0;
        float somaTipo7 = 0;
        int qtdTipo7 = 0;
        float somaTipo8 = 0;
        int qtdTipo8 = 0;
        float somaTipo9 = 0;
        int qtdTipo9 = 0;
        float somaTipo10 = 0;
        int qtdTipo10 = 0;
        ParametrosGlobais param = new ParametrosGlobais();
        // interar lista de atendimentos encerrados no dia
        for (int i = 0; i < listaDoDia.getTamanho(); i++) {
            Assunto a = null;
            HeapAssunto assuntos = listaDoDia.acessar(i).getAssuntos();
            for (int j = 0; j < assuntos.getTamanho(); j++) {
                a = assuntos.acessar(j);
                if (a.getTipoAssunto().getTipo() == param.t0.getTipo()) {
                    somaTipo0 += (float) a.getDuracaoAtendimento();
                    qtdTipo0++;
                } else if (a.getTipoAssunto().getTipo() == param.t1.getTipo()) {
                    somaTipo1 += (float) a.getDuracaoAtendimento();
                    qtdTipo1++;
                } else if (a.getTipoAssunto().getTipo() == param.t2.getTipo()) {
                    somaTipo2 += (float) a.getDuracaoAtendimento();
                    qtdTipo2++;
                } else if (a.getTipoAssunto().getTipo() == param.t3.getTipo()) {
                    somaTipo3 += (float) a.getDuracaoAtendimento();
                    qtdTipo3++;
                } else if (a.getTipoAssunto().getTipo() == param.t4.getTipo()) {
                    somaTipo4 += (float) a.getDuracaoAtendimento();
                    qtdTipo4++;
                } else if (a.getTipoAssunto().getTipo() == param.t5.getTipo()) {
                    somaTipo5 += (float) a.getDuracaoAtendimento();
                    qtdTipo5++;
                } else if (a.getTipoAssunto().getTipo() == param.t6.getTipo()) {
                    somaTipo6 += (float) a.getDuracaoAtendimento();
                    qtdTipo6++;
                } else if (a.getTipoAssunto().getTipo() == param.t7.getTipo()) {
                    somaTipo7 += (float) a.getDuracaoAtendimento();
                    qtdTipo7++;
                } else if (a.getTipoAssunto().getTipo() == param.t8.getTipo()) {
                    somaTipo8 += (float) a.getDuracaoAtendimento();
                    qtdTipo8++;
                } else if (a.getTipoAssunto().getTipo() == param.t9.getTipo()) {
                    somaTipo9 += (float) a.getDuracaoAtendimento();
                    qtdTipo9++;
                } else if (a.getTipoAssunto().getTipo() == param.t10.getTipo()) {
                    somaTipo10 += (float) a.getDuracaoAtendimento();
                    qtdTipo10++;
                }
            }
        }

        System.out.println("Média do tipoAssunto0: " + somaTipo0 / qtdTipo0);
        System.out.println("Média do tipoAssunto1: " + somaTipo1 / qtdTipo1);
        System.out.println("Média do tipoAssunto2: " + somaTipo2 / qtdTipo2);
        System.out.println("Média do tipoAssunto3: " + somaTipo3 / qtdTipo3);
        System.out.println("Média do tipoAssunto4: " + somaTipo4 / qtdTipo4);
        System.out.println("Média do tipoAssunto5: " + somaTipo5 / qtdTipo5);
        System.out.println("Média do tipoAssunto6: " + somaTipo6 / qtdTipo6);
        System.out.println("Média do tipoAssunto7: " + somaTipo7 / qtdTipo7);
        System.out.println("Média do tipoAssunto8: " + somaTipo8 / qtdTipo8);
        System.out.println("Média do tipoAssunto9: " + somaTipo9 / qtdTipo9);
        System.out.println("Média do tipoAssunto10: " + somaTipo10 / qtdTipo10);
    }

    private HeapAssunto atenderAssuntos(Atendimento momento) throws InterruptedException {
        HeapAssunto heapAssuntosAtendidos = new HeapAssunto();
        Assunto assuntoAtendido = null;
        ParametrosGlobais param = new ParametrosGlobais();
        // 'for' foi utilizado para diminuir tamanho do código e passar a ideia de que
        // seria chamado o assunto por vez, a medida que fosse desejado
        int tamanhoInicial = momento.assuntos.getTamanho();
        for (int i = 0; i < tamanhoInicial; i++) {
            Thread.sleep(param.espera2min);
            // pegar assunto de maior urgência
            assuntoAtendido = ((Assunto) momento.assuntos.remover());
            assuntoAtendido.setProvidencia("Providência x tomada");
            assuntoAtendido.calcularDuracaoAtendimento(momento.horaAtendimento);
            // incluir o assunto já atendido em uma lista a parte.
            // Se inserisse no momento de uma vez, entraria em loop pois estaria
            // removendo e inserindo em cada iteração
            heapAssuntosAtendidos.incluir(assuntoAtendido);
        }
        return heapAssuntosAtendidos;
    }
}
