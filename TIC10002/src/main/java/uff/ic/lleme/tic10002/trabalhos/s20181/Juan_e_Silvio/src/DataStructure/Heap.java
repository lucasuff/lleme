package DataStructure;

import Objects.Atendimento;
import javax.naming.LimitExceededException;

public class Heap {

    private HeapNode[] fila = new HeapNode[100];
    private int n = 0;

    public Atendimento getNext() throws LimitExceededException {
        return remover();
    }

    private void subir(int i) {
        int pai = (i - 1) / 2;
        if (pai >= 0)
            if (fila[i].prioridade > fila[pai].prioridade) {
                trocar(i, pai);
                subir(pai);
            }
    }

    private void descer(int i) {
        int j = (2 * i) + 1;
        if (j < n) {
            if (j < n - 1)
                if (fila[j + 1].prioridade > fila[j].prioridade)
                    j = j + 1;
            if (fila[i].prioridade < fila[j].prioridade) {
                trocar(i, j);
                descer(j);
            }
        }
    }

    public void inserir(Atendimento a) throws LimitExceededException {
        HeapNode no = new HeapNode(a);
        reorganizaHeap();
        if (n < fila.length - 1) {
            fila[n++] = no;
            subir(n - 1); // Insere na posição n anterior, pois incrementou no passo acima
        } else
            throw new LimitExceededException();
    }

    public int getSize() {
        return n;
    }

    public Atendimento remover() throws LimitExceededException {
        if (n != 0) {
            Atendimento t = fila[0].atendimento;
            fila[0] = fila[--n];
            fila[n] = null;
            descer(0);
            return t;
        } else
            throw new LimitExceededException();
    }

    public void reorganizaHeap() {
        for (int i = 0; i < n; i++)
            alterarPrioridade(i);
    }

    /*
        DAQUI PRA CIMA ESTÁ REVISADO
     */
    public void alterarPrioridade(int id) {
        fila[id].recalculaPrioridade();
        subir(id);
        descer(id);
    }

    public void trocar(int i, int j) {
        HeapNode aux = fila[i];
        fila[i] = fila[j];
        fila[j] = aux;
    }

    public void printHeap() {
        System.out.print("HEAP = {");
        for (int i = 0; i < fila.length; i++)
            if (fila[i] != null)
                System.out.print(String.format("[%d,%s,%f],", i, fila[i].atendimento.getCliente().getNome(), fila[i].prioridade));
        System.out.println("}");
    }
}
