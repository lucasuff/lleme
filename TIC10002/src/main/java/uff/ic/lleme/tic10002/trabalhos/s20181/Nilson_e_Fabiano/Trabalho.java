package uff.ic.lleme.tic10002.trabalhos.s20181.Nilson_e_Fabiano;

import uff.ic.lleme.tic10002.trabalhos.s20181.Nilson_e_Fabiano.model.Atendimento;
import uff.ic.lleme.tic10002.trabalhos.s20181.Nilson_e_Fabiano.model.Cliente;
import uff.ic.lleme.tic10002.trabalhos.s20181.Nilson_e_Fabiano.model.Assunto;
import java.util.Random;

/**
 *
 * Classe que cria os Tipos de Assunto e implementa as rotinas de teste.
 *
 * @author Nilson e Fabiano
 */
public class Trabalho {

    public static void main(String[] args) {

        TipoAssunto[] ta = cadastrarTabelaTipoAssunto();

        teste1(ta);
        teste2(ta);
        teste3(ta);
        teste4(ta);
        teste5(ta);
    }

    public static TipoAssunto[] cadastrarTabelaTipoAssunto() {
        TipoAssunto[] ta = new TipoAssunto[11];
        ta[0] = TiposAssunto.insere(new TipoAssunto(0, "Assunto 0", 0));
        ta[1] = TiposAssunto.insere(new TipoAssunto(1, "Assunto 1", 1));
        ta[2] = TiposAssunto.insere(new TipoAssunto(2, "Assunto 2", 2));
        ta[3] = TiposAssunto.insere(new TipoAssunto(3, "Assunto 3", 3));
        ta[4] = TiposAssunto.insere(new TipoAssunto(4, "Assunto 4", 4));
        ta[5] = TiposAssunto.insere(new TipoAssunto(5, "Assunto 5", 5));
        ta[6] = TiposAssunto.insere(new TipoAssunto(6, "Assunto 6", 6));
        ta[7] = TiposAssunto.insere(new TipoAssunto(7, "Assunto 7", 7));
        ta[8] = TiposAssunto.insere(new TipoAssunto(8, "Assunto 8", 8));
        ta[9] = TiposAssunto.insere(new TipoAssunto(9, "Assunto 9", 9));
        ta[10] = TiposAssunto.insere(new TipoAssunto(10, "Assunto 10", 10));
        return ta;

    }

    public static void teste1(TipoAssunto ta[]) {
        System.out.println("*****Teste 1*****");

        Servico servico = new Servico();
        Cliente cliente;
        Assuntos assuntos;
        Random rand = new Random();

        cliente = new Cliente(1, "Cliente1", 40);
        assuntos = new Assuntos();
        assuntos.insere(new Assunto(ta[0], "Assunto 1"));
        assuntos.insere(new Assunto(ta[1], "Assunto 2"));
        servico.recepcionar(cliente, assuntos);

        cliente = new Cliente(2, "Cliente2", 30);
        assuntos = new Assuntos();
        assuntos.insere(new Assunto(ta[2], "Assunto 1"));
        assuntos.insere(new Assunto(ta[3], "Assunto 2"));
        servico.recepcionar(cliente, assuntos);

        cliente = new Cliente(3, "Cliente3", 20);
        assuntos = new Assuntos();
        assuntos.insere(new Assunto(ta[4], "Assunto 1"));
        assuntos.insere(new Assunto(ta[5], "Assunto 2"));
        servico.recepcionar(cliente, assuntos);

        cliente = new Cliente(4, "Cliente4", 60);
        assuntos = new Assuntos();
        assuntos.insere(new Assunto(ta[6], "Assunto 1"));
        assuntos.insere(new Assunto(ta[7], "Assunto 2"));
        servico.recepcionar(cliente, assuntos);

        cliente = new Cliente(4, "Cliente5", 50);
        assuntos = new Assuntos();
        assuntos.insere(new Assunto(ta[8], "Assunto 1"));
        assuntos.insere(new Assunto(ta[9], "Assunto 2"));
        servico.recepcionar(cliente, assuntos);

        cliente = new Cliente(4, "Cliente6", 20);
        assuntos = new Assuntos();
        assuntos.insere(new Assunto(ta[10], "Assunto 1"));
        servico.recepcionar(cliente, assuntos);

        Atendimento atendimento;

        while ((atendimento = servico.atender()) != null) {
            assuntos = atendimento.getAssuntos();
            for (Assunto a = assuntos.getPrimeiro();
                    a != null; a = assuntos.getProximo()) {
                Integer d = 2 + rand.nextInt(40);
                a.registrar("Providecia " + d.toString(), d);
            }
            servico.encerrar(atendimento);
        }

        servico.gerarEstatistica();
    }

    public static void teste2(TipoAssunto[] ta) {
        System.out.println("*****Teste 2*****");

        Servico servico = new Servico();
        Cliente cliente;
        Assuntos assuntos;
        Random rand = new Random();
        Atendimento atendimento;

        cliente = new Cliente(1, "Cliente1", 40);
        assuntos = new Assuntos();
        assuntos.insere(new Assunto(ta[0], "Assunto 1"));
        assuntos.insere(new Assunto(ta[1], "Assunto 2"));
        servico.recepcionar(cliente, assuntos);

        cliente = new Cliente(2, "Cliente2", 30);
        assuntos = new Assuntos();
        assuntos.insere(new Assunto(ta[2], "Assunto 1"));
        assuntos.insere(new Assunto(ta[3], "Assunto 2"));
        servico.recepcionar(cliente, assuntos);

        atendimento = servico.atender();
        assuntos = atendimento.getAssuntos();
        for (Assunto a = assuntos.getPrimeiro();
                a != null; a = assuntos.getProximo()) {
            Integer d = 2 + rand.nextInt(40);
            a.registrar("Providecia " + d.toString(), d);
        }
        servico.encerrar(atendimento);

        cliente = new Cliente(3, "Cliente3", 20);
        assuntos = new Assuntos();
        assuntos.insere(new Assunto(ta[0], "Assunto 1"));
        assuntos.insere(new Assunto(ta[1], "Assunto 2"));
        servico.recepcionar(cliente, assuntos);

        atendimento = servico.atender();
        assuntos = atendimento.getAssuntos();
        for (Assunto a = assuntos.getPrimeiro();
                a != null; a = assuntos.getProximo()) {
            Integer d = 2 + rand.nextInt(40);
            a.registrar("Providecia " + d.toString(), d);
        }
        servico.encerrar(atendimento);

        cliente = new Cliente(4, "Cliente4", 60);
        assuntos = new Assuntos();
        assuntos.insere(new Assunto(ta[4], "Assunto 1"));
        assuntos.insere(new Assunto(ta[3], "Assunto 2"));
        servico.recepcionar(cliente, assuntos);

        cliente = new Cliente(4, "Cliente5", 50);
        assuntos = new Assuntos();
        assuntos.insere(new Assunto(ta[8], "Assunto 1"));
        assuntos.insere(new Assunto(ta[9], "Assunto 2"));
        servico.recepcionar(cliente, assuntos);

        cliente = new Cliente(4, "Cliente6", 20);
        assuntos = new Assuntos();
        assuntos.insere(new Assunto(ta[10], "Assunto 1"));
        servico.recepcionar(cliente, assuntos);

        atendimento = servico.atender();
        assuntos = atendimento.getAssuntos();
        for (Assunto a = assuntos.getPrimeiro();
                a != null; a = assuntos.getProximo()) {
            Integer d = 2 + rand.nextInt(40);
            a.registrar("Providecia " + d.toString(), d);
        }
        servico.encerrar(atendimento);

        cliente = new Cliente(5, "Cliente7", 70);
        assuntos = new Assuntos();
        assuntos.insere(new Assunto(ta[1], "Assunto 1"));
        assuntos.insere(new Assunto(ta[3], "Assunto 3"));
        servico.recepcionar(cliente, assuntos);

        atendimento = servico.atender();
        assuntos = atendimento.getAssuntos();
        for (Assunto a = assuntos.getPrimeiro();
                a != null; a = assuntos.getProximo()) {
            Integer d = 2 + rand.nextInt(40);
            a.registrar("Providecia " + d.toString(), d);
        }
        servico.encerrar(atendimento);

        atendimento = servico.atender();
        assuntos = atendimento.getAssuntos();
        for (Assunto a = assuntos.getPrimeiro();
                a != null; a = assuntos.getProximo()) {
            Integer d = 2 + rand.nextInt(40);
            a.registrar("Providecia " + d.toString(), d);
        }
        servico.encerrar(atendimento);

        cliente = new Cliente(6, "Cliente8", 30);
        assuntos = new Assuntos();
        assuntos.insere(new Assunto(ta[5], "Assunto 1"));
        servico.recepcionar(cliente, assuntos);

        cliente = new Cliente(7, "Cliente9", 80);
        assuntos = new Assuntos();
        assuntos.insere(new Assunto(ta[7], "Assunto 1"));
        assuntos.insere(new Assunto(ta[9], "Assunto 2"));
        servico.recepcionar(cliente, assuntos);

        atendimento = servico.atender();
        assuntos = atendimento.getAssuntos();
        for (Assunto a = assuntos.getPrimeiro();
                a != null; a = assuntos.getProximo()) {
            Integer d = 2 + rand.nextInt(40);
            a.registrar("Providecia " + d.toString(), d);
        }
        servico.encerrar(atendimento);

        atendimento = servico.atender();
        assuntos = atendimento.getAssuntos();
        for (Assunto a = assuntos.getPrimeiro();
                a != null; a = assuntos.getProximo()) {
            Integer d = 2 + rand.nextInt(40);
            a.registrar("Providecia " + d.toString(), d);
        }
        servico.encerrar(atendimento);

        cliente = new Cliente(7, "Cliente10", 80);
        assuntos = new Assuntos();
        assuntos.insere(new Assunto(ta[8], "Assunto 1"));
        assuntos.insere(new Assunto(ta[10], "Assunto 2"));
        servico.recepcionar(cliente, assuntos);

        atendimento = servico.atender();
        assuntos = atendimento.getAssuntos();
        for (Assunto a = assuntos.getPrimeiro();
                a != null; a = assuntos.getProximo()) {
            Integer d = 2 + rand.nextInt(40);
            a.registrar("Providecia " + d.toString(), d);
        }
        servico.encerrar(atendimento);

        atendimento = servico.atender();
        assuntos = atendimento.getAssuntos();
        for (Assunto a = assuntos.getPrimeiro();
                a != null; a = assuntos.getProximo()) {
            Integer d = 2 + rand.nextInt(40);
            a.registrar("Providecia " + d.toString(), d);
        }
        servico.encerrar(atendimento);

        atendimento = servico.atender();
        assuntos = atendimento.getAssuntos();
        for (Assunto a = assuntos.getPrimeiro();
                a != null; a = assuntos.getProximo()) {
            Integer d = 2 + rand.nextInt(40);
            a.registrar("Providecia " + d.toString(), d);
        }
        servico.encerrar(atendimento);

        servico.gerarEstatistica();
    }

    public static void teste3(TipoAssunto[] ta) {
        System.out.println("*****Teste 3*****");

        Servico servico = new Servico();
        Cliente cliente;
        Assuntos assuntos;
        Random rand = new Random();
        Atendimento atendimento;

        cliente = new Cliente(1, "Cliente1", 40);
        assuntos = new Assuntos();
        assuntos.insere(new Assunto(ta[0], "Assunto 1"));
        assuntos.insere(new Assunto(ta[1], "Assunto 2"));
        assuntos.insere(new Assunto(ta[2], "Assunto 3"));
        servico.recepcionar(cliente, assuntos);

        atendimento = servico.atender();
        assuntos = atendimento.getAssuntos();
        for (Assunto a = assuntos.getPrimeiro();
                a != null; a = assuntos.getProximo()) {
            Integer d = 2 + rand.nextInt(40);
            a.registrar("Providecia " + d.toString(), d);
        }
        servico.encerrar(atendimento);

        cliente = new Cliente(2, "Cliente2", 30);
        assuntos = new Assuntos();
        assuntos.insere(new Assunto(ta[3], "Assunto 1"));
        servico.recepcionar(cliente, assuntos);

        atendimento = servico.atender();
        assuntos = atendimento.getAssuntos();
        for (Assunto a = assuntos.getPrimeiro();
                a != null; a = assuntos.getProximo()) {
            Integer d = 2 + rand.nextInt(40);
            a.registrar("Providecia " + d.toString(), d);
        }
        servico.encerrar(atendimento);

        cliente = new Cliente(3, "Cliente3", 20);
        assuntos = new Assuntos();
        assuntos.insere(new Assunto(ta[4], "Assunto 1"));
        assuntos.insere(new Assunto(ta[5], "Assunto 2"));
        assuntos.insere(new Assunto(ta[6], "Assunto 3"));
        assuntos.insere(new Assunto(ta[7], "Assunto 4"));
        servico.recepcionar(cliente, assuntos);

        atendimento = servico.atender();
        assuntos = atendimento.getAssuntos();
        for (Assunto a = assuntos.getPrimeiro();
                a != null; a = assuntos.getProximo()) {
            Integer d = 2 + rand.nextInt(40);
            a.registrar("Providecia " + d.toString(), d);
        }
        servico.encerrar(atendimento);

        cliente = new Cliente(4, "Cliente4", 60);
        assuntos = new Assuntos();
        assuntos.insere(new Assunto(ta[9], "Assunto 1"));
        assuntos.insere(new Assunto(ta[10], "Assunto 2"));
        servico.recepcionar(cliente, assuntos);

        atendimento = servico.atender();
        assuntos = atendimento.getAssuntos();
        for (Assunto a = assuntos.getPrimeiro();
                a != null; a = assuntos.getProximo()) {
            Integer d = 2 + rand.nextInt(40);
            a.registrar("Providecia " + d.toString(), d);
        }
        servico.encerrar(atendimento);

        cliente = new Cliente(4, "Cliente5", 50);
        assuntos = new Assuntos();
        assuntos.insere(new Assunto(ta[8], "Assunto 1"));
        assuntos.insere(new Assunto(ta[9], "Assunto 2"));
        servico.recepcionar(cliente, assuntos);

        atendimento = servico.atender();
        assuntos = atendimento.getAssuntos();
        for (Assunto a = assuntos.getPrimeiro();
                a != null; a = assuntos.getProximo()) {
            Integer d = 2 + rand.nextInt(40);
            a.registrar("Providecia " + d.toString(), d);
        }
        servico.encerrar(atendimento);

        cliente = new Cliente(4, "Cliente6", 20);
        assuntos = new Assuntos();
        assuntos.insere(new Assunto(ta[7], "Assunto 1"));
        servico.recepcionar(cliente, assuntos);

        atendimento = servico.atender();
        assuntos = atendimento.getAssuntos();
        for (Assunto a = assuntos.getPrimeiro();
                a != null; a = assuntos.getProximo()) {
            Integer d = 2 + rand.nextInt(40);
            a.registrar("Providecia " + d.toString(), d);
        }
        servico.encerrar(atendimento);

        cliente = new Cliente(5, "Cliente7", 70);
        assuntos = new Assuntos();
        assuntos.insere(new Assunto(ta[6], "Assunto 1"));
        assuntos.insere(new Assunto(ta[5], "Assunto 2"));
        assuntos.insere(new Assunto(ta[4], "Assunto 3"));
        servico.recepcionar(cliente, assuntos);

        atendimento = servico.atender();
        assuntos = atendimento.getAssuntos();
        for (Assunto a = assuntos.getPrimeiro();
                a != null; a = assuntos.getProximo()) {
            Integer d = 2 + rand.nextInt(40);
            a.registrar("Providecia " + d.toString(), d);
        }
        servico.encerrar(atendimento);

        cliente = new Cliente(6, "Cliente8", 30);
        assuntos = new Assuntos();
        assuntos.insere(new Assunto(ta[5], "Assunto 1"));
        servico.recepcionar(cliente, assuntos);

        atendimento = servico.atender();
        assuntos = atendimento.getAssuntos();
        for (Assunto a = assuntos.getPrimeiro();
                a != null; a = assuntos.getProximo()) {
            Integer d = 2 + rand.nextInt(40);
            a.registrar("Providecia " + d.toString(), d);
        }
        servico.encerrar(atendimento);

        cliente = new Cliente(7, "Cliente9", 80);
        assuntos = new Assuntos();
        assuntos.insere(new Assunto(ta[4], "Assunto 1"));
        assuntos.insere(new Assunto(ta[3], "Assunto 2"));
        servico.recepcionar(cliente, assuntos);

        atendimento = servico.atender();
        assuntos = atendimento.getAssuntos();
        for (Assunto a = assuntos.getPrimeiro();
                a != null; a = assuntos.getProximo()) {
            Integer d = 2 + rand.nextInt(40);
            a.registrar("Providecia " + d.toString(), d);
        }
        servico.encerrar(atendimento);

        cliente = new Cliente(7, "Cliente10", 80);
        assuntos = new Assuntos();
        assuntos.insere(new Assunto(ta[2], "Assunto 1"));
        assuntos.insere(new Assunto(ta[1], "Assunto 2"));
        servico.recepcionar(cliente, assuntos);

        atendimento = servico.atender();
        assuntos = atendimento.getAssuntos();
        for (Assunto a = assuntos.getPrimeiro();
                a != null; a = assuntos.getProximo()) {
            Integer d = 2 + rand.nextInt(40);
            a.registrar("Providecia " + d.toString(), d);
        }
        servico.encerrar(atendimento);

        servico.gerarEstatistica();
    }

    public static void teste4(TipoAssunto[] ta) {
        System.out.println("*****Teste 4*****");

        Servico servico = new Servico();
        Cliente cliente;
        Assuntos assuntos;
        Random rand = new Random();
        Atendimento atendimento;

        cliente = new Cliente(1, "Cliente1", 40);
        assuntos = new Assuntos();
        assuntos.insere(new Assunto(ta[0], "Assunto 1"));
        assuntos.insere(new Assunto(ta[1], "Assunto 2"));
        assuntos.insere(new Assunto(ta[2], "Assunto 3"));
        servico.recepcionar(cliente, assuntos);

        atendimento = servico.atender();
        assuntos = atendimento.getAssuntos();
        for (Assunto a = assuntos.getPrimeiro();
                a != null; a = assuntos.getProximo()) {
            Integer d = 2 + rand.nextInt(40);
            a.registrar("Providecia " + d.toString(), d);
        }
        servico.encerrar(atendimento);

        cliente = new Cliente(1, "Cliente2", 40);
        assuntos = new Assuntos();
        assuntos.insere(new Assunto(ta[3], "Assunto 1"));
        assuntos.insere(new Assunto(ta[4], "Assunto 2"));
        assuntos.insere(new Assunto(ta[5], "Assunto 3"));
        servico.recepcionar(cliente, assuntos);

        atendimento = servico.atender();
        assuntos = atendimento.getAssuntos();
        for (Assunto a = assuntos.getPrimeiro();
                a != null; a = assuntos.getProximo()) {
            Integer d = 2 + rand.nextInt(40);
            a.registrar("Providecia " + d.toString(), d);
        }
        servico.encerrar(atendimento);

        servico.gerarEstatistica();
    }

    public static void teste5(TipoAssunto[] ta) {
        System.out.println("*****Teste 5*****");

        Servico servico = new Servico();
        Cliente cliente;
        Assuntos assuntos;
        Random rand = new Random();
        Atendimento atendimento;

        cliente = new Cliente(1, "Cliente1", 40);
        assuntos = new Assuntos();
        assuntos.insere(new Assunto(ta[1], "Assunto 1"));
        assuntos.insere(new Assunto(ta[2], "Assunto 2"));
        assuntos.insere(new Assunto(ta[3], "Assunto 3"));
        servico.recepcionar(cliente, assuntos);

        atendimento = servico.atender();
        assuntos = atendimento.getAssuntos();
        for (Assunto a = assuntos.getPrimeiro();
                a != null; a = assuntos.getProximo()) {
            Integer d = 2 + rand.nextInt(40);
            a.registrar("Providecia " + d.toString(), d);
        }
        servico.encerrar(atendimento);

        cliente = new Cliente(1, "Cliente2", 70);
        assuntos = new Assuntos();
        assuntos.insere(new Assunto(ta[1], "Assunto 1"));
        assuntos.insere(new Assunto(ta[2], "Assunto 2"));
        assuntos.insere(new Assunto(ta[3], "Assunto 3"));
        servico.recepcionar(cliente, assuntos);

        atendimento = servico.atender();
        assuntos = atendimento.getAssuntos();
        for (Assunto a = assuntos.getPrimeiro();
                a != null; a = assuntos.getProximo()) {
            Integer d = 2 + rand.nextInt(40);
            a.registrar("Providecia " + d.toString(), d);
        }
        servico.encerrar(atendimento);

        cliente = new Cliente(1, "Cliente3", 50);
        assuntos = new Assuntos();
        assuntos.insere(new Assunto(ta[1], "Assunto 1"));
        assuntos.insere(new Assunto(ta[2], "Assunto 2"));
        assuntos.insere(new Assunto(ta[3], "Assunto 3"));
        servico.recepcionar(cliente, assuntos);

        atendimento = servico.atender();
        assuntos = atendimento.getAssuntos();
        for (Assunto a = assuntos.getPrimeiro();
                a != null; a = assuntos.getProximo()) {
            Integer d = 2 + rand.nextInt(40);
            a.registrar("Providecia " + d.toString(), d);
        }
        servico.encerrar(atendimento);

        cliente = new Cliente(1, "Cliente3", 60);
        assuntos = new Assuntos();
        assuntos.insere(new Assunto(ta[1], "Assunto 1"));
        assuntos.insere(new Assunto(ta[2], "Assunto 2"));
        assuntos.insere(new Assunto(ta[3], "Assunto 3"));
        servico.recepcionar(cliente, assuntos);

        atendimento = servico.atender();
        assuntos = atendimento.getAssuntos();
        for (Assunto a = assuntos.getPrimeiro();
                a != null; a = assuntos.getProximo()) {
            Integer d = 2 + rand.nextInt(40);
            a.registrar("Providecia " + d.toString(), d);
        }
        servico.encerrar(atendimento);

        cliente = new Cliente(1, "Cliente4", 20);
        assuntos = new Assuntos();
        assuntos.insere(new Assunto(ta[1], "Assunto 1"));
        assuntos.insere(new Assunto(ta[2], "Assunto 2"));
        assuntos.insere(new Assunto(ta[3], "Assunto 3"));
        servico.recepcionar(cliente, assuntos);

        atendimento = servico.atender();
        assuntos = atendimento.getAssuntos();
        for (Assunto a = assuntos.getPrimeiro();
                a != null; a = assuntos.getProximo()) {
            Integer d = 2 + rand.nextInt(40);
            a.registrar("Providecia " + d.toString(), d);
        }
        servico.encerrar(atendimento);

        cliente = new Cliente(1, "Cliente5", 30);
        assuntos = new Assuntos();
        assuntos.insere(new Assunto(ta[1], "Assunto 1"));
        assuntos.insere(new Assunto(ta[2], "Assunto 2"));
        assuntos.insere(new Assunto(ta[3], "Assunto 3"));
        servico.recepcionar(cliente, assuntos);

        atendimento = servico.atender();
        assuntos = atendimento.getAssuntos();
        for (Assunto a = assuntos.getPrimeiro();
                a != null; a = assuntos.getProximo()) {
            Integer d = 2 + rand.nextInt(40);
            a.registrar("Providecia " + d.toString(), d);
        }
        servico.encerrar(atendimento);

        cliente = new Cliente(1, "Cliente6", 40);
        assuntos = new Assuntos();
        assuntos.insere(new Assunto(ta[1], "Assunto 1"));
        assuntos.insere(new Assunto(ta[2], "Assunto 2"));
        assuntos.insere(new Assunto(ta[3], "Assunto 3"));
        servico.recepcionar(cliente, assuntos);

        atendimento = servico.atender();
        assuntos = atendimento.getAssuntos();
        for (Assunto a = assuntos.getPrimeiro();
                a != null; a = assuntos.getProximo()) {
            Integer d = 2 + rand.nextInt(40);
            a.registrar("Providecia " + d.toString(), d);
        }
        servico.encerrar(atendimento);

        cliente = new Cliente(1, "Cliente7", 50);
        assuntos = new Assuntos();
        assuntos.insere(new Assunto(ta[1], "Assunto 1"));
        assuntos.insere(new Assunto(ta[2], "Assunto 2"));
        assuntos.insere(new Assunto(ta[3], "Assunto 3"));
        servico.recepcionar(cliente, assuntos);

        atendimento = servico.atender();
        assuntos = atendimento.getAssuntos();
        for (Assunto a = assuntos.getPrimeiro();
                a != null; a = assuntos.getProximo()) {
            Integer d = 2 + rand.nextInt(40);
            a.registrar("Providecia " + d.toString(), d);
        }
        servico.encerrar(atendimento);

        cliente = new Cliente(1, "Cliente8", 30);
        assuntos = new Assuntos();
        assuntos.insere(new Assunto(ta[1], "Assunto 1"));
        assuntos.insere(new Assunto(ta[2], "Assunto 2"));
        assuntos.insere(new Assunto(ta[3], "Assunto 3"));
        servico.recepcionar(cliente, assuntos);

        atendimento = servico.atender();
        assuntos = atendimento.getAssuntos();
        for (Assunto a = assuntos.getPrimeiro();
                a != null; a = assuntos.getProximo()) {
            Integer d = 2 + rand.nextInt(40);
            a.registrar("Providecia " + d.toString(), d);
        }
        servico.encerrar(atendimento);

        cliente = new Cliente(1, "Cliente9", 40);
        assuntos = new Assuntos();
        assuntos.insere(new Assunto(ta[1], "Assunto 1"));
        assuntos.insere(new Assunto(ta[2], "Assunto 2"));
        assuntos.insere(new Assunto(ta[3], "Assunto 3"));
        servico.recepcionar(cliente, assuntos);

        atendimento = servico.atender();
        assuntos = atendimento.getAssuntos();
        for (Assunto a = assuntos.getPrimeiro();
                a != null; a = assuntos.getProximo()) {
            Integer d = 2 + rand.nextInt(40);
            a.registrar("Providecia " + d.toString(), d);
        }
        servico.encerrar(atendimento);

        cliente = new Cliente(1, "Cliente10", 70);
        assuntos = new Assuntos();
        assuntos.insere(new Assunto(ta[1], "Assunto 1"));
        assuntos.insere(new Assunto(ta[2], "Assunto 2"));
        assuntos.insere(new Assunto(ta[3], "Assunto 3"));
        servico.recepcionar(cliente, assuntos);

        atendimento = servico.atender();
        assuntos = atendimento.getAssuntos();
        for (Assunto a = assuntos.getPrimeiro();
                a != null; a = assuntos.getProximo()) {
            Integer d = 2 + rand.nextInt(40);
            a.registrar("Providecia " + d.toString(), d);
        }
        servico.encerrar(atendimento);

        servico.gerarEstatistica();
    }

}
