package uff.ic.lleme.tcc00328.exercicios.dicionario;

import uff.ic.lleme.tcc00328.exercicios.dicionario.model.Palavra;
import java.util.Set;
import java.util.TreeSet;

public abstract class BD {

    private static Set<Palavra> palavras = new TreeSet<>();

    public static Palavra busca(String palavra) {
        for (Palavra p : palavras)
            if (p.palavra.equals(palavra))
                return p;
        return null;
    }
}
