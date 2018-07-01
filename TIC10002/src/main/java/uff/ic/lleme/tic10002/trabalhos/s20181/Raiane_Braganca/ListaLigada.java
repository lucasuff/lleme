package uff.ic.lleme.tic10002.trabalhos.s20181.Raiane_Braganca;

import java.util.Comparator;
import java.util.NoSuchElementException;

class No<E> {

    E item;
    No<E> proximo;
    No<E> anterior;

    No(No<E> anterior, E element, No<E> proximo) {
        this.item = element;
        this.proximo = proximo;
        this.anterior = anterior;
    }
}

public class ListaLigada<E> {

    transient int tamanho = 0;

    /**
     * Ponteiro para o primeiro elemento. (primeiro == null && ultimo == null)
     * || (primeiro.anterior == null && primeiro.item != null)
     */
    transient No<E> primeiro;

    /**
     * Ponteiro para o último elemento. (primeiro == null && ultimo == null) ||
     * (ultimo.proximo == null && ultimo.item != null)
     */
    transient No<E> ultimo;

    public ListaLigada() {
    }

    /**
     * Adiciona vínculo para o primeiro elemento.
     */
    private void vinculaPrimeiro(E e) {
        final No<E> f = primeiro;
        final No<E> novoNo = new No<>(null, e, f);
        primeiro = novoNo;
        if (f == null)
            ultimo = novoNo;
        else
            f.anterior = novoNo;
        tamanho++;
    }

    /**
     * Adiciona vínculo para o último elemento.
     */
    void vinculaUltimo(E e) {
        final No<E> l = ultimo;
        final No<E> novoNo = new No<>(l, e, null);
        ultimo = novoNo;
        if (l == null)
            primeiro = novoNo;
        else
            l.proximo = novoNo;
        tamanho++;
    }

    /**
     * Adiciona vínculo antes do elemento especificado.
     */
    void vinculaAntes(E e, No<E> succ) {
        final No<E> pred = succ.anterior;
        final No<E> novoNo = new No<>(pred, e, succ);
        succ.anterior = novoNo;
        if (pred == null)
            primeiro = novoNo;
        else
            pred.proximo = novoNo;
        tamanho++;
    }

    /**
     * Retira o vínculo do primeiro elemento.
     */
    private E desvinculaPrimeiro(No<E> f) {
        final E element = f.item;
        final No<E> proximo = f.proximo;
        f.item = null;
        f.proximo = null;
        primeiro = proximo;
        if (proximo == null)
            ultimo = null;
        else
            proximo.anterior = null;
        tamanho--;
        return element;
    }

    /**
     * Retira o vínculo do último elemento.
     */
    private E desvinculaUltimo(No<E> l) {
        final E element = l.item;
        final No<E> anterior = l.anterior;
        l.item = null;
        l.anterior = null;
        ultimo = anterior;
        if (anterior == null)
            primeiro = null;
        else
            anterior.proximo = null;
        tamanho--;
        return element;
    }

    /**
     * Retira o vínculo do elemento especificado.
     */
    E desvincula(No<E> x) {
        final E element = x.item;
        final No<E> proximo = x.proximo;
        final No<E> anterior = x.anterior;

        if (anterior == null)
            primeiro = proximo;
        else {
            anterior.proximo = proximo;
            x.anterior = null;
        }

        if (proximo == null)
            ultimo = anterior;
        else {
            proximo.anterior = anterior;
            x.proximo = null;
        }

        x.item = null;
        tamanho--;
        return element;
    }

    /**
     * Retorna o primeiro elemento da lista.
     *
     * @return o primeiro elemento da lista
     * @throws NoSuchElementException se a lista estiver vazia
     */
    public E obtemPrimeiro() {
        final No<E> f = primeiro;
        if (f == null)
            throw new NoSuchElementException();
        return f.item;
    }

    /**
     * Retorna o último elemento da lista.
     *
     * @return o último elemento da lista
     * @throws NoSuchElementException se a lista estiver vazia
     */
    public E obtemUltimo() {
        final No<E> l = ultimo;
        if (l == null)
            throw new NoSuchElementException();
        return l.item;
    }

    /**
     * Remove e retorna o primeiro elemento da lista.
     *
     * @return o primeiro elemento da lista
     * @throws NoSuchElementException se a lista estiver vazia
     */
    public E removePrimeiro() {
        final No<E> f = primeiro;
        if (f == null)
            throw new NoSuchElementException();
        return desvinculaPrimeiro(f);
    }

    /**
     * Remove e retorna o último elemento da lista.
     *
     * @return o último elemento da lista
     * @throws NoSuchElementException se a lista estiver vazia
     */
    public E removeUltimo() {
        final No<E> l = ultimo;
        if (l == null)
            throw new NoSuchElementException();
        return desvinculaUltimo(l);
    }

    /**
     * Adiciona um elmento no final da lista.
     *
     * Este método é equivalente ao {@link #adicionaNoFinal}
     *
     * @param e elemento a ser inserido na lista
     * @return {@code true} (as specified by {@link Collection#adiciona})
     */
    public boolean adiciona(E e) {
        vinculaUltimo(e);
        return true;
    }

    /**
     * Adiciona o elemento especificado no início da lista.
     *
     * @param e o elmento que será adiciona
     */
    public void adicionaNoInicio(E e) {
        vinculaPrimeiro(e);
    }

    /**
     * Adiciona o elemento especificado no final da lista.
     *
     * Este método é equivalente ao {@link #adiciona}
     *
     * @param e o elmento que será adiciona
     */
    public void adicionaNoFinal(E e) {
        vinculaUltimo(e);
    }

    /**
     * Insere o elemento na lista na posição especificada. Desloca o elemento
     * atualmente da posição (se existir) e qualquer elementos posteriores para
     * a direita.
     *
     * @param indice índice em que o elemento especificado deve ser inserido
     * @param elemento elemento a ser inserido
     */
    public void adicionaNumaPosicaoEspecifica(int indice, E elemento) {
        verificaPosicao(indice);

        if (indice == tamanho)
            vinculaUltimo(elemento);
        else
            vinculaAntes(elemento, no(indice));
    }

    /**
     * Insere um elemento na lista utilizando um comparator.
     *
     * @param a elemento a ser inserido
     * @param c objeto comparator
     * @return a posição na qual o elemento foi inserido
     */
    public int adicionaComPrioridade(E a, Comparator c) {
        E b;
        if (estaVazia())
            adiciona(a);
        else {
            for (int i = 0; i < tamanho; i++) {
                b = obtem(i);
                if (c.compare(a, b) < 0) {
                    adicionaNumaPosicaoEspecifica(i, a);
                    return i;
                }
            }
            adicionaNoFinal(a);
            return (tamanho - 1);
        }
        return 0;
    }

    /**
     * Retorna o número de elementos da lista.
     *
     * @return o número de elementos da lista
     */
    public int tamanho() {
        return tamanho;
    }

    /**
     * Remove todos os elementos da lista.
     */
    public void limpa() {
        for (No<E> x = primeiro; x != null;) {
            No<E> proximo = x.proximo;
            x.item = null;
            x.proximo = null;
            x.anterior = null;
            x = proximo;
        }
        primeiro = ultimo = null;
        tamanho = 0;
    }

    /**
     * Retorna o elemento na posição especificada.
     *
     * @param indice índice do elemento a devolver
     * @return o elemento especificado pela posição informada
     */
    public E obtem(int indice) {
        verifiqueIndice(indice);
        return no(indice).item;
    }

    /**
     * Substitui o elemento na posição especificada, pelo elemento informado.
     *
     * @param indice índice do elemento a substituir
     * @param elemento elemento a ser armazenado na posição especificada
     * @return o elemento anteriormente na posição especificada
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public E substitui(int indice, E elemento) {
        verifiqueIndice(indice);
        No<E> x = no(indice);
        E oldVal = x.item;
        x.item = elemento;
        return oldVal;
    }

    /**
     * Remove o elemento da lista na posição especificada. Desloca qualquer
     * elementos subsequentes para a esquerda. Retorna o elemento que foi
     * removido da lista.
     *
     * @param indice índice do elemento a ser removido
     * @return o elemento que foi removido
     */
    public E remove(int indice) {
        verifiqueIndice(indice);
        return desvincula(no(indice));
    }

    /**
     * Remove o primeiro elemento da lista.
     *
     * @return o primeiro elemento da lista
     * @throws NoSuchElementException se a lista estiver vazia
     */
    public E remove() {
        return removePrimeiro();
    }

    /**
     * Verifica se a lista está vazia.
     *
     * @return boolean
     */
    public boolean estaVazia() {
        return tamanho == 0;
    }

    /**
     * Verifica se o indice é de um elemento existente.
     */
    private boolean indiceValido(int indice) {
        return indice >= 0 && indice < tamanho;
    }

    /**
     * Verifica se o argumento é o índice de uma posição válida para um iterador
     * ou uma operação de adição.
     */
    private boolean posicaoValida(int indice) {
        return indice >= 0 && indice <= tamanho;
    }

    private String outOfBoundsMsg(int indice) {
        return "Indice: " + indice + ", Tamanho: " + tamanho;
    }

    private void verifiqueIndice(int indice) {
        if (!indiceValido(indice))
            throw new IndexOutOfBoundsException(outOfBoundsMsg(indice));
    }

    private void verificaPosicao(int indice) {
        if (!posicaoValida(indice))
            throw new IndexOutOfBoundsException(outOfBoundsMsg(indice));
    }

    No<E> no(int indice) {
        if (indice < (tamanho >> 1)) {
            No<E> x = primeiro;
            for (int i = 0; i < indice; i++)
                x = x.proximo;
            return x;
        } else {
            No<E> x = ultimo;
            for (int i = tamanho - 1; i > indice; i--)
                x = x.anterior;
            return x;
        }
    }

}
