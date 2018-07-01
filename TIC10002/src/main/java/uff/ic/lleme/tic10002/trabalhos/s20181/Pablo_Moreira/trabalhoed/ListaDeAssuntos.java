/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.ic.lleme.tic10002.trabalhos.s20181.Pablo_Moreira.trabalhoed;

/**
 *
 * @author pablomoreira
 */
public class ListaDeAssuntos {

    private NoAssunto inicio;
    private NoAssunto finalL;
    private TipoAssunto tipo;
    private int tamanho;

    public ListaDeAssuntos(TipoAssunto tipo) {
        this.tipo = tipo;
        inicio = null;
        finalL = null;
        tamanho = 0;
    }

    public ListaDeAssuntos() {
        inicio = null;
        finalL = null;
        tipo = null;
        tamanho = 0;

    }

    public Assunto obterAssunto(int posicao) {

        int i = 0;
        NoAssunto aux = inicio;
        while (i <= posicao && aux != null) {

            if (i == posicao)
                return aux.assunto;
            aux = aux.prox;
            i++;

        }

        return null;
    }

    public void Inserir(Assunto novo) {

        if (tipo != null)
            if (novo.getTipo() != tipo.getTipo())
                return;

        if (tamanho == 0)
            inicio = finalL = new NoAssunto(novo);
        else {
            NoAssunto novoFinal = new NoAssunto(novo);
            finalL.prox = novoFinal;
            finalL = novoFinal;
        }
        tamanho++;

    }

    public float obterMediaDeTempos() {

        if (tipo == null)
            return -1.0f;

        if (tamanho == 0)
            return 0.0f;

        NoAssunto aux = inicio;
        float soma = 0.0f;
        while (aux != null) {
            soma += aux.assunto.getDuracao();
            aux = aux.prox;
        }

        return soma / tamanho;

    }

}

class NoAssunto {

    public Assunto assunto;
    public NoAssunto prox;

    public NoAssunto(Assunto assunto, NoAssunto prox) {
        this.assunto = assunto;
        this.prox = prox;
    }

    public NoAssunto(Assunto assunto) {
        this.assunto = assunto;
        this.prox = null;
    }

}
