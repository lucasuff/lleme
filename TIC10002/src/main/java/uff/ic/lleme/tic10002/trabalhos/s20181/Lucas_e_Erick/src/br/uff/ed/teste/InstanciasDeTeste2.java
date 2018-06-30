package br.uff.ed.teste;

import br.uff.ed.colecao.Lista;
import br.uff.ed.main.Assunto;
import br.uff.ed.main.Cliente;
import br.uff.ed.main.TipoAssunto;
import br.uff.ed.main.Urgencia;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class InstanciasDeTeste2 {

    private List<Cliente> clientes;
    private List<TipoAssunto> tipoassuntos;

    public InstanciasDeTeste2() {
        clientes = new ArrayList<Cliente>();
        tipoassuntos = new ArrayList<TipoAssunto>();
        TipoAssunto ta1 = new TipoAssunto(0, "cabe�a", "dor de cabe�a", Urgencia.L1);
        tipoassuntos.add(ta1);
        TipoAssunto ta2 = new TipoAssunto(1, "diabetes", "crise de insulina", Urgencia.L2);
        tipoassuntos.add(ta2);
        TipoAssunto ta3 = new TipoAssunto(2, "rinite", "virose", Urgencia.L3);
        tipoassuntos.add(ta3);
        TipoAssunto ta4 = new TipoAssunto(3, "dengue", "virose", Urgencia.L4);
        tipoassuntos.add(ta4);
        TipoAssunto ta5 = new TipoAssunto(4, "chicungunya", "virose", Urgencia.L5);
        tipoassuntos.add(ta5);
        TipoAssunto ta6 = new TipoAssunto(5, "pneumonia", "bacteriana", Urgencia.L6);
        tipoassuntos.add(ta6);
        TipoAssunto ta7 = new TipoAssunto(6, "atropelamento", "morrendo", Urgencia.L7);
        tipoassuntos.add(ta7);
        TipoAssunto ta8 = new TipoAssunto(7, "dor", "cora��o", Urgencia.L8);
        tipoassuntos.add(ta8);
        TipoAssunto ta9 = new TipoAssunto(8, "parto", "parto", Urgencia.L9);
        tipoassuntos.add(ta9);
        TipoAssunto ta10 = new TipoAssunto(9, "hemorragia", "interna", Urgencia.L10);
        tipoassuntos.add(ta10);
        Assunto conteudo2 = new Assunto(ta2, null, null, 0);
        Assunto conteudo3 = new Assunto(ta3, null, null, 0);
        Assunto conteudo4 = new Assunto(ta4, null, null, 0);
        Assunto conteudo5 = new Assunto(ta5, null, null, 0);
        Assunto conteudo6 = new Assunto(ta6, null, null, 0);
        Assunto conteudo7 = new Assunto(ta7, null, null, 0);
        Assunto conteudo8 = new Assunto(ta8, null, null, 0);
        Assunto conteudo9 = new Assunto(ta9, null, null, 0);
        Assunto conteudo10 = new Assunto(ta10, null, null, 0);
        int chaveAssunto = 0;

        Lista<Assunto> assunto1 = new Lista<Assunto>();
        assunto1.add(conteudo3, ++chaveAssunto);
        Lista<Assunto> assunto2 = new Lista<Assunto>();
        assunto2.add(conteudo4, ++chaveAssunto);
        Lista<Assunto> assunto3 = new Lista<Assunto>();
        assunto3.add(conteudo5, ++chaveAssunto);
        assunto3.add(conteudo6, ++chaveAssunto);
        Lista<Assunto> assunto4 = new Lista<Assunto>();
        assunto4.add(conteudo2, ++chaveAssunto);
        assunto4.add(conteudo7, ++chaveAssunto);
        assunto4.add(conteudo8, ++chaveAssunto);
        assunto4.add(conteudo3, ++chaveAssunto);
        Lista<Assunto> assunto5 = new Lista<Assunto>();
        assunto5.add(conteudo9, ++chaveAssunto);
        assunto5.add(conteudo10, ++chaveAssunto);
        Lista<Assunto> assunto6 = new Lista<Assunto>();
        assunto6.add(conteudo10, ++chaveAssunto);
        assunto6.add(conteudo7, ++chaveAssunto);
        Lista<Assunto> assunto7 = new Lista<Assunto>();
        assunto7.add(conteudo3, ++chaveAssunto);
        Cliente c7 = new Cliente(123, 19, "claudio", assunto1, LocalDateTime.of(2018, Month.JUNE, 18, 11, 30));
        Cliente c2 = new Cliente(234, 30, "luiz", assunto2, LocalDateTime.of(2018, Month.JUNE, 18, 11, 05));
        Cliente c3 = new Cliente(452, 89, "maom�", assunto4, LocalDateTime.of(2018, Month.JUNE, 18, 11, 10));
        Cliente c4 = new Cliente(239, 67, "jo�o", assunto3, LocalDateTime.of(2018, Month.JUNE, 18, 11, 15));
        Cliente c5 = new Cliente(931, 35, "maria", assunto5, LocalDateTime.of(2018, Month.JUNE, 18, 11, 20));
        Cliente c6 = new Cliente(232, 2, "joana", assunto6, LocalDateTime.of(2018, Month.JUNE, 18, 11, 25));
        Cliente c1 = new Cliente(6777, 17, "bianca", assunto7, LocalDateTime.of(2018, Month.JUNE, 18, 11, 00));
        clientes.add(c1);
        clientes.add(c2);
        clientes.add(c3);
        clientes.add(c4);
        clientes.add(c5);
        clientes.add(c6);
        clientes.add(c7);

        // ============================
        /*
		 * chaveAssunto = 0; Lista<Assunto> assunto20 = new Lista<Assunto>();
		 * assunto20.add(conteudo1, ++chaveAssunto); assunto20.add(conteudo2,
		 * ++chaveAssunto); assunto20.add(conteudo3, ++chaveAssunto);
		 * assunto20.add(conteudo4, ++chaveAssunto); assunto20.add(conteudo5,
		 * ++chaveAssunto); assunto20.add(conteudo6, ++chaveAssunto);
		 * assunto20.add(conteudo7, ++chaveAssunto); assunto20.add(conteudo8,
		 * ++chaveAssunto); assunto20.add(conteudo9, ++chaveAssunto);
		 * assunto20.add(conteudo10, ++chaveAssunto);
		 *
		 * // NoLista ant = new NoLista(); // NoLista prox = new NoLista();
		 *
		 * for (int i = 1; i <= 10; i++) { System.out.println("Tamanho da lista: " +
		 * assunto20.getTamanho());
		 * System.out.println(assunto20.remove(i).getTipoAssunto().getTipo()); }
         */
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public List<TipoAssunto> getTipoAssuntos() {
        return tipoassuntos;
    }
}
