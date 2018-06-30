package br.uff.ed.colecao;

import java.io.Serializable;

@SuppressWarnings("rawtypes")
public abstract class FilaDePrioridadesAbstrata<T extends Comparable> implements Serializable {

    private static final long serialVersionUID = 6236712988335433421L;
    protected Object[] heapMAX;
    protected int qtdElementosNNulos;
    protected int tamanhodafila;

    protected FilaDePrioridadesAbstrata(int tamanhodafila) {
        this.tamanhodafila = tamanhodafila;
        this.heapMAX = new Object[tamanhodafila];
    }

    public int getTamanhoFila() {
        return this.qtdElementosNNulos;
    }

    /*
	 * cada fila espec�fica tem um custo para essa fun��o. Ver as implementa��es
	 * filhas.
     */
    public abstract void add(T t);

    /*
	 * por estarmos utilizando heapmax, o elemento de maior prioridade sempre est�
	 * no topo, o que permite que a remo��o tenha custo de O1, ou seja, custo
	 * constante. No entanto, � necess�rio o uso da fun��o descer para manter as
	 * propriedades do heap, esta por sua vez tem custo O(log n) tornando o custo
	 * total para remo��o do elemento com maior prioridade O(log n).
     */
    @SuppressWarnings("unchecked")
    public T remove() {
        if (qtdElementosNNulos != 0) {
            T t = (T) this.heapMAX[1];
            this.heapMAX[1] = this.heapMAX[qtdElementosNNulos];
            qtdElementosNNulos--;
            descer(1, qtdElementosNNulos);
            return t;
        }
        return null;
    }

    /*
	 * Como um heap pode ser representado como uma �rvore bin�ria completa, ent�o a
	 * complexidade desta fun��o � a complexidade de encontrar um percurso em uma
	 * �rvore deste tipo onde a altura � (1+(piso(log n)). Desta forma, a
	 * complexidade da fun��o � O(log n).
     */
    @SuppressWarnings("unchecked")
    protected void subir(int no) {
        int pai = Math.floorDiv(no, 2);
        if (pai >= 1)
            if (((T) (heapMAX[no])).compareTo(((T) heapMAX[pai])) == 1) {
                swap(no, pai);
                subir(pai);
            }
    }

    /*
	 * Como um heap pode ser representado como uma �rvore bin�ria completa, ent�o a
	 * complexidade desta fun��o � a complexidade de encontrar um percurso em uma
	 * �rvore deste tipo onde a altura � (1+(piso(log n)). Desta forma, a
	 * complexidade da fun��o � O(log n).
     */
    @SuppressWarnings("unchecked")
    protected void descer(int index, int qtdElementosNNulos) {
        int filho = 2 * index;

        if (filho <= qtdElementosNNulos) {
            if (filho < qtdElementosNNulos)
                if (((T) heapMAX[filho + 1]).compareTo(((T) heapMAX[filho])) == 1)
                    filho = filho + 1;
            if (((T) heapMAX[index]).compareTo(((T) heapMAX[filho])) == -1) {
                swap(index, filho);
                descer(filho, qtdElementosNNulos);
            }
        }
    }

    /*
	 * Esta fun��o unicamente troca a posi��o de um "n� pai" com um de seus "filhos"
	 * ou "um de seus filhos" com o "n� pai", logo seu custo � O1 (constante).
     */
    private void swap(int index1, int index2) {
        Object noTMP = heapMAX[index1];
        heapMAX[index1] = heapMAX[index2];
        heapMAX[index2] = noTMP;
    }

    protected void aumentaHEAP() {
        Object[] tmp = new Object[2 * tamanhodafila];
        // (T[]) Array.newInstance(classe, 2 * tamanhodafila);
        for (int i = 0; i < tamanhodafila; i++)
            tmp[i] = heapMAX[i];
        this.heapMAX = tmp;
    }
}
