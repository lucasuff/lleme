package uff.ic.lleme.tcc00328.exercicios.letreiro.model.mostrador1;

import uff.ic.lleme.tcc00328.exercicios.letreiro.model.Caractere;

public class D extends Caractere {

    private static D instancia = null;

    private D() {
    }

    public static D obterInstancia() {
        if (instancia == null)
            instancia = new D();
        return instancia;
    }

    @Override
    public void acenderMostrador(Mostrador21Segmentos mostrador) {
        boolean[][] estados = {{true, true, true, true, false},
        {true, false, false, false, true},
        {true, false, false, false, true},
        {true, false, false, false, true},
        {true, true, true, true, false}};
        mostrador.acenderLeds(estados);
    }
}
