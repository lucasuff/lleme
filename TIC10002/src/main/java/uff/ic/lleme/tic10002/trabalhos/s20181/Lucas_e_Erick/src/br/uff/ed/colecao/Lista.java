package br.uff.ed.colecao;

import java.io.Serializable;

@SuppressWarnings("rawtypes")
public class Lista<T extends Comparable> implements Serializable {

    private static final long serialVersionUID = -3593322284541883689L;
    private NoLista<T> noInicio;
    private int tamanho;

    public Lista() {
    }

    /*
	 * Por se tratar de uma lista encadeada, podemos sempre inserir um novo elemento
	 * no in�cio da lista e atualizar os ponteiros. Dessa forma o custo para
	 * inser��o de um novo elemento � O1, ou seja, custo constante.
     */
    public void add(T conteudo, int chave) {
        if (noInicio == null) {
            noInicio = new NoLista<T>(conteudo, chave);
            tamanho++;
        } else {
            NoLista<T> noNovo = new NoLista<T>(conteudo, chave);
            noNovo.setProxNo(noInicio);
            noInicio = noNovo;
            tamanho++;
        }
    }

    /*
* O metodo de busca, por se tratar de uma lista encadeada no pior caso tem complexidade O(n) j� que percorrer toda a lista.
     */
    public NoLista busca(int chave) {
        return buscaPrivada(chave)[0];
    }

    private NoLista[] buscaPrivada(int chave) {
        NoLista ant = null;
        NoLista prox = null;
        NoLista aux = this.noInicio;
        if (aux != null)
            if (aux.getChave() == chave) {
                ant = null;
                prox = (NoLista) aux.getProxNo();
                NoLista[] arrayNo = new NoLista[3];
                arrayNo[0] = aux;
                arrayNo[1] = ant;
                arrayNo[2] = prox;
                return arrayNo;
            } else
                while (aux.getProxNo() != null) {
                    ant = aux;
                    aux = (NoLista) aux.getProxNo();
                    prox = (NoLista) aux.getProxNo();
                    if (aux.getChave() == chave) {
                        NoLista[] arrayNo = new NoLista[3];
                        arrayNo[0] = aux;
                        arrayNo[1] = ant;
                        arrayNo[2] = prox;
                        return arrayNo;
                    }
                }
        return null;
    }

    /*
* esse m�todo tem a mesma complexidade do metodo buscar, j� que para remover um elemento � necess�rio acha-lo na lista, o que no pior caso ter� complexidade de O(n).
     */
    @SuppressWarnings({"unchecked", "unused"})
    public T remove(int chave) {
        NoLista[] aux = this.buscaPrivada(chave);
        NoLista busc = aux[0];
        NoLista ant = aux[1];
        NoLista prox = aux[2];

        if (aux != null) {
            if (ant != null && prox != null)
                ant.setProxNo(prox);
            else if (ant == null && prox != null)
                this.noInicio = prox;
            else if (ant != null && prox == null)
                ant.setProxNo(null);
            this.tamanho--;
            return (T) busc.getConteudo();
        }
        return null;
    }

    /*
* complexidade constante
     */
    public NoLista<T> getNoInicio() {
        return noInicio;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public void setNoInicio(NoLista<T> noInicio) {
        this.noInicio = noInicio;
    }

    /*
* esse m�todo transforma a lista em um array de objects para facilitar a itera��o em outras classes que venham a usar uma lista. Por necessitar percorrer toda a lista sua complexidade � O(n).
     */
    public Object[] toArray() {
        if (this.noInicio != null) {
            Object[] o = new Object[this.tamanho];
            NoLista<T> aux = this.noInicio;
            int i = 0;
            while (aux != null) {
                o[i] = aux.getConteudo();
                aux = ((NoLista<T>) aux.proxNo);
                i++;
            }
            return o;
        }
        return null;
    }

}
