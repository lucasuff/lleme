package br.uff.ed.colecao;

import br.uff.ed.main.Cliente;
import java.io.Serializable;

@SuppressWarnings("rawtypes")
public class FilaDePrioridadesEstatica<T extends Comparable> extends FilaDePrioridadesAbstrata<T>
        implements Serializable {

    private static final long serialVersionUID = 7454597459073987175L;

    public FilaDePrioridadesEstatica(int tamanhodafila) {
        super(tamanhodafila);
    }

    /*
	 * esta fila assume que as prioridades s�o est�ticas, ou seja, ao entrar na fila
	 * o elemento j� tem sua prioridade definida e as prioridades dos demais
	 * elementos n�o � alterada. Dessa forma, o custo de inserir um novo elemento
	 * depende da fun��o subir que tem custo O(log n).
     */
    public void add(T t) {
        if (this.qtdElementosNNulos >= this.heapMAX.length)
            aumentaHEAP();
        this.heapMAX[qtdElementosNNulos + 1] = t;
        qtdElementosNNulos++;
        System.out.println(qtdElementosNNulos);
        subir(qtdElementosNNulos);
        System.out.println(((Cliente) this.heapMAX[1]).getNome());
    }

}
