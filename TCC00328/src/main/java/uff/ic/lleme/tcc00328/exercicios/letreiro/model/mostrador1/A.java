package uff.ic.lleme.tcc00328.exercicios.letreiro.model.mostrador1;

import uff.ic.lleme.tcc00328.exercicios.letreiro.model.Caractere;

public class A extends Caractere {

    private static A instancia = null;

    private A() {
    }

    public static A obterInstancia() {
        if (instancia == null)
            instancia = new A();
        return instancia;
    }

    @Override
    public void acenderMostrador(Mostrador21Segmentos mostrador) {
        boolean[][] estados = {{true, true, true, true, true},
        {true, false, false, false, true},
        {true, true, true, true, true},
        {true, false, false, false, true},
        {true, false, false, false, true}};
        mostrador.acenderLeds(estados);
    }
}
