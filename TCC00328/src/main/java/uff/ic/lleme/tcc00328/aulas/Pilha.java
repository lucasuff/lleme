package uff.ic.lleme.tcc00328.aulas;

public class Pilha<T> {

    private Object[] elementos = new Object[100];
    private int topo = -1;

    public void push(T elemento) {
        elementos[++topo] = elemento;
    }

    public T pop() {
        return (T) elementos[--topo + 1];
    }

    public boolean isEmpty() {
        return topo < 0;
    }
}
