package uff.ic.lleme.tic10002.trabalhos.s20181.Romulo_e_Lucas.src.ed.trabalho20181;

public class HeapAtendimento {

    public class Priorizacao {

        public Atendimento objeto = null;
        public float prioridade = 0;

        public Priorizacao(Atendimento objeto, float prioridade) {
            this.objeto = objeto;
            this.prioridade = prioridade;
        }
    }

    private Priorizacao[] heap = new Priorizacao[100];
    private int n = 0;

    public void incluir(Atendimento objeto, float prioridade) {
        if (n < heap.length - 1) {
            this.heap = recalcularPrioridades();
            heap[n++] = new Priorizacao(objeto, prioridade);
            subir(n - 1);
        } else
            System.out.println("Limite excedido");
    }

    public Atendimento remover() {
        Atendimento t = heap[0].objeto;
        heap[0] = heap[--n]; // passa o ultimo para primeiro
        heap[n] = null; // apaga o ultimo
        descer(0);
        return t;
    }

    private void subir(int i) {
        int pai = (i - 1) / 2;
        if (pai >= 0)
            if (heap[i].prioridade > heap[pai].prioridade) {
                trocar(i, pai);
                subir(pai);
            }
    }

    private void descer(int i) {
        int filho = 2 * i + 1;
        if (filho < n) {
            if (filho < n - 1)
                if (heap[filho + 1].prioridade > heap[filho].prioridade)
                    filho = filho + 1;
            if (heap[i].prioridade < heap[filho].prioridade) {
                trocar(i, filho);
                descer(filho);
            }
        }
    }

    private void trocar(int i, int j) {
        Priorizacao aux = heap[i];
        heap[i] = heap[j];
        heap[j] = aux;
    }

    public void printHeapAtendimento() {
        System.out.print("{");
        for (Priorizacao h : heap)
            if (h != null)
                System.out.print(String.format("[%s -> %.4f] ", (h.objeto).getCliente().getNome(), h.prioridade));
        System.out.println("}");
    }

    private Priorizacao[] recalcularPrioridades() {
        HeapAtendimento recalculado = new HeapAtendimento();
        for (int i = 0; i < n; i++) {
            Atendimento a = heap[i].objeto;
            // a prioridade é recalculada e inserida em um novo heap
            recalculado.incluirSemRecalcular(a, a.calcularPrioridade());
        }
        return recalculado.heap;
    }

    private void incluirSemRecalcular(Atendimento o, float p) {
        if (n < heap.length - 1) {
            heap[n++] = new Priorizacao(o, p);
            subir(n - 1);
        }
    }

    public int getTamanho() {
        return n;
    }

    public Atendimento acessar(int i) {
        return heap[i].objeto;
    }
}
