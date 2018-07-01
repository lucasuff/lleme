package uff.ic.lleme.tic10002.trabalhos.s20181.Romulo_e_Lucas;

import uff.ic.lleme.tic10002.trabalhos.s20181.Romulo_e_Lucas.model.Assunto;

public class HeapAssunto {

    private Assunto[] heap = new Assunto[100];
    private int n = 0;

    public void incluir(Assunto objeto) {
        if (n < heap.length - 1) {
            heap[n++] = objeto;
            subir(n - 1);
        } else
            System.out.println("Limite excedido");
    }

    public Assunto remover() {
        Assunto t = heap[0];
        heap[0] = heap[--n]; // passa o ultimo para primeiro
        heap[n] = null; // apaga o ultimo
        descer(0);
        return t;
    }

    private void subir(int i) {
        int pai = (i - 1) / 2;
        if (pai >= 0)
            if (heap[i].getTipoAssunto().getUrgencia() > heap[pai].getTipoAssunto().getUrgencia()) {
                trocar(i, pai);
                subir(pai);
            }
    }

    private void descer(int i) {
        int filho = 2 * i + 1;
        if (filho < n) {
            if (filho < n - 1)
                if (heap[filho + 1].getTipoAssunto().getUrgencia() > heap[filho].getTipoAssunto().getUrgencia())
                    filho = filho + 1;
            if (heap[i].getTipoAssunto().getUrgencia() < heap[filho].getTipoAssunto().getUrgencia()) {
                trocar(i, filho);
                descer(filho);
            }
        }
    }

    private void trocar(int i, int j) {
        Assunto aux = heap[i];
        heap[i] = heap[j];
        heap[j] = aux;
    }

    public void printHeapAssunto() {
        System.out.print("{");
        for (Assunto h : heap)
            if (h != null)
                System.out.print(String.format("[%s -> %.4f] ", h.getDescricao(), h.getTipoAssunto().getUrgencia()));
        System.out.println("}");
    }

    public int getTamanho() {
        return n;
    }

    public Assunto acessar(int i) {
        return heap[i];
    }
}
