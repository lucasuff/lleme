package uff.ic.lleme.tcc00328.exercicios.letreiro.model.mostrador1;

import uff.ic.lleme.tcc00328.exercicios.letreiro.model.Caractere;

public class I extends Caractere {

    private static I instancia = null;

    private I() {
    }

    public static I obterInstancia() {
        if (instancia == null)
            instancia = new I();
        return instancia;
    }

    @Override
    public void acenderMostrador(Mostrador21Segmentos mostrador) {
        boolean[][] estados = {{false, false, true, false, false},
        {false, false, true, false, false},
        {false, false, true, false, false},
        {false, false, true, false, false},
        {false, false, true, false, false}};
        mostrador.acenderLeds(estados);
    }
}
