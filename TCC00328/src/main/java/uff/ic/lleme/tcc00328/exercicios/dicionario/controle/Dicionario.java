package uff.ic.lleme.tcc00328.exercicios.dicionario.controle;

import uff.ic.lleme.tcc00328.exercicios.dicionario.BD;
import uff.ic.lleme.tcc00328.exercicios.dicionario.model.Palavra;

public class Dicionario {

    public static Palavra busca(String palavra) {
        Palavra p = BD.busca(palavra);
        return p;
    }
}
