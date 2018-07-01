package uff.ic.lleme.tic10002.trabalhos.s20181.Bruna_Cleomar_Patrick;

public class ListaEstatica {

    protected int indice = 0;
    protected int tamanho = 0;
    protected Object[] lista;

    //... construtor.
    public ListaEstatica(int tamanho) {
        if (tamanho < 0)
            throw new IllegalArgumentException("Tamanho não pode ser zero.");

        this.tamanho = tamanho;
        this.lista = new Object[tamanho];
    }

    public int getTamanho() {
        return this.indice;
    }

    public boolean vazia() {
        return this.indice == 0;
    }

    public boolean Inserir(Object e) {
        if (getTamanho() + 1 <= tamanho) {
            ((Object[]) this.lista)[indice++] = e;
            return true;
        }
        return false;
    }

    public Object Remover(int index) {
        Object item = null;
        if (index < indice) {
            item = ((Object[]) this.lista)[index];
            indice--;
        }
        return item;
    }

    public Object get(int index) {
        Object item = null;
        if (index < indice)
            item = ((Object[]) this.lista)[index];
        return item;
    }

    public Object set(int index, Object novoItem) {
        Object item = null;
        if (index < indice)
            item = ((Object[]) this.lista)[index] = novoItem;
        return item;
    }

    public Object Buscar(Object obj) {
        return null;
    }

}
