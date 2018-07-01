package uff.ic.lleme.tic10002.trabalhos.s20181.Mateus_e_Raphael;

import uff.ic.lleme.tic10002.trabalhos.s20181.Mateus_e_Raphael.model.Atendimento;
import java.time.Instant;

/**
 *
 * @author mateu
 */
public class HeapDinamico {

    private int tamanho_heap;
    private Atendimento[] heap;
    //chaves
    private int n = 0;

    public HeapDinamico(int tamanho_heap) {
        this.tamanho_heap = tamanho_heap;
        heap = new Atendimento[this.tamanho_heap];
    }

    public int getTamanho_heap() {
        return tamanho_heap;
    }

    public void setTamanho_heap(int tamanho_heap) {
        this.tamanho_heap = tamanho_heap;
    }

    public int getN() {
        return n;
    }

    public void incluir(Atendimento atendimento) {
        if (n > this.getTamanho_heap() - 1)
            fullHeap();
        heap[n++] = atendimento;
        subir(n - 1);
    }

    public void fullHeap() {
        this.setTamanho_heap(this.getTamanho_heap() * 2);
        Atendimento[] heap_aux = new Atendimento[this.getTamanho_heap()];

        for (int i = 0; i < this.heap.length; i++)
            heap_aux[i] = this.heap[i];
        this.heap = heap_aux;
    }

    private void subir(int i) {
        int j = (i - 1) / 2;
        if (j >= 0) // verifica se já chegou ao máximo do vetor
            if (heap[i].getPrioridadeCliente() > heap[j].getPrioridadeCliente()) {
                trocar(i, j);
                subir(j);
            }
    }

    private void trocar(int i, int j) {
        Atendimento aux = heap[i];
        heap[i] = heap[j];
        heap[j] = aux;
    }

    public Atendimento remover() {
        Atendimento paciente = this.heap[0];
        heap[0] = heap[--n];
        heap[n] = null;
        descer(0);
        return paciente;
    }

    public Atendimento removerPrioritario() {
        //recalcula prioridade para cada vez q o paciente for chamado
        recalcularPrioridade(false);

        //remove o paciente com maior prioridade
        return remover();
    }

    public Atendimento removerPrioritario(Instant agora) {
        //recalcula prioridade para cada vez q o paciente for chamado
        recalcularPrioridade(agora);

        //remove o paciente com maior prioridade
        return remover();
    }

    public void recalcularPrioridade(boolean relcula) {
        for (int i = 0; i < getN() - 1; i++) {
            heap[i].calculaPrioridade(relcula);
            subir(i);
            descer(i);
        }
    }

    public void recalcularPrioridade(Instant agora) {
        for (int i = 0; i < getN() - 1; i++) {
            heap[i].calculaPrioridade(agora);
            subir(i);
            descer(i);
        }
    }

    private void descer(int i) {
        int filho = 2 * i + 1; //da direita
        if (filho < n) {
            if (filho < n - 1) //tem filho da esquerda
                if (heap[filho + 1].getPrioridadeCliente() > heap[filho].getPrioridadeCliente()) //escolhe o maior
                    filho = filho + 1;
            if (heap[i].getPrioridadeCliente() < heap[filho].getPrioridadeCliente()) {
                trocar(i, filho);
                descer(filho);
            }
        }
    }

    public void alterarPrioridade(int id, int prioridade) {
        heap[id].setPrioridadeCliente(prioridade);
        subir(id);
        descer(id);
    }

    public void imprimir() {
        System.out.print("{");
        for (int i = 0; i < this.getTamanho_heap(); i++)
            if (this.heap[i] != null)
                /*System.out.println("["+ this.heap[i].getCliente().getNome() + " - " +
                        this.heap[i].getPrioridadeCliente() +
                        "("+ ((float)(this.heap[i].getCliente().getIdade())/65)+ ";" +
                        this.heap[i].getAssuntos().getMediaAssuntos()/10 + ")]; ");*/
                System.out.print(this.heap[i].getCliente().getNome() + "; ");
        System.out.println("}");
    }
}
