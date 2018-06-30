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

public class InstanciasDeTeste3 {

    private List<Cliente> clientes;
    private List<TipoAssunto> tipoassuntos;

    public InstanciasDeTeste3() {
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
        Assunto conteudo1 = new Assunto(ta1, null, null, 0);
        Assunto conteudo4 = new Assunto(ta4, null, null, 0);
        Assunto conteudo5 = new Assunto(ta5, null, null, 0);
        Assunto conteudo7 = new Assunto(ta7, null, null, 0);
        Assunto conteudo8 = new Assunto(ta8, null, null, 0);
        Assunto conteudo10 = new Assunto(ta10, null, null, 0);
        int chaveAssunto = 0;

        Lista<Assunto> assunto1 = new Lista<Assunto>();
        assunto1.add(conteudo4, ++chaveAssunto);
        Lista<Assunto> assunto2 = new Lista<Assunto>();
        assunto2.add(conteudo7, ++chaveAssunto);
        Lista<Assunto> assunto3 = new Lista<Assunto>();
        assunto3.add(conteudo5, ++chaveAssunto);
        assunto3.add(conteudo1, ++chaveAssunto);
        Lista<Assunto> assunto4 = new Lista<Assunto>();
        assunto4.add(conteudo8, ++chaveAssunto);
        assunto4.add(conteudo10, ++chaveAssunto);
        Cliente c1 = new Cliente(123, 30, "Maurilho", assunto1, LocalDateTime.of(2018, Month.JUNE, 18, 11, 30));
        Cliente c2 = new Cliente(234, 40, "Rogerinho do Ing�", assunto2, LocalDateTime.of(2018, Month.JUNE, 18, 11, 05));
        Cliente c3 = new Cliente(452, 51, "Julinho da Vam", assunto4, LocalDateTime.of(2018, Month.JUNE, 18, 11, 10));
        Cliente c4 = new Cliente(239, 45, "Rennan", assunto3, LocalDateTime.of(2018, Month.JUNE, 18, 11, 15));
        clientes.add(c1);
        clientes.add(c2);
        clientes.add(c3);
        clientes.add(c4);

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
