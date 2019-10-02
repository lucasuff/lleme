package uff.ic.lleme.tcc00328.exercicios.jogoDeDados;

public class Dado {

    private int valor = 1;

    public int getValor() {
        return valor;
    }

    public int sortear() {
        valor = (int) (1 + Math.random() * 6);
        return valor;
    }
}
