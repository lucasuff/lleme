package uff.ic.lleme.tcc00328.exercicios.letreiro.model.mostrador1;

import uff.ic.lleme.tcc00328.exercicios.letreiro.model.Caractere;

public class L extends Caractere {

    private static L instancia = null;

    private L() {
    }

    public static L obterInstancia() {
        if (instancia == null)
            instancia = new L();
        return instancia;
    }

    @Override
    public void acenderMostrador(Mostrador21Segmentos mostrador) {
        boolean[][] estados
            = {{true, false, false, false, false},
            {true, false, false, false, false},
            {true, false, false, false, false},
            {true, false, false, false, false},
            {true, true, true, true, true}};
        mostrador.acenderLeds(estados);
    }
}
