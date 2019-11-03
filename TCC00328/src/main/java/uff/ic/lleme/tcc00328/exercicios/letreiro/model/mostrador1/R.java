package uff.ic.lleme.tcc00328.exercicios.letreiro.model.mostrador1;

import uff.ic.lleme.tcc00328.exercicios.letreiro.model.Caractere;

public class R extends Caractere {

    private static R instancia = null;

    private R() {
    }

    public static R obterInstancia() {
        if (instancia == null)
            instancia = new R();
        return instancia;
    }

    @Override
    public void acenderMostrador(Mostrador21Segmentos mostrador) {
        boolean[][] estados = {{true, true, true, true, false},
        {true, false, false, false, true},
        {true, true, true, true, false},
        {true, false, false, true, false},
        {true, false, false, false, true}};
        mostrador.acenderLeds(estados);
    }
}
