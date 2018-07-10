package uff.ic.lleme.tic10002.trabalhos.s20181.Lucas_e_Erick;

import uff.ic.lleme.tic10002.trabalhos.s20181.Lucas_e_Erick.model.Cliente;
import uff.ic.lleme.tic10002.trabalhos.s20181.Lucas_e_Erick.model.Priorizavel;
import java.io.Serializable;

@SuppressWarnings("rawtypes")
public class FilaDePrioridadesDinamica<T extends Comparable & Priorizavel> extends FilaDePrioridadesAbstrata<T>
        implements Serializable {

    private static final long serialVersionUID = -2834229347761719146L;

    public FilaDePrioridadesDinamica(int tamanhodafila) {
        super(tamanhodafila);
    }

    /*
	 * por necessitar percorrer todo o heap e atualizar todos os elementos, a
	 * complexidade de inserir um novo elemento � On + o custo do subir que � O(log
	 * n), ou seja, o custo total ser� On por ser o termo mais relevante.
     */
    @SuppressWarnings("unchecked")
    public void add(T t) {
        System.out.println("a hora de entrada foi: " + t.getPriorizador());
        System.out.println("a prioridade do novo n� foi: " + ((Cliente) t).getPrioridade());
        if (qtdElementosNNulos != 0)
            for (int i = 1; i <= qtdElementosNNulos; i++) {
                ((T) this.heapMAX[i]).priorizar(t.getPriorizador());
                System.out.println("a prioridade do cliente " + ((Cliente) this.heapMAX[i]).getNome() + " �: "
                        + ((Cliente) this.heapMAX[i]).getPrioridade());
            }
        if (qtdElementosNNulos >= this.heapMAX.length)
            aumentaHEAP();
        this.heapMAX[qtdElementosNNulos + 1] = t;
        qtdElementosNNulos++;
        subir(qtdElementosNNulos);
        System.out.println("o novo topo �: " + (((Cliente) this.heapMAX[1]).getNome()));
    }

}
