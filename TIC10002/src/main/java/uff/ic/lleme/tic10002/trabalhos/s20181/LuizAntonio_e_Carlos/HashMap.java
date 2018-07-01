/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.ic.lleme.tic10002.trabalhos.s20181.LuizAntonio_e_Carlos;
// encadeada de todos os atendimentos encerrados que possuem assuntos daquele tipo.

import uff.ic.lleme.tic10002.trabalhos.s20181.LuizAntonio_e_Carlos.model.Assunto;
import uff.ic.lleme.tic10002.trabalhos.s20181.LuizAntonio_e_Carlos.model.Atendimento;
import uff.ic.lleme.tic10002.trabalhos.s20181.LuizAntonio_e_Carlos.model.TipoAssunto;

/**
 *
 * @author Carlos Muniz
 */
public class HashMap {

    public class No {

        private Atendimento elemento;
        private No proximo;

        public No(Atendimento elemento, No proximo) {
            this.elemento = elemento;
            this.proximo = proximo;
        }

        public Atendimento getElemento() {
            return this.elemento;
        }

        public void setElemento(Atendimento elemento) {
            this.elemento = elemento;
        }

        public No getProximo() {
            return this.proximo;
        }

        public void setProximo(No proximo) {
            this.proximo = proximo;
        }

    }

    private int tamanho = 11;
    private No[] tabela = new No[tamanho];

    public HashMap() {
        for (int i = 0; i < tamanho; i++)
            tabela[i] = null;

    }

    public void incluir(Atendimento atendimento, TipoAssunto tipo) {
        int i = hash(tipo.getTipo());
        if (tabela[i] == null)
            tabela[i] = new No(atendimento, null);
        else {
            No atual = tabela[i];
            if (!tabela[i].getElemento().equals(atendimento)) {
                while (atual.getProximo() != null) {
                    atual = atual.getProximo();
                    if (atual.getElemento().equals(atendimento))
                        return;
                }
                atual.setProximo(new No(atendimento, null));
            }
        }
    }

    public void incluir(Atendimento atendimento) {
        if (atendimento != null) {
            Assunto[] assuntos = atendimento.getAssuntos();

            for (Assunto assunto : assuntos)
                incluir(atendimento, assunto.getTipo());

        }

    }

    public No consultar(TipoAssunto tipo) {
        int i = hash(tipo.getTipo());
        return tabela[i];
    }

    private int hash(String nome) {
        int h = 0;
        int a = 31;
        for (int i = 0; i < nome.length(); ++i)
            h = ((h * a) + nome.charAt(i)) % tamanho;
        return h;
    }

    public int getTamanho() {
        return this.tamanho;
    }

}
