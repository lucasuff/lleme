package uff.ic.lleme.tcc00288.aulas.concorrencia.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sorteio {

    public static void main(String[] args) {

        List<Integer> temas = new ArrayList<>();
        temas.addAll(Arrays.asList(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8}));

        for (int i = 8; i > 0; i--)
            System.out.println(temas.remove((int) (Math.random() * i)));
    }
}
