package uff.ic.lleme.tic10002.trabalhos.s20181.Lucas_e_Erick.model;

public enum Urgencia {
    L1(1), L2(2), L3(3), L4(4), L5(5), L6(6), L7(7), L8(8), L9(9), L10(10);
    private int valor;

    Urgencia(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }
}
