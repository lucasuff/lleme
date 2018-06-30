/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.ic.lleme.tic10002.trabalhos.s20181.LuizAntonio_e_CarlosMuniz;

/**
 *
 * @author Luiz Antonio
 */
public class ListaDeAtendimentos {

    private class Elemento {

        public Atendimento atendimento;
        public Elemento proximo;

        public Elemento(Atendimento atendimento) {
            this.atendimento = atendimento;
        }
    }

    private Elemento primeiro = null;

    public void incluir(Atendimento atendimento) {
        if (primeiro == null)
            primeiro = new Elemento(atendimento);
        else
            incluir(atendimento, primeiro);
    }

    private void incluir(Atendimento atendimento, Elemento elemento) {
        if (elemento.proximo == null)
            elemento.proximo = new Elemento(atendimento);
        else
            incluir(atendimento, elemento.proximo);
    }

    public Atendimento buscar(int idCliente) {
        if (primeiro == null) {
            System.out.println("Não existem clientes em atendimento.");
            return null;
        } else if (primeiro.atendimento.getCliente().getId() == idCliente) {
            Atendimento atendimento = primeiro.atendimento;

            // remove o elemento encontrado da lista
            Elemento proximo = primeiro.proximo;
            primeiro = proximo;

            return atendimento;
        } else
            return buscar(idCliente, primeiro.proximo);
    }

    private Atendimento buscar(int idCliente, Elemento elemento) {
        if (elemento == null)
            return null;
        else if (elemento.atendimento.getCliente().getId() == idCliente) {
            Atendimento atendimento = elemento.atendimento;

            // remove o elemento encontrado da lista
            Elemento proximo = elemento.proximo;
            elemento = proximo;

            return atendimento;
        } else
            return buscar(idCliente, elemento.proximo);
    }

    public void print() {
        if (primeiro != null) {
            Elemento elemento = primeiro;
            System.out.print("[" + elemento.atendimento.getCliente());

            if (elemento.proximo != null)
                System.out.print(",");

            while (elemento.proximo != null) {
                elemento = elemento.proximo;
                System.out.print(elemento.atendimento.getCliente());
                if (elemento.proximo != null)
                    System.out.print(",");
            }
            System.out.println("]");
        }
    }

}
