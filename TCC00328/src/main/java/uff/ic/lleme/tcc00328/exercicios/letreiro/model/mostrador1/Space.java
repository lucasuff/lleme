package uff.ic.lleme.tcc00328.exercicios.letreiro.model.mostrador1;

import uff.ic.lleme.tcc00328.exercicios.letreiro.model.Caractere;

public class Space extends Caractere {

    private static Space instancia = null;

    private Space() {
    }

    public static Space obterInstancia() {
        if (instancia == null)
            instancia = new Space();
        return instancia;
    }

    @Override
    public void acenderMostrador(Mostrador21Segmentos mostrador1) {
        boolean[][] estados = {{false, false, false, false, false},
        {false, false, false, false, false},
        {false, false, false, false, false},
        {false, false, false, false, false},
        {false, false, false, false, false}};
        mostrador1.acenderLeds(estados);
    }
}
