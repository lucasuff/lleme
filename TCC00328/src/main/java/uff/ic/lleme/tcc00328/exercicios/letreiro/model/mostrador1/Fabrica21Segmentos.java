package uff.ic.lleme.tcc00328.exercicios.letreiro.model.mostrador1;

import uff.ic.lleme.tcc00328.exercicios.letreiro.model.Caractere;
import uff.ic.lleme.tcc00328.exercicios.letreiro.model.Fabrica;

public class Fabrica21Segmentos extends Fabrica {

    @Override
    public Mostrador21Segmentos criarMostrador() {
        return new Mostrador21Segmentos();
    }

    @Override
    public Caractere obterCaractere(char caractere) {
        switch (caractere) {
            case 'A':
            case 'a':
                return A.obterInstancia();
            case 'E':
            case 'e':
                return E.obterInstancia();
            case 'I':
            case 'i':
                return I.obterInstancia();
            case 'U':
            case 'u':
                return U.obterInstancia();
            case 'D':
            case 'd':
                return D.obterInstancia();
            case 'L':
            case 'l':
                return L.obterInstancia();
            case 'N':
            case 'n':
                return N.obterInstancia();
            case 'R':
            case 'r':
                return R.obterInstancia();
            case 'Z':
            case 'z':
                return Z.obterInstancia();
            default:
                return Space.obterInstancia();
        }
    }
}
