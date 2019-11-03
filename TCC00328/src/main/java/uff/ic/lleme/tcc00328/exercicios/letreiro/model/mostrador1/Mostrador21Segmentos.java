package uff.ic.lleme.tcc00328.exercicios.letreiro.model.mostrador1;

import uff.ic.lleme.tcc00328.exercicios.letreiro.model.Caractere;
import uff.ic.lleme.tcc00328.exercicios.letreiro.model.Mostrador;

public class Mostrador21Segmentos extends Mostrador {

    private static final byte linhas = 5;
    private static final byte colunas = 5;
    public boolean[][] estados = new boolean[linhas][colunas];
    private final char[][] leds
            = {{' ', '_', '_', '_', ' '},
            {'|', '\\', '|', '/', '|'},
            {'|', '-', '+', '-', '|'},
            {'|', '/', '|', '\\', '|'},
            {' ', '-', '-', '-', ' '}};

    @Override
    public void acenderLeds(Caractere caractere) {
        caractere.acenderMostrador(this);
    }

    public void acenderLeds(boolean[][] estados) {
        if (estados != null) {
            int k = estados.length;
            int l = estados[0].length;

            for (int i = 0; i < estados.length; i++)
                for (int j = 0; j < estados.length; j++)
                    if (i < k && j < l)
                        this.estados[i][j] = estados[i][j];
        }
    }

    @Override
    public char[][] obterLeds() {
        char[][] leds = new char[linhas][colunas];
        for (int i = 0; i < leds.length; i++)
            for (int j = 0; j < leds[0].length; j++)
                if (estados[i][j])
                    leds[i][j] = this.leds[i][j];
                else
                    leds[i][j] = ' ';
        return leds;
    }
}
